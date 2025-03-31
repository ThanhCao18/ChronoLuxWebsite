package com.hau.dto;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.ui.Model;

import java.util.Map;

public class FilterCriteria {
    private Long minPrice;
    private Long maxPrice;
    private String gender;
    private String keyword;

    public FilterCriteria(Long minPrice, Long maxPrice, String gender, String keyword) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.gender = gender;
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // Getters và setters
    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public static FilterCriteria Of(String filter,String keyword){
        Long minPrice =null;
        Long maxPrice =null;
        String gender = null;
        if(filter != null){
            if (filter.contains("duoi-1-trieu")) {
                minPrice = 0L;
                maxPrice = 1000000L;
            } else if (filter.contains("tu-1-3-trieu")) {
                minPrice = 1000000L;
                maxPrice = 3000000L;
            } else if (filter.contains("tu-3-6-trieu")) {
                minPrice = 3000000L;
                maxPrice = 6000000L;
            } else if (filter.contains("tu-6-9-trieu")) {
                minPrice = 6000000L;
                maxPrice = 9000000L;
            } else if (filter.contains("tren-9-trieu")) {
                minPrice = 9000000L;
                maxPrice = null; // Không giới hạn trên
            }

            if(filter.contains("nam")){
                gender = "nam";
            }
            else if(filter.contains("nu")){
                gender = "nu";
            }
        }
        String searchKeyword = (keyword == null) ? null : "%" + keyword + "%";
        return  new FilterCriteria(minPrice,maxPrice,gender,searchKeyword);
    }
    public static Map<String,String> applyFiltersAndSorting(ProductDTO product,int page,int limit, String sortName, String sortBy,
                                              String keyword, String filter) {
        product.setPage(page);
        product.setLimit(limit);
        Map<String, String> priceFilters = null;
        // Kiểm tra và thiết lập thông tin sắp xếp
        if (sortName != null && sortBy != null) {
            product.setSortName(sortName);
            product.setSortBy(sortBy);
        }

        // Thiết lập từ khóa tìm kiếm nếu có
        if (keyword != null) {
            product.setKeyword(keyword);
        }

        // Thiết lập bộ lọc và thêm vào model nếu filter không rỗng
        if (filter != null) {
            priceFilters = new LinkedTreeMap<>();
            priceFilters.put("nam", "Nam");
            priceFilters.put("nu", "Nữ");
            priceFilters.put("duoi-1-trieu", "Dưới 1 triệu");
            priceFilters.put("tu-1-3-trieu", "Từ 1 - 3 triệu");
            priceFilters.put("tu-3-6-trieu", "Từ 3 - 6 triệu");
            priceFilters.put("tu-6-9-trieu", "Từ 6 - 9 triệu");
            priceFilters.put("tren-9-trieu", "Trên 9 triệu");
        }
        return priceFilters;
    }
}



