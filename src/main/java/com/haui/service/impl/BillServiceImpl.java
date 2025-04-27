package com.haui.service.impl;

import com.haui.Enum.DeliveryStatus;
import com.haui.constant.SystemConstant;
import com.haui.converter.BillConverter;
import com.haui.converter.CartItemConverter;
import com.haui.converter.ProductBillSnapshotConverter;
import com.haui.dto.BillDTO;
import com.haui.dto.CartItemDTO;
import com.haui.dto.ProductBillSnapshotDTO;
import com.haui.entity.*;
import com.haui.repository.*;
import com.haui.service.BillService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private BillConverter billConverter;
    @Autowired
    private CartItemConverter cartItemConverter;
    @Autowired
    private ProductBillSnapshotRepository productBillSnapshotRepository;
    @Autowired
    private ProductBillSnapshotConverter productBillSnapshotConverter;

    @Override
    @Transactional
    public BillDTO save(BillDTO billDTO) {
        BillEntity billEntity = billConverter.convertToEntity(billDTO);
        if (billDTO.getUsername() != null) {
            UserEntity user = userRepository.findOneByUserNameAndStatus(billDTO.getUsername(), SystemConstant.ACTIVE_STATUS);
            billEntity.setUser(user);
        }

        List<CartItemEntity> cartItemEntities = new ArrayList<>();
        for (CartItemDTO cartItemDTO : billDTO.getCartItemDTOS()) {
            CartItemEntity cartItemEntity = cartItemConverter.convertToEntity(cartItemDTO);
            ProductEntity productEntity = productRepository.findOneByNameAndActiveForUpdate(cartItemDTO.getProductName(), true);
            if(productEntity.getInstock() < Integer.parseInt(cartItemDTO.getQuantity()))
            {
                throw new RuntimeException("không đủ sản phẩm trong kho");
            }
            productEntity.setInstock(productEntity.getInstock() - Integer.parseInt(cartItemDTO.getQuantity()));
            if (cartItemDTO.getUsername() != null && Objects.equals(cartItemDTO.getUsername(),"null")) {
                UserEntity userEntity = userRepository.findOneByUserNameAndStatus(cartItemDTO.getUsername(), SystemConstant.ACTIVE_STATUS);
                cartItemEntity.setUser(userEntity);
            }
            cartItemEntity.setProduct(productEntity);
            cartItemRepository.save(cartItemEntity);
            productRepository.save(productEntity);
            cartItemEntities.add(cartItemEntity);
        }
        if (billDTO.getVoucherCode() != null) {
            VoucherEntity voucherEntity = voucherRepository.findOneByCodeAndActive(billDTO.getVoucherCode(),true);
            billEntity.setVoucher(voucherEntity);
        }

        billEntity.setCartItems(cartItemEntities);


        BillEntity bill = billRepository.save(billEntity);
        //save Product Bill Snapshot
        bill.getCartItems().stream().forEach(cartItem -> {
            ProductBillSnapshot productBillSnapshot = new ProductBillSnapshot();
            productBillSnapshot.setPrice(cartItem.getProduct().getPrice());
            productBillSnapshot.setImgUrl(cartItem.getProduct().getImgUrl());
            productBillSnapshot.setQuantity(cartItem.getQuantity());
            productBillSnapshot.setProductName(cartItem.getProduct().getName());
            productBillSnapshot.setBill(bill);
            productBillSnapshotRepository.save(productBillSnapshot);
        });

        for (CartItemEntity cartItemEntity : cartItemEntities) {
            cartItemEntity.setBill(bill);
        }
        return billConverter.convertToDTO(billEntity);
    }

    @Override
    @Transactional
    public void confirmPaidBill(Long id) {
        BillEntity billEntity = billRepository.findOne(id);
        billEntity.setStatus(SystemConstant.PAYMENT_SUCCESS);
        billRepository.save(billEntity);
    }

    @Override
    public void confirmRefund(Long id) {
        BillEntity billEntity = billRepository.findOne(id);
        billEntity.setStatus(SystemConstant.PAYMENT_REFUND_SUCCESS);
        billRepository.save(billEntity);
    }

    @Override
    @Transactional
    public void confirmCustomerReceived(Long id) {
        BillEntity billEntity = billRepository.findOne(id);
        billEntity.setDeliveryStatus(DeliveryStatus.DELIVERED);
        billRepository.save(billEntity);
    }

    @Override
    public int getTotalCancelledBill() {
        return billRepository.countTotalCancelledBill();
    }

    @Override
    public int getTotalSoldBill() {
        return billRepository.countTotalSoldBill();
    }

    @Override
    public Double getTotalOfSoldBills() {
        Double total = billRepository.findTotalOfSoldBills();
        if (total == null) {
            return 0.0;
        }
        return billRepository.findTotalOfSoldBills();
    }

    @Override
    public Page<BillDTO> getUnSolveBills(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<BillEntity> refundBills = billRepository.findByStatusInAndDeliveryStatusInOrderByCreateDateDesc(pageable,
                List.of(SystemConstant.PAYMENT_REFUND_PENDING, SystemConstant.PAYMENT_SUCCESS),
                List.of(DeliveryStatus.CANCELLED));

        Page<BillEntity> unsolveBills = billRepository.findByStatusInAndDeliveryStatusInOrderByCreateDateDesc(pageable,
                List.of(SystemConstant.PAYMENT_PENDING, SystemConstant.PAYMENT_SUCCESS),
                List.of(DeliveryStatus.PENDING));

        List<BillEntity> mergeBill = new ArrayList<>();
        mergeBill.addAll(refundBills.getContent());
        mergeBill.addAll(unsolveBills.getContent());
        mergeBill.sort(Comparator.comparing(BillEntity::getCreateDate).reversed());

        List<BillDTO> dto = mergeBill.stream().map(billEntity -> {
            return billConverter.convertToDTO(billEntity);
        }).collect(Collectors.toList());

        return new PageImpl<>(dto, pageable, refundBills.getTotalElements() + unsolveBills.getTotalElements());
    }

    @Override
    public Page<BillDTO> getPaidBills(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<BillEntity> billEntities = billRepository.findPaidBills(pageable);
        List<BillDTO> billDTOS = billEntities.getContent().stream().map(billEntity -> {
            BillDTO billDTO = billConverter.convertToDTO(billEntity);
            return billDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(billDTOS, pageable, billEntities.getTotalElements());
    }

    @Override
    public Page<BillDTO> getBillsOrderByCreatedDateDesc(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<BillEntity> billEntities = billRepository.findAllByOrderByCreateDateDesc(pageable);
        List<BillDTO> billDTOS = billEntities.getContent().stream().map(billEntity -> {
            BillDTO billDTO = billConverter.convertToDTO(billEntity);
            return billDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(billDTOS, pageable, billEntities.getTotalElements());
    }

    @Override
    public Page<BillDTO> getBillsOrderByCreatedDateAsc(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<BillEntity> billEntities = billRepository.findAllByOrderByCreateDateAsc(pageable);
        List<BillDTO> billDTOS = billEntities.getContent().stream().map(billEntity -> {
            BillDTO billDTO = billConverter.convertToDTO(billEntity);
            return billDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(billDTOS, pageable, billEntities.getTotalElements());
    }

    @Override
    public Double getTotalOfSoldBillsInMonth(int month, int year) {
        Double total = billRepository.findTotalOfSoldBillsInMonth(month, year);
        if (total == null) {
            return 0.0;
        }
        return billRepository.findTotalOfSoldBillsInMonth(month, year);
    }

    @Override
    public BillDTO findByIdWithDetail(Long id) {
        BillEntity billEntity = billRepository.findOne(id);
        List<ProductBillSnapshotDTO> productBillSnapshots = billEntity.getProductBillSnapshots().stream().map(productBillSnapshot -> {
            return productBillSnapshotConverter.convertToDTO(productBillSnapshot);
        }).toList();
        BillDTO billDTO = billConverter.convertToDTO(billEntity);
        billDTO.setProductBillSnapshotDTOS(productBillSnapshots);
        return billDTO;
    }

    @Override
    public List<BillDTO> getBillByUser(Long userId) {
        return billRepository.findByUserIdOrderByCreateDateDesc(userId).orElse(Collections.emptyList()).stream()
                .map(billEntity -> billConverter.convertToDTO(billEntity))
                .toList();
    }

    @Override
    public List<BillDTO> getAllBills() {
        List<BillEntity> billEntities = billRepository.findAllByOrderByCreateDateDesc();
        return billEntities.stream().map(billEntity -> {
            BillDTO billDTO = billConverter.convertToDTO(billEntity);
            List<ProductBillSnapshotDTO> productBillSnapshots = billEntity.getProductBillSnapshots().stream().map(productBillSnapshot -> {
                return productBillSnapshotConverter.convertToDTO(productBillSnapshot);
            }).toList();
            billDTO.setProductBillSnapshotDTOS(productBillSnapshots);
            return billDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<BillDTO> getBillByIdOrDisplayNameOrPhone(String keyword, int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<BillEntity> billEntities;
        if (StringUtils.isBlank(keyword)) {
            billEntities = billRepository.findAllByOrderByCreateDateDesc(pageable);
        } else {
            try {
                Long id = Long.parseLong(keyword);
                billEntities = billRepository.findByIdOrderByCreateDateDesc(pageable, id);
            } catch (NumberFormatException e) {
                billEntities = billRepository.findByDisplayNameOrPhoneContainingIgnoreCaseOrderByCreateDateDesc(pageable, keyword, keyword);
            }
        }

        List<BillDTO> billDTOS = billEntities.getContent().stream().map(billEntity -> {
            BillDTO billDTO = billConverter.convertToDTO(billEntity);
            return billDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(billDTOS, pageable, billEntities.getTotalElements());
    }

    @Override
    @Transactional
    public void cancelBillById(Long id,String reason) {
        BillEntity billEntity = billRepository.findOne(id);
        billEntity.setCancelReason(reason);
        billEntity.setDeliveryStatus(DeliveryStatus.CANCELLED);

        if(billEntity.getStatus().equals(SystemConstant.PAYMENT_SUCCESS)) {
            billEntity.setStatus(SystemConstant.PAYMENT_REFUND_PENDING);
        }
        List<CartItemEntity> cartItemEntities = billEntity.getCartItems();
        cartItemEntities.forEach(cartItemEntity -> {
            ProductEntity productEntity = cartItemEntity.getProduct();
            productEntity.setInstock(productEntity.getInstock() + cartItemEntity.getQuantity());
            productRepository.save(productEntity);
        });
        billRepository.save(billEntity);
    }
}
