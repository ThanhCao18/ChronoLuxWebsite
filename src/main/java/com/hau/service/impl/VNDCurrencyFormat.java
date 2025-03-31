package com.hau.service.impl;

import com.hau.service.CurrencyFormat;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class VNDCurrencyFormat implements CurrencyFormat {
    @Override
    public String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }
}
