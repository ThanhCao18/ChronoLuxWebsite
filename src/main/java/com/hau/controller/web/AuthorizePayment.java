package com.hau.controller.web;

import com.hau.Enum.DeliveryStatus;

import com.hau.config.MailConfig;
import com.hau.constant.SystemConstant;
import com.hau.dto.*;
import com.hau.service.*;
import com.hau.service.impl.PaymentServices;
import com.hau.util.CartUtils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AuthorizePayment {

    @Autowired
    private UserService userService;
    @Autowired
    private BillService billService;
    @Autowired
    private ProductService productService;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CurrencyFormat currencyFormat;
    @PostMapping("/authorize_payment")
    public ModelAndView authorizePayment(@ModelAttribute CartItemDTO cartItemDTO, HttpServletRequest request,
                                         @AuthenticationPrincipal Authentication authentication
                                         ) throws IOException {


        UserDTO userDTO = null;
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        List<ProductDTO> productDTOList = productService.findAllByActive(true);
        CartDTO cartDTO = CartUtils.getCartByCookie(request.getCookies(), productDTOList);

        //
        if(authentication != null){
            userDTO = userService.getCurrentLoggedInCustomer(authentication);
        }
        cartItemDTOS =  CartUtils.getCartItemByAuthentication(cartDTO,userDTO);
        ModelAndView mav = new ModelAndView();
        try{
            PaymentServices paymentServices = new PaymentServices();

            String approvalLink = paymentServices.authorizePayment(cartItemDTOS,cartItemDTO,request);
            mav = new ModelAndView("redirect:"+approvalLink);

        }
        catch (PayPalRESTException ex){
            ex.printStackTrace();
            mav = new ModelAndView("redirect:/checkout");
            mav.addObject("error","Invalid Payment Details");
        }
        return mav;
    }
    @PostMapping("/execute_payment")
    public ModelAndView ExecutePayment (@RequestParam("paymentId") String paymentId,
                                        @RequestParam("PayerID") String payerId,
                                        @AuthenticationPrincipal Authentication authentication,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws MessagingException, UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();

        try{
            UserDTO userDTO =  userService.getCurrentLoggedInCustomer(authentication);
            List<ProductDTO> productDTOList = productService.findAllByActive(true);
            String txt = "";
            CartDTO cartDTO = CartUtils.getCartByCookie(request.getCookies(), productDTOList);


            PaymentServices paymentServices = new PaymentServices();

            Payment payment = paymentServices.executePayment(paymentId,payerId);
            if(Objects.isNull(payment)) throw new RuntimeException("Thanh toán không thành công");

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().getFirst();
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
            Amount amount = transaction.getAmount();

            BillDTO billDTO = BillConverter(shippingAddress,payerInfo,amount,transaction,userDTO);

            BillDTO bill =  billService.save(billDTO);
            List<CartItemDTO> cartItemDTOS = CartUtils.getCartItemByAuthentication(cartDTO, userDTO);
            MailConfig.sendEmailAsync(bill.getEmail(),cartItemDTOS,bill,mailSender,currencyFormat);
            MailConfig.sendEmailToAdminAsync(SystemConstant.EMAIL_ADMIN,cartItemDTOS,bill,mailSender,currencyFormat);

            CartUtils.DeleteCartItemByAuthentication(userDTO,cartDTO,txt,response);
            mav = new ModelAndView("redirect:/checkout/success");

        } catch (PayPalRESTException ex){
            ex.printStackTrace();
            mav = new ModelAndView("redirect:/checkout") ;

        }

        return mav;
    }

    private BillDTO BillConverter(ShippingAddress shippingAddress, PayerInfo payerInfo , Amount amount , Transaction transaction,  UserDTO userDTO){
        ////

        BillDTO billDTO = new BillDTO();
        billDTO.setStreet(shippingAddress.getLine1());
        billDTO.setWard(shippingAddress.getCity());
        billDTO.setDistrict(shippingAddress.getState());
        billDTO.setCity(shippingAddress.getCountryCode());
        billDTO.setEmail(payerInfo.getEmail());
        billDTO.setStatus(SystemConstant.PAYMENT_SUCCESS);
        billDTO.setDeliveryStatus(DeliveryStatus.PENDING);
        billDTO.setPhone(payerInfo.getPhone());
        billDTO.setDisplayName(payerInfo.getLastName() +" "+ payerInfo.getFirstName());


        billDTO.setUsername(Optional.ofNullable(userDTO)
                .map(UserDTO::getUserName)
                .orElse(null));

        double subtotal =  Double.parseDouble(amount.getDetails().getSubtotal()) * 24000;
        double total = Double.parseDouble(amount.getTotal()) * 24000;


        billDTO.setSubtotal(subtotal);
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        for(Item item : transaction.getItemList().getItems()){
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setQuantity ( Integer.parseInt(item.getQuantity()));
            if(userDTO != null) {
                cartItemDTO.setUsername(userDTO.getUserName());
            }
            cartItemDTO.setProductName(item.getName());
            cartItemDTOS.add(cartItemDTO);
        }
        billDTO.setPaymentMethod(SystemConstant.PAYMENT_METHOD_PAYPAL);
        billDTO.setTotal(total);
        billDTO.setCartItemDTOS(cartItemDTOS);
        return  billDTO;
    }

}
