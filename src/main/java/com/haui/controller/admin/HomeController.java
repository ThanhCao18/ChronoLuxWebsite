package com.haui.controller.admin;

import com.haui.service.BillService;
import com.haui.service.CartItemService;
import com.haui.service.CurrencyFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller(value = "ControllerOfAdmin")
public class HomeController {
    @Autowired
    private BillService billService;
    @Autowired
    private CurrencyFormat currencyFormat;
    @Autowired
    private CartItemService cartItemService;

    @RequestMapping("/admin/home")
    public String showDashboard(Model model) {
        model.addAttribute("totalCancelledBill", billService.getTotalCancelledBill());
        model.addAttribute("totalBoughtBill", billService.getTotalSoldBill());
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("totalOfPaidBills", currencyFormat.formatCurrency(billService.getTotalOfSoldBills()));
        model.addAttribute("totalOfSuccessfulBillsInMonth", currencyFormat.formatCurrency(billService.getTotalOfSoldBillsInMonth(currentMonth, currentYear)));
        model.addAttribute("top5", cartItemService.findTotalQuantityPerProduct());
        return "admin/home";
    }

    @GetMapping("/admin/total")
    @ResponseBody
    public ResponseEntity<Double> getMonthlyTotal(
            @RequestParam("month") int month,
            @RequestParam("year") int year
    ) {
        Double total = billService.getTotalOfSoldBillsInMonth(month, year);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }

    @GetMapping("/admin/total-quantity")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> getTotalQuantityPerProduct() {
        Map<String, Integer> totalQuantityPerProduct = cartItemService.findTotalQuantityPerProduct();
        return ResponseEntity.ok(totalQuantityPerProduct);
    }
}
