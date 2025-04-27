package com.haui.service.impl;

import com.haui.converter.ProductConverter;
import com.haui.dto.FilterCriteria;
import com.haui.dto.ProductDTO;
import com.haui.entity.ProductEntity;
import com.haui.entity.ProductLineEntity;
import com.haui.repository.ProductLineRepository;
import com.haui.repository.ProductRepository;
import com.haui.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private ProductLineRepository productLineRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findByName(String name) {
        ProductEntity result = productRepository.findOneByNameAndActive(name, true);
        if(result != null)
        return productConverter.toDTO(result);
        else return null;
    }

    @Override
    public List<ProductDTO> findTop8ByOrderByIdDesc() {
        List<ProductDTO> products = new ArrayList<>();
        List<ProductEntity> productEntities = productRepository.findTop8ByActiveOrderByIdDesc(true);
        for (ProductEntity productEntity : productEntities) {
            ProductDTO product = productConverter.toDTO(productEntity);
            products.add(product);
        }
        return products;
    }

    @Override
    public LinkedHashSet<ProductDTO> findTop8BestSelling() {
        LinkedHashSet<ProductDTO> products = new LinkedHashSet<>();
        LinkedHashSet<ProductEntity> productEntities = productRepository.findTop8BestSellingProducts();
        int count = 0;
        for (ProductEntity productEntity : productEntities) {
            ProductDTO product = productConverter.toDTO(productEntity);
            products.add(product);
            if(++count ==8)
                break;
        }
        return products;
    }

    @Override
    public List<ProductDTO> findAllByActive(boolean active) {
        List<ProductDTO> products = new ArrayList<>();
        List<ProductEntity> productEntities = productRepository.findAllByActive(active);
        for (ProductEntity productEntity : productEntities) {
            ProductDTO product = productConverter.toDTO(productEntity);
            products.add(product);
        }
        return products;
    }

    @Override
    public List<ProductDTO> findAll(Pageable pageable, String keyword, String filter) {

        List<ProductDTO> products = new ArrayList<>();
        List<ProductEntity> productEntities = null;
        FilterCriteria filterCriteria = FilterCriteria.Of(filter, keyword);
        productEntities = productRepository.findAll(filterCriteria.getGender(), filterCriteria.getKeyword(),
                filterCriteria.getMinPrice(), filterCriteria.getMaxPrice(),
                pageable).getContent();

        for (ProductEntity productEntity : productEntities) {
            ProductDTO product = productConverter.toDTO(productEntity);
            products.add(product);
        }
        return products;
    }


    @Override
    public long getTotalItem(String keyword, String filter) {
        FilterCriteria filterCriteria = FilterCriteria.Of(filter, keyword);
        return productRepository.count(filterCriteria.getGender(), filterCriteria.getKeyword(), filterCriteria.getMinPrice(), filterCriteria.getMaxPrice());
    }

    @Override
    public List<ProductDTO> findAllByIdBrand(Pageable pageable, Long id, String keyword, String filter) {
        List<ProductEntity> productEntities = null;
        List<ProductDTO> products = new ArrayList<>();
        FilterCriteria filterCriteria = FilterCriteria.Of(filter, keyword);
        productEntities = productRepository.findAllByIdBrand(id, filterCriteria.getGender(), filterCriteria.getKeyword(),
                filterCriteria.getMinPrice(), filterCriteria.getMaxPrice(),
                pageable).getContent();
        for (ProductEntity productEntity : productEntities) {
            ProductDTO product = productConverter.toDTO(productEntity);
            products.add(product);
        }
        return products;
    }

    @Override
    public long getTotalItemByIdBrand(Long id, String keyword, String filter) {
        FilterCriteria filterCriteria = FilterCriteria.Of(filter, keyword);
        return productRepository.countByIdBrand(id, filterCriteria.getGender(), filterCriteria.getKeyword(), filterCriteria.getMinPrice(), filterCriteria.getMaxPrice());
    }

    @Override
    public long getTotalItemByIdProductLine(Long id, String keyword, String filter) {
        FilterCriteria filterCriteria = FilterCriteria.Of(filter, keyword);
        return productRepository.countByIdProductLine(id, filterCriteria.getGender(), filterCriteria.getKeyword(), filterCriteria.getMinPrice(), filterCriteria.getMaxPrice());
    }

    @Override
    public List<ProductDTO> findAllByIdProductLine(Pageable pageable, Long id, String keyword, String filter) {
        List<ProductEntity> productEntities = null;
        List<ProductDTO> products = new ArrayList<>();
        FilterCriteria filterCriteria = FilterCriteria.Of(filter, keyword);
        productEntities = productRepository.findAllByIdProductLine(id, filterCriteria.getGender(), filterCriteria.getKeyword(),
                filterCriteria.getMinPrice(), filterCriteria.getMaxPrice(),
                pageable).getContent();
        for (ProductEntity productEntity : productEntities) {
            ProductDTO product = productConverter.toDTO(productEntity);
            products.add(product);
        }
        return products;
    }

    @Override
    public ProductDTO findByIdAndActive(long id, boolean active) {
        ProductEntity productEntity = productRepository.findByIdAndActive(id, active);
        return productConverter.toDTO(productEntity);
    }

    @Override
    public Page<ProductDTO> findByProductLine_Id(Long id, int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductEntity> productEntities = productRepository.findAllByProductLine_IdAndActive(id, true, pageable);
        if (productEntities == null) {
            return null;
        }
        List<ProductDTO> productDTOs = productEntities.getContent().stream().map(productEntity -> {
            ProductDTO productDTO = productConverter.toDTO(productEntity);
            productDTO.setBrandName(productEntity.getProductLine().getBrand().getName());
            productDTO.setProductLineName(productEntity.getProductLine().getName());
            return productDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(productDTOs, pageable, productEntities.getTotalElements());
    }

    @Override
    public Page<ProductDTO> findByBrand_Id(Long id, int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductEntity> productEntities = productRepository.findAllByBrandIdAndActive(id, true, pageable);
        List<ProductDTO> productDTOs = productEntities.getContent().stream().map(productEntity -> {
            ProductDTO productDTO = productConverter.toDTO(productEntity);
            productDTO.setBrandName(productEntity.getProductLine().getBrand().getName());
            productDTO.setProductLineName(productEntity.getProductLine().getName());
            return productDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(productDTOs, pageable, productEntities.getTotalElements());
    }

    @Override
    public Page<ProductDTO> findAll(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductEntity> productEntities = productRepository.findAllByActive(true, pageable);
        List<ProductDTO> productDTOs = productEntities.getContent().stream().map(productEntity -> {
            ProductDTO productDTO = productConverter.toDTO(productEntity);
            productDTO.setBrandName(productEntity.getProductLine().getBrand().getName());
            productDTO.setProductLineName(productEntity.getProductLine().getName());
            return productDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(productDTOs, pageable, productEntities.getTotalElements());
    }

    @Transactional
    @Override
    public void save(ProductDTO productDTO) {
        // add
        if (productDTO.getId() == null) {
            ProductEntity productEntity = productConverter.toEntity(productDTO);
            productEntity.setActive(true);
            ProductLineEntity productLineEntity = productLineRepository.findOne(productDTO.getProductLineId());
            productEntity.setProductLine(productLineEntity);
            productLineEntity.getProducts().add(productEntity);
            productRepository.save(productEntity);
        } else {
            // update
            ProductEntity oldProduct = productRepository.findOne(productDTO.getId());

            ProductEntity updatedProduct = productConverter.toEntity(productDTO);
            updatedProduct.setActive(true);
            updatedProduct.setRatings(oldProduct.getRatings());
            updatedProduct.setBills(oldProduct.getBills());
            updatedProduct.setProductLine(oldProduct.getProductLine());

            productRepository.save(updatedProduct);
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        ProductEntity productEntity = productRepository.findOne(id);
        productEntity.setActive(false);
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductDTO> findAllByIdBrandNotPage(Long id) {
        List<ProductEntity> productEntities = null;
        List<ProductDTO> products = new ArrayList<>();
        productEntities = productRepository.findAllByIdBrandNotPage(id);
        for (ProductEntity productEntity : productEntities) {
            ProductDTO product = productConverter.toDTO(productEntity);
            products.add(product);
        }
        return products;
    }

    @Override
    public Page<ProductDTO> findByKeyword(String keyword, int page, int limit) {
        if (StringUtils.isEmpty(keyword.trim())) {
            return findAll(page, limit);
        }
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductEntity> productEntities = productRepository.findAllByNameContainingIgnoreCaseAndActive(keyword, true, pageable);
        List<ProductDTO> productDTOs = productEntities.getContent().stream().map(productEntity -> {
            ProductDTO productDTO = productConverter.toDTO(productEntity);
            productDTO.setBrandName(productEntity.getProductLine().getBrand().getName());
            productDTO.setProductLineName(productEntity.getProductLine().getName());
            return productDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(productDTOs, pageable, productEntities.getTotalElements());
    }

}
