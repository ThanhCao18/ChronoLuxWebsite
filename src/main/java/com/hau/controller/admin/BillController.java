package com.hau.controller.admin;

import com.hau.service.BillService;
import com.hau.service.ExcelService;
import com.hau.service.PrintPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PrintPDFService printPDFService;

    @GetMapping("/admin/bills")
    public String showBill(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int limit) {
        model.addAttribute("billPage", billService.getBillsOrderByCreatedDateDesc(page, limit));
        model.addAttribute("currentPage", page);
        return "admin/bill-view";
    }

    @GetMapping("/admin/bill/view")
    public String viewBill(@RequestParam Long id,
                           @RequestParam("page") int currentPage,
                           Model model) {
        model.addAttribute("billDTO", billService.findByIdWithDetail(id));
        model.addAttribute("currentPage", currentPage);
        return "admin/bill-detail-view";
    }

    @GetMapping("/admin/bill/search")
    public String searchBill(@RequestParam(defaultValue = "") String keyword,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int limit,
                             Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("billPage", billService.getBillByIdOrDisplayNameOrPhone(keyword, page, limit));
        return "admin/bill-view";
    }

    @GetMapping("/admin/bill/sort")
    public String sortBill(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int limit,
                           @RequestParam(name = "sortOrder", required = false) String sortOrder,
                           Model model) {
        if (sortOrder.equals("asc")) {
            model.addAttribute("billPage", billService.getBillsOrderByCreatedDateAsc(page, limit));
        } else {
            model.addAttribute("billPage", billService.getBillsOrderByCreatedDateDesc(page, limit));
        }
        return "admin/bill-view";
    }

    @GetMapping("/admin/bill/view-update")
    public String viewUpdateBill(Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int limit) {
        model.addAttribute("billPage", billService.getUnSolveBills(page, limit));
        model.addAttribute("currentPage", page);
        return "admin/bill-update-view";
    }

    @GetMapping("/admin/bill/view-update/confirm-paid")
    public String confirmPaidBill(@RequestParam Long id,
                                  RedirectAttributes redirectAttributes) {
        billService.confirmPaidBill(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xác nhận đơn hàng thanh toán thành công");
        return "redirect:/admin/bill/view-update";
    }

    @GetMapping("/admin/bill/view-update/confirm-received")
    public String confirmReceived(@RequestParam Long id,
                                  RedirectAttributes redirectAttributes) {
        billService.confirmCustomerReceived(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xác nhận giao hàng thành công");
        return "redirect:/admin/bill/view-update";
    }

    @GetMapping("/admin/bill/view-update/confirm-refund")
    public String confirmRefund(@RequestParam Long id,
                                RedirectAttributes redirectAttributes) {
        billService.confirmRefund(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xác nhận hoàn tiền thành công");
        return "redirect:/admin/bill/view-update";
    }

    @PostMapping("/admin/bill/view-update/confirm-cancel")
    public String confirmCancel(@RequestParam Long id,
                                @RequestParam("cancelReason") String cancelReason,
                                RedirectAttributes redirectAttributes) {
        billService.cancelBillById(id, cancelReason);
        redirectAttributes.addFlashAttribute("successMessage", "Xác nhận hủy đơn thành công");
        return "redirect:/admin/bill/view-update";
    }

    @GetMapping("/admin/bill/view-update/search")
    public String searchInUpdateBill(@RequestParam(defaultValue = "") String keyword,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int limit,
                                     Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("billPage", billService.getBillByIdOrDisplayNameOrPhone(keyword, page, limit));
        return "admin/bill-view";
    }

    @GetMapping("/admin/bill/excel/export")
    public void exportExcel(HttpServletResponse response) {
        try {
            byte[] excelData = excelService.exportBillsToExcel();
            LocalDate now = LocalDate.now();
            String formattedDate = now.format(DateTimeFormatter.ofPattern("MM-yyyy"));

            String fileName = "Thống kê hóa đơn " + formattedDate + ".xlsx";

            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);

            response.getOutputStream().write(excelData);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi xuất file Excel", e);
        }
    }

    @GetMapping("/admin/bill/print-pdf")
    public ResponseEntity<InputStreamResource> printInvoice(@RequestParam Long id) throws IOException {
        byte[] pdfBytes = printPDFService.generateInvoicePdf(billService.findByIdWithDetail(id));
        ByteArrayInputStream in = new ByteArrayInputStream(pdfBytes);

        String filename = "Invoice - " + id + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }

}
