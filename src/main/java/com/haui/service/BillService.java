package com.haui.service;

import com.haui.dto.BillDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillService {
    BillDTO save(BillDTO billDTO);
    void confirmPaidBill(Long id);
    void confirmRefund(Long id);
    void confirmCustomerReceived(Long id);
    int getTotalCancelledBill();
    int getTotalSoldBill();
    Double getTotalOfSoldBills();
    Page<BillDTO> getUnSolveBills(int page, int limit);
    Page<BillDTO> getPaidBills(int page, int limit);
    Page<BillDTO> getBillsOrderByCreatedDateDesc(int page, int limit);
    Page<BillDTO> getBillsOrderByCreatedDateAsc(int page, int limit);
    Double getTotalOfSoldBillsInMonth(int month, int year);
    BillDTO findByIdWithDetail(Long id);
    List< BillDTO> getBillByUser(Long userId);
    List<BillDTO> getAllBills();
    Page< BillDTO> getBillByIdOrDisplayNameOrPhone(String keyword, int page, int limit);
    void cancelBillById(Long id,String reason);
}
