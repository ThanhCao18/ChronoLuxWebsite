package com.haui.controller.admin;

import com.haui.dto.FilterCriteria;
import com.haui.dto.ProductDTO;
import com.haui.dto.ProductLineDTO;
import com.haui.service.BrandService;
import com.haui.service.ProductService;
import com.haui.service.ProductLineService;
import com.haui.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.haui.service.FileService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller(value = "ControllerOfProductLineWeb")
public class ProductLineController {
    @Autowired
    private ProductLineService productLineService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private FileService fileService;
    private static final String UPLOAD_DIR = "product-lines";

    @GetMapping("/shop/brand/product-line")
    public String ProductLinePage(Model model,
                                  @RequestParam("idBrand") long idBrand,
                                  @RequestParam("id") long id,
                                  @RequestParam("page") int page ,
                                  @RequestParam("limit") int limit,
                                  @RequestParam(value = "sortName",required = false) String sortName,
                                  @RequestParam(value = "sortBy",required = false) String sortBy,
                                  @RequestParam(value = "keyword",required = false) String keyword,
                                  @RequestParam(value = "filter",required = false) String filter){
        ProductDTO product = new ProductDTO();
        ProductLineDTO productLine = productLineService.findByIdAndActive(id,true);
        Map<String,String> priceFilter = FilterCriteria.applyFiltersAndSorting(product,page,limit,sortName,sortBy,keyword,filter);
        product.setTotalItem((int)productService.getTotalItemByIdProductLine(id,keyword,filter));
        product.setTotalPage((int) Math.ceil((double) product.getTotalItem() / product.getLimit()));
        product.setId(id);
        Pageable pageable = PageableUtil.getInstance(page,limit,sortName,sortBy);
        productLine.setListResult(productLineService.findAllByBrandIdAndActive(idBrand,true));
        // Thêm filter vào model
        model.addAttribute("filter", filter);
        product.setFilter(filter);
        model.addAttribute("priceFilters", priceFilter);
        model.addAttribute("brand",brandService.getBrandById(idBrand,true));
        model.addAttribute("products",productService.findAllByIdProductLine(pageable,id,keyword,filter));
        model.addAttribute("model",product);
        model.addAttribute("productLine",productLine);
        model.addAttribute("idBrand",idBrand);
        return "web/product-line";
    }

    @GetMapping("/admin/product-lines")
    public String getProductLinesPage(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "6") int limit,
                                      @RequestParam(defaultValue = "0") long brandId,
                                      Model model) {
        Page<ProductLineDTO> productLines;
        if(brandId == 0){
            productLines = productLineService.findAll(page, limit);
        }
        else{
            productLines = productLineService.findAllByBrandId(brandId, page, limit);
        }
        model.addAttribute("productLines", productLines);
        model.addAttribute("currentPage", page);
        model.addAttribute("brands", brandService.findAllByActive(true));
        model.addAttribute("brandId", brandId);
        return "admin/product-line-view";
    }

    @GetMapping("/admin/product-line/create")
    public String showCreateForm(Model model,
                                @RequestParam("currentPage") int page) {
        model.addAttribute("productLine", new ProductLineDTO());
        model.addAttribute("brands", brandService.findAllByActive(true));
        model.addAttribute("currentPage", page);
        return "admin/product-line-add";
    }

    @PostMapping("/admin/product-line/save")
    public String saveProductLine(@ModelAttribute("productLine") ProductLineDTO productLine,
                                  @RequestParam MultipartFile logo,
                                  @RequestParam MultipartFile banner,
                                  @RequestParam("page") int currentPage,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) throws Exception{
        request.setCharacterEncoding("UTF-8");
        if(!logo.isEmpty() && !banner.isEmpty()) {
            String logoName = fileService.saveFile(logo, UPLOAD_DIR);
            String bannerName = fileService.saveFile(banner, UPLOAD_DIR);
            productLine.setIconUrl(logoName);
            productLine.setBannerUrl(bannerName);
        }
        else if(!logo.isEmpty() && banner.isEmpty()) {
            String logoName = fileService.saveFile(logo, UPLOAD_DIR);
            productLine.setIconUrl(logoName);
            productLine.setBannerUrl(productLineService.findByIdAndActive(productLine.getId(),true).getBannerUrl());
        }
        else if(logo.isEmpty() && !banner.isEmpty()) {
            String bannerName = fileService.saveFile(banner, UPLOAD_DIR);
            productLine.setBannerUrl(bannerName);
            productLine.setIconUrl(productLineService.findByIdAndActive(productLine.getId(),true).getIconUrl());
        }
        else{
            ProductLineDTO productLineDTO = productLineService.findByIdAndActive(productLine.getId(),true);
            productLine.setIconUrl(productLineDTO.getIconUrl());
            productLine.setBannerUrl(productLineDTO.getBannerUrl());
        }
        String message = (productLine.getId() == null || productLine.getId() == 0) ? "Thêm dòng sản phẩm thành công" : "Cập nhật dòng sản phẩm thành công";
        redirectAttributes.addFlashAttribute("successMessage", message);
        productLineService.save(productLine);
        return "redirect:/admin/product-lines?page=" + currentPage;
    }

    @GetMapping("/admin/product-line/update")
    public String showUpdateForm(@RequestParam("id") long id, Model model, @RequestParam("currentPage") int page) {
        ProductLineDTO productLine = productLineService.findByIdAndActive(id,true);
        model.addAttribute("productLine", productLine);
        model.addAttribute("brands", brandService.findAllByActive(true));
        model.addAttribute("currentPage", page);
        return "admin/product-line-update";
    }

    @GetMapping("/admin/product-line/delete")
    public String deleteProductLine(@RequestParam("id") long id) {
        productLineService.deleteById(id);
        return "redirect:/admin/product-lines";
    }

    // for selection tag with ajax
    @GetMapping("/admin/product-line/getProductLines")
    @ResponseBody
    public List<ProductLineDTO> getProductLines(@RequestParam("brandId") long brandId) {
        return productLineService.findAllByBrandIdAndActive(brandId,true);
    }

    @GetMapping("/admin/product-line/search")
    public String searchProductLine(@RequestParam("keyword") String keyword,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "6") int limit,
                                   Model model) {
        Page<ProductLineDTO> productLines = productLineService.findByKeyword(keyword, page, limit);
        model.addAttribute("productLines", productLines);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("brands", brandService.findAllByActive(true));
        if (!productLines.getContent().isEmpty()) {
            model.addAttribute("brandId", productLines.getContent().getFirst().getBrandId());
        } else {
            model.addAttribute("brandId", null);
        }
        return "admin/product-line-view";
    }
}
