package com.hau.controller.admin;

import com.hau.dto.CorporationDTO;
import com.hau.service.CorporationService;
import com.hau.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CorporationController {
    private final CorporationService corporationService;
    private final FileService fileService;

    @Autowired
    public CorporationController(CorporationService corporationService, FileService fileService) {
        this.corporationService = corporationService;
        this.fileService = fileService;
    }

    @GetMapping("/admin/corporation")
    public String getCorporationPage(Model model) {
        model.addAttribute("corporation", corporationService.getAllCorporationInformation());
        return "admin/corporation-information";
    }

    @PostMapping("/admin/corporation/save")
    public String saveCorporation(@ModelAttribute("corporation") CorporationDTO corporationDTO,
                                  @RequestParam("img_file") MultipartFile file,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) throws Exception{
        request.setCharacterEncoding("UTF-8");
        if (!file.isEmpty()) {
            fileService.deleteFile(corporationDTO.getImg(), "corporation");
            String imgName = fileService.saveFile(file, "corporation");
            corporationDTO.setImg(imgName);
        } else {
            corporationDTO.setImg(corporationService.getAllCorporationInformation().getImg());
        }
        corporationService.saveCorporation(corporationDTO);
        String message = (corporationDTO.getId() == null || corporationDTO.getId() == 0) ? "Thêm thông tin giới thiệu thành công" : "Cập nhật thông tin giới thiệu thành công";
        redirectAttributes.addFlashAttribute("successMessage", message);
        return "redirect:/admin/corporation";
    }

    @GetMapping("/coporation")
    public ResponseEntity<CorporationDTO> GetCoporation(){
        return ResponseEntity.ok(corporationService.getAllCorporationInformation());
    }
}
