package com.haui.controller.admin;

import com.haui.dto.VoucherConfigDTO;
import com.haui.dto.VoucherDTO;
import com.haui.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("/admin/vouchers")
    public String showVouchers(Model model,
                               @RequestParam(defaultValue = "1") int validVoucherPage,
                               @RequestParam(defaultValue = "1") int expiredVoucherPage,
                               @RequestParam(defaultValue = "10") int limit) {
        model.addAttribute("validVouchers", voucherService.findValidVouchers(validVoucherPage, limit));
        model.addAttribute("expiredVouchers", voucherService.findExpiredVouchers(expiredVoucherPage, limit));
        model.addAttribute("currentValidVoucherPage", validVoucherPage);
        model.addAttribute("currentExpiredVoucherPage", expiredVoucherPage);
        return "admin/vouchers-view";
    }

    @GetMapping("/admin/voucher/create")
    public String showCreateVoucherForm(Model model) {
        model.addAttribute("voucher", new VoucherDTO());
        return "admin/voucher-add";
    }

    @PostMapping("/admin/voucher/save")
    public String saveVoucher(@ModelAttribute("voucher") VoucherDTO voucherDTO,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) throws Exception {
        request.setCharacterEncoding("UTF-8");
        voucherService.save(voucherDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm mã giảm giá thành công");
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/admin/voucher/delete")
    public String deleteVoucher(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        voucherService.deleteVoucher(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa mã giảm giá thành công");
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/admin/default-voucher")
    @ResponseBody
    public ResponseEntity<VoucherConfigDTO> getVoucherConfig() {
        return ResponseEntity.ok(voucherService.getVoucherConfig());
    }

    @PostMapping("/admin/default-voucher")
    @ResponseBody
    public ResponseEntity<?> updateVoucherConfig(@RequestBody VoucherConfigDTO voucherConfigDTO) {
        voucherService.updateVoucherConfig(voucherConfigDTO);
        return ResponseEntity.ok("ok");
    }
}
