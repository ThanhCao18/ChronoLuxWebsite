package com.haui.controller.web;

import com.haui.Enum.DeliveryStatus;

import com.haui.Enum.VoucherType;
import com.haui.config.MailConfig;
import com.haui.constant.SystemConstant;
import com.haui.dto.*;
import com.haui.service.*;
import com.haui.service.impl.PaymentServices;
import com.haui.util.CartUtils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// neu total cua product snapshot khac voi total hien tai thi lay total snapshot
@Controller
public class CheckoutController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private BillService billService;
    @Autowired
    private ProductService productService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CurrencyFormat currencyFormat;
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView checkoutPage(@RequestParam("subtotal") long subtotal,
                                     @RequestParam( value = "voucherCode",required = false) String voucherCode,
                                     @RequestParam("total") long total,
                                     @AuthenticationPrincipal Authentication authentication,
                                     HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("web/checkout");
        UserDTO userDTO = null;
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        List<ProductDTO> productDTOList = productService.findAllByActive(true);
        CartDTO cartDTO = CartUtils.getCartByCookie(request.getCookies(), productDTOList);
        //
        if(authentication != null){
            userDTO = userService.getCurrentLoggedInCustomer(authentication);
            mav.addObject("user",userDTO);
        }
        cartItemDTOS =  CartUtils.getCartItemByAuthentication(cartDTO,userDTO);

        mav.addObject("cartItems", cartItemDTOS);
        mav.addObject("subtotal",subtotal);
        if(voucherCode != null){
            mav.addObject("voucher",voucherService.findOneByCode(voucherCode));

        }

        mav.addObject("totalPrice",total);
        return mav;
    }
    @PostMapping("/checkout")
    public String addBill(@ModelAttribute BillDTO billDTO,
                          @AuthenticationPrincipal Authentication authentication,
                          HttpServletRequest request,
                          HttpServletResponse response) throws MessagingException, IOException {
        UserDTO userDTO = null;
        List<ProductDTO> productDTOList = productService.findAllByActive(true);
        String txt = "";

        if(authentication != null){
            userDTO = userService.getCurrentLoggedInCustomer(authentication);
            billDTO.setUsername(userDTO.getUserName());
        }
        else {
            billDTO.setUsername(null);
        }
        CartDTO cartDTO = CartUtils.getCartByCookieAndDeleteCookie(request.getCookies(), productDTOList,txt,response);
        billDTO.setCartItemDTOS(CartUtils.getCartItemByAuthentication(cartDTO,userDTO));
        billDTO.setStatus(SystemConstant.PAYMENT_PENDING);
        billDTO.setDeliveryStatus(DeliveryStatus.PENDING);
        BillDTO billDTO1 = billService.save(billDTO);
        VoucherDTO voucherDTO = voucherService.findOneByCode(billDTO1.getVoucherCode());

        if(voucherDTO != null){ // kiem tra co dung voucher khong
            if(voucherDTO.getVoucherType().equals(VoucherType.PRIVATE))    // kiem tra co phai voucher khach hang moi hay khong
                voucherService.setExpiredDate(billDTO1.getVoucherCode(), LocalDateTime.now()); // neu su dung thi het han voucher
        }

        var cartItemDTOS = CartUtils.getCartItemByAuthentication(cartDTO, userDTO);

        MailConfig.sendEmailAsync(billDTO1.getEmail(),cartItemDTOS,billDTO1,mailSender,currencyFormat);
        MailConfig.sendEmailToAdminAsync(SystemConstant.EMAIL_ADMIN,cartItemDTOS,billDTO1,mailSender,currencyFormat);

        CartUtils.DeleteCartItemByAuthentication(userDTO,cartDTO,txt,response);
        return "redirect:/checkout/success";
    }
    @GetMapping("/checkout/review")
    public ModelAndView ReviewPayment(@RequestParam("paymentId") String paymentId,
                                      @RequestParam("PayerID") String payerId
                                      ){
        ModelAndView mav = new ModelAndView();
        try{
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.getPaymentDetails(paymentId);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().getFirst();
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

            mav = new ModelAndView("web/checkout-review");
            mav.addObject("paymentId",paymentId);
            mav.addObject("payerId",payerId);
            mav.addObject("payer",payerInfo);
            mav.addObject("transaction",transaction);
            mav.addObject("shippingAddress",shippingAddress);


        }catch (PayPalRESTException ex){
                ex.printStackTrace();
                mav = new ModelAndView("web/checkout");
                mav.addObject("error","Could not get payment details");
        }
        return mav;
    }

    @GetMapping("/checkout/success")
    public String CheckoutSuccess()  {

        return "web/checkout-success";
    }

}






