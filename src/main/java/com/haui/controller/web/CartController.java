package com.haui.controller.web;

import com.haui.Enum.VoucherType;
import com.haui.dto.*;
import com.haui.service.CartItemService;
import com.haui.service.ProductService;
import com.haui.service.UserService;
import com.haui.service.VoucherService;
import com.haui.util.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CartController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private ProductService productService;
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public ModelAndView cartPage(@AuthenticationPrincipal Authentication authentication,
                                 @RequestParam(value = "code",required = false) String code,
                                 @RequestParam(value = "alert",required = false) String alert,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        String txt = "";

        ModelAndView mav = new ModelAndView("web/cart");

        UserDTO userDTO = null;
        List<VoucherDTO> voucherValids = new ArrayList<>(voucherService.findByType(VoucherType.PUBLIC));

        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        VoucherDTO voucherDTO = null;
        List<ProductDTO> productDTOList = productService.findAllByActive(true);
        CartDTO cartDTO = CartUtils.getCartByCookieAndDeleteCookie(request.getCookies(), productDTOList,txt,response);

        //
        if(authentication != null){
            userDTO = userService.getCurrentLoggedInCustomer(authentication);
            voucherDTO = voucherService.findOneById(userDTO.getVoucherId());
            if(voucherDTO != null){
                voucherValids.add(voucherDTO);
            }


        }
        String error = null;
            for(CartItemDTO cartItem : cartDTO.getCartItemDTOS()){
                    if(cartItem.getProductQuantity() < Integer.parseInt(cartItem.getQuantity())){
                        cartItem.setQuantity(cartItem.getProductQuantity());
                        error = "Số lượng bạn chọn đã đặt mức tối đa của sản phẩm này";
                    }
                    if(Integer.parseInt(cartItem.getQuantity()) != 0){
                        cartItemDTOS.add(cartItem);
                    }
            }

        List<CartItemDTO> items = cartItemDTOS;
        if(items.size()>0){
            txt = items.get(0).getUsername()+":"+items.get(0).getProductId()+":"+ items.get(0).getQuantity();
            for(int i = 1 ; i<items.size(); i++) {
                txt += "|" +items.get(i).getUsername() +":"+items.get(i).getProductId() +":"+ items.get(i).getQuantity();
            }
        }
        Cookie c  = new Cookie("cart",txt);
        c.setMaxAge(2*24*60*60);
        c.setPath("/ChronoLuxWeb");
        response.addCookie(c);
        List<Cookie> cookieList = new ArrayList<>();
        cookieList.add(c);
        Cookie[] updatedCookies = cookieList.toArray(new Cookie[cookieList.size()]);
        CartDTO cartDTO1 = CartUtils.getCartByCookie(updatedCookies , productDTOList);
        cartItemDTOS = CartUtils.getCartItemByAuthentication(cartDTO1,userDTO);

        mav.addObject("validVouchers",voucherValids);
        mav.addObject("error",error);
        mav.addObject("cartItems",cartItemDTOS);
        mav.addObject("totalPrice",cartDTO.getTotalByUser(userDTO));
        mav.addObject("alert",alert);
        System.out.println(alert);

        if(code != null) {
        voucherDTO = voucherService.findOneByCode(code);
        // xu ly logic ap voucher
         renderVoucher(voucherDTO,mav,authentication,userDTO);
        }

        return mav;
    }
    @GetMapping("/cart/add")
    public String addToCart(@RequestParam("productId") long productId,
                            @RequestParam("quantity") int quantity,
                            @AuthenticationPrincipal Authentication authentication,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            Model model){
        UserDTO userDTO =  new UserDTO();
        if(authentication != null){
           userDTO  = userService.getCurrentLoggedInCustomer(authentication);
        }

        String txt = "";
        Cookie [] arr = request.getCookies();

        if(arr != null){
            for(Cookie o : arr){
                if(o.getName().equals("cart")){
                    txt = o.getValue();
                    o.setMaxAge(0);
                    response.addCookie(o);
                }
            }
        }

        if(txt.isEmpty()){
            txt = userDTO.getUserName()+":"+productId+":"+quantity;
        }else {
            txt = txt + "|"+userDTO.getUserName()+":"+productId+":"+quantity;
        }
            Cookie c = new Cookie("cart",txt);
            c.setMaxAge(2*24*60*60);
            c.setPath("/ChronoLuxWeb");
            response.addCookie(c);

        return "redirect:/cart?alert=true";
    }
    @GetMapping("/cart/update")
    public String updateQuantityCart(@RequestParam("productId") long productId,
                                     @RequestParam("quantity") int quantity,
                                     @AuthenticationPrincipal Authentication authentication,
                                     HttpServletResponse response,
                                     HttpServletRequest request){
        UserDTO userDTO;
        List<ProductDTO> productDTOList = productService.findAllByActive(true);
        String txt = "";
        CartDTO cartDTO = CartUtils.getCartByCookieAndDeleteCookie(request.getCookies(), productDTOList,txt,response);
        if(authentication != null){
            userDTO = userService.getCurrentLoggedInCustomer(authentication);
            for(CartItemDTO cartItemDTO : cartDTO.getCartItemDTOS()){
                if((cartItemDTO.getUsername().equals(userDTO.getUserName())) && cartItemDTO.getProductId() == productId){
                    if(quantity < 1){
                        cartItemDTO.setQuantity(1);
                    }
                    else{
                        cartItemDTO.setQuantity(quantity);
                    }

                }
            }
        } else {
            for(CartItemDTO cartItemDTO : cartDTO.getCartItemDTOS()){
                if((cartItemDTO.getUsername().equals("null")) && cartItemDTO.getProductId() == productId){
                    if(quantity < 1){
                        cartItemDTO.setQuantity(1);
                    }
                    else{
                        cartItemDTO.setQuantity(quantity);
                    }
                }
            }
        }
        List<CartItemDTO> items = cartDTO.getCartItemDTOS();
        txt = "";
        if(items.size()>0){
            txt = items.get(0).getUsername()+":"+items.get(0).getProductId()+":"+ items.get(0).getQuantity();
            for(int i = 1 ; i<items.size(); i++) {
                txt += "|" +items.get(i).getUsername() +":"+items.get(i).getProductId() +":"+ items.get(i).getQuantity();

            }
        }
        Cookie c  = new Cookie("cart",txt);
        c.setMaxAge(2*24*60*60);
        c.setPath("/ChronoLuxWeb");
        response.addCookie(c);
        return "redirect:/cart";
    }
    @GetMapping("/cart/del")
    public String deleleCart(@RequestParam("productId") long productId,
                             @AuthenticationPrincipal Authentication authentication,
                             HttpServletRequest request,
                             HttpServletResponse response){
        UserDTO userDTO = null;
        List<ProductDTO> productDTOList = productService.findAllByActive(true);
        String txt = "";

        CartDTO cartDTO = CartUtils.getCartByCookieAndDeleteCookie(request.getCookies(), productDTOList,txt,response);

        if(authentication != null){
            userDTO = userService.getCurrentLoggedInCustomer(authentication);
        }
        CartUtils.DeleteCartItemByProductIdAndAuthentication(userDTO,cartDTO,txt,response,productId);
        return "redirect:/cart";
    }
    @GetMapping("/cart/total")
    public ResponseEntity<Integer> getTotalCartItem(@AuthenticationPrincipal Authentication authentication, HttpServletRequest request){
        UserDTO userDTO = null;
        if(authentication != null){
            userDTO = userService.getCurrentLoggedInCustomer(authentication);
        }
        List<ProductDTO> productDTOList = productService.findAllByActive(true);
        CartDTO cartDTO = CartUtils.getCartByCookie(request.getCookies(),productDTOList);
        return ResponseEntity.ok(CartUtils.GetTotalCartItemByAuthentication(userDTO,cartDTO)) ;
    }

    private void renderVoucher(VoucherDTO voucherDTO, ModelAndView mav,Authentication authentication, UserDTO userDTO){
        if (voucherDTO != null) {
            if(voucherDTO.getVoucherType().equals(VoucherType.PUBLIC)){
                mav.addObject("voucher", voucherDTO);
            }
            else {
                if( authentication != null && Objects.equals(voucherDTO.getId(), userDTO.getVoucherId())){
                      mav.addObject("voucher", voucherDTO);
                }
                else
                   mav.addObject("InvalidVoucher"
                            , "Mã giảm giá không tồn tại. Vui lòng thử lại!");
            }
        }else{
            mav.addObject("InvalidVoucher"
                    , "Mã giảm giá không tồn tại. Vui lòng thử lại!");
        }
    }
}
