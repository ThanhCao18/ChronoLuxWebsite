package com.hau.controller.admin;

import com.hau.dto.ProductLineDTO;
import com.hau.dto.WarrantyDTO;
import com.hau.service.BrandService;
import com.hau.service.ProductLineService;
import com.hau.service.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WarrantyController {
    @Autowired
    private ProductLineService productLineService;
    @Autowired
    private WarrantyService warrantyService;
    @Autowired
    private BrandService brandService;

    @GetMapping("/admin/warranty")
    public String getWarrantyPage(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "6") int limit,
                                  @RequestParam(defaultValue = "0") long brandId,
                                  @RequestParam(defaultValue = "2") int isHasWarranty,
                                  Model model) {
        Page<ProductLineDTO> productLines;
        if (brandId == 0 && isHasWarranty == 2) { // brand: all - warranty: all
            productLines = productLineService.findAll(page, limit);
        } else if (brandId == 0 && isHasWarranty == 1) { // brand: all - warranty: true
            productLines = productLineService.findAllWithWarranty(page, limit, true);
        } else if (brandId == 0 && isHasWarranty == 0) { // brand: all - warranty: false
            productLines = productLineService.findAllWithWarranty(page, limit, false);
        } else if (brandId != 0 && isHasWarranty == 1) { // brand: your choice - warranty: true
            productLines = productLineService.findAllWithWarrantyByBrandId(page, limit, true, brandId);
        } else if (brandId != 0 && isHasWarranty == 0) { // brand: your choice - warranty: false
            productLines = productLineService.findAllWithWarrantyByBrandId(page, limit, false, brandId);
        } else { // brand: your choice - warranty: all
            productLines = productLineService.findAllByBrandId(brandId, page, limit);
        }
        model.addAttribute("productLines", productLines);
        model.addAttribute("currentPage", page);
        model.addAttribute("brands", brandService.findAllByActive(true));
        model.addAttribute("brandId", brandId);
        model.addAttribute("isHasWarranty", isHasWarranty);
        return "admin/warranty-view";
    }

    @GetMapping("/admin/warranty/createOrUpdate")
    public String getCreateWarrantyPage(Model model,
                                        @RequestParam long id,
                                        @RequestParam("currentPage") int page) {
        model.addAttribute("warranty", warrantyService.findOneByProductLineId(id));
        model.addAttribute("currentPage", page);
        return "admin/warranty-information";
    }

    @PostMapping("/admin/warranty/save")
    public String saveWarranty(@ModelAttribute WarrantyDTO warrantyDTO,
                               @RequestParam long productLineId,
                               @RequestParam("page") int currentPage,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) throws Exception {
        request.setCharacterEncoding("UTF-8");
        warrantyDTO.setProductLineId(productLineId);
        warrantyService.saveWarranty(warrantyDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm chính sách bảo hành thành công");
        return "redirect:/admin/warranty?page=" + currentPage;
    }

    @GetMapping("/admin/warranty/delete")
    public String deleteWarranty(@RequestParam long id) {
        warrantyService.deleteWarrantyByProductLineId(id);
        return "redirect:/admin/warranty";
    }
}
