package com.hau.controller.web;

import com.hau.Enum.DeliveryStatus;
import com.hau.config.MailConfig;
import com.hau.constant.SystemConstant;
import com.hau.dto.*;
import com.hau.service.*;
import com.hau.service.impl.PaymentVnpayService;
import com.hau.util.CartUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.mail.MessagingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
public class PaymentVNPayController {
    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CurrencyFormat currencyFormat;
    @Autowired
    private PaymentVnpayService paymentVnpayService;
    @GetMapping("/payment/create-payment")
    public String createPayment(HttpServletRequest request,
                                @ModelAttribute BillDTO billDTO) throws UnsupportedEncodingException {
        String paymentUrl = paymentVnpayService.createPayMent(request,billDTO);
        return "redirect:"+paymentUrl;
    }

    @GetMapping("/payment/execute-payment")
    public String CheckoutSuccess(HttpServletRequest request,
                                  @AuthenticationPrincipal Authentication authentication,
                                  HttpServletResponse response) throws UnsupportedEncodingException, MessagingException {
        UserDTO userDTO = null;
        HttpSession session = request.getSession();
        // check signValue
        String signValue = paymentVnpayService.getSignValue(request);
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {

                BillDTO billDTO = (BillDTO) session.getAttribute("bill");
                List<ProductDTO> productDTOList = productService.findAllByActive(true);
                String txt = "";

                userDTO = userService.getCurrentLoggedInCustomer(authentication);
                billDTO.setUsername(Optional.ofNullable(userDTO)
                        .map(UserDTO::getUserName)
                        .orElse(null));

                // dat duoi setUsername
                CartDTO cartDTO = CartUtils.getCartByCookie(request.getCookies(), productDTOList);
                billDTO.setCartItemDTOS(CartUtils.getCartItemByAuthentication(cartDTO,userDTO));
                //
                billDTO.setStatus(SystemConstant.PAYMENT_SUCCESS);
                billDTO.setDeliveryStatus(DeliveryStatus.PENDING);
                BillDTO bill =  billService.save(billDTO);

                List<CartItemDTO> cartItemDTOS = CartUtils.getCartItemByAuthentication(cartDTO, userDTO);
                MailConfig.sendEmailAsync(bill.getEmail(),cartItemDTOS,bill,mailSender,currencyFormat);
                MailConfig.sendEmailToAdminAsync(SystemConstant.EMAIL_ADMIN,cartItemDTOS,bill,mailSender,currencyFormat);
                CartUtils.DeleteCartItemByAuthentication(userDTO,cartDTO,txt,response);
                session.removeAttribute("bill");
                return "redirect:/checkout/success";
            } else if("24".equals(request.getParameter("vnp_ResponseCode")) ) {
                session.removeAttribute("bill");
                return "redirect:/cart";
            }
        } else {
            System.out.print("Chu ky khong hop le");
            session.removeAttribute("bill");
            return "redirect:/cart";
        }
        return "redirect:/cart";
    }

}
