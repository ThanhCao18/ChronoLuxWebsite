package com.hau.service;

import com.hau.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;


public interface ProductService {
    ProductDTO findByName(String name);
    List<ProductDTO> findTop8ByOrderByIdDesc();
    LinkedHashSet<ProductDTO> findTop8BestSelling();
    List<ProductDTO> findAllByActive(boolean active);
    List<ProductDTO> findAll(Pageable pageable,String keyword,String filter);
    long getTotalItem(String keyword,String filter);
    List<ProductDTO> findAllByIdBrand(Pageable pageable,Long id,String keyword,String filter);
    long getTotalItemByIdBrand(Long id,String keyword,String filter);
    long getTotalItemByIdProductLine(Long id,String keyword,String filter);
    List<ProductDTO> findAllByIdProductLine(Pageable pageable,Long id,String keyword,String filter);
    ProductDTO findByIdAndActive(long id,boolean active);
    Page<ProductDTO> findByProductLine_Id(Long id, int page, int limit);
    Page<ProductDTO> findByBrand_Id(Long id, int page, int limit);
    Page<ProductDTO> findAll(int page, int limit);
    void save(ProductDTO productDTO);
    void delete(long id);
    List<ProductDTO> findAllByIdBrandNotPage(Long id);
    // admin
    Page<ProductDTO> findByKeyword(String keyword, int page, int limit);
}
