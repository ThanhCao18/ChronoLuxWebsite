package com.hau.service.impl;

import com.hau.constant.SystemConstant;
import com.hau.dto.CartItemDTO;
import com.hau.util.GetSiteURLUtil;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PaymentServices {
    private static final String CLIENT_ID = "Afv4DMYmONIPvzNkw3vsXG5gd0hPeIvvVRmA6TC4AYMMW3eAOrybjvyMIjTif4PvWa6hx9y6o0XiVATI";
    private static final String CLIENT_SECRET = "ECcJS5UVXSRL9HfU-eNRIuU7KrS8TFVldf8KrrTDwaBBPi0YZyj4_dAqUo4lVE2tqrmOEecDmDb6I0fB";
    private static final String MODE = "sandbox";

    public String authorizePayment(List<CartItemDTO> cartItemDTOS, CartItemDTO cartItemDTO, HttpServletRequest request) throws PayPalRESTException {
        Payer payer = getPayerInfomation();
        RedirectUrls redirectUrls = getRedirectURLs(request);
        List<Transaction> listTransaction = getTransactionInfomation(cartItemDTOS,cartItemDTO);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction)
                .setRedirectUrls(redirectUrls)
                .setPayer(payer)
                .setIntent("sale");
        APIContext apiContext = new APIContext(CLIENT_ID,CLIENT_SECRET,MODE);
        Payment approvedPayment =  requestPayment.create(apiContext);
        System.out.println(approvedPayment);
        return getApprovalLink(approvedPayment);
    }
    private String getApprovalLink(Payment approvedPayment){
        List<Links> links =  approvedPayment.getLinks();
        String approvalLink = null;
        for(Links link : links){
            if(link.getRel().equalsIgnoreCase("approval_url")){
                approvalLink =link.getHref();
            }
        }
        return approvalLink;
    }
    private List<Transaction> getTransactionInfomation( List<CartItemDTO> cartItems, CartItemDTO cartItemDTO){
        Double subtotal = 0.0 ;
        Double total = 0.0;

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();
        for(CartItemDTO cartItem : cartItems){
            Item item = new Item();
            item.setCurrency("USD")
                    .setName(cartItem.getProductName())
                    .setPrice(String.format(Locale.US ,"%.2f",convertVndToUsd(cartItem.getProductPrice())))
                    .setQuantity(cartItem.getQuantity());
            items.add(item);
            Double itemPrice = Double.valueOf(String.format(Locale.US, "%.2f", convertVndToUsd(cartItem.getProductPrice())));
            subtotal += itemPrice * Integer.parseInt(cartItem.getQuantity());
            System.out.println(subtotal);

        }

        Details details = new Details();
        details.setSubtotal(String.format(Locale.US,"%.2f",subtotal));

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal((String.format(Locale.US, "%.2f",subtotal)));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("ChronoLux Watch Purchase");

        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<Transaction>();
        listTransaction.add(transaction);
        return listTransaction;
    }

    private RedirectUrls getRedirectURLs(HttpServletRequest request) {
        RedirectUrls redirectUrls = new RedirectUrls();
        String total =  request.getParameter("total");
        String subtotal =  request.getParameter("subtotal");
        redirectUrls.setCancelUrl( GetSiteURLUtil.getSiteURL(request)+"/checkout?subtotal="+subtotal+"&total="+total);

        redirectUrls.setReturnUrl(GetSiteURLUtil.getSiteURL(request)+"/checkout/review");


        return  redirectUrls;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID,CLIENT_SECRET,MODE);
       return Payment.get(apiContext,paymentId);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        Payment payment = new Payment().setId(paymentId);
        APIContext apiContext =new APIContext(CLIENT_ID,CLIENT_SECRET,MODE);
        return payment.execute(apiContext,paymentExecution);
    }
    private Payer getPayerInfomation() {
        Payer payer =  new Payer();
        payer.setPaymentMethod("paypal");
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("DUC")
                .setLastName("HUY")
                .setEmail("damduc.huy@company.com");
        payer.setPayerInfo(payerInfo);
        return payer;
    }


    public static Double convertVndToUsd(String vndAmount) {
        try {
            Double amountVnd = Double.parseDouble(vndAmount);
            return amountVnd / SystemConstant.EXCHANGE_RATE;
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng số: " + e.getMessage());
            return 0.0;
        }
    }
}
