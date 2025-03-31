package com.hau.util;

import com.hau.dto.CartDTO;
import com.hau.dto.CartItemDTO;
import com.hau.dto.ProductDTO;
import com.hau.dto.UserDTO;
import com.hau.service.UserService;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CartUtils {

    public static CartDTO getCartByCookie(Cookie[] arr , List<ProductDTO> productDTOList){
        CartDTO cartDTO = null;
        String txt = "";
        if(arr != null){
            for(Cookie c : arr){
                if(c.getName().equals("cart")){
                    txt = c.getValue();
                }
            }
        }
        cartDTO = new CartDTO(txt,productDTOList);
        return cartDTO;
    }
    public static List<CartItemDTO> getCartItemByAuthentication( CartDTO cartDTO, UserDTO userDTO){
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        if(userDTO != null){
            for(CartItemDTO cartItem : cartDTO.getCartItemDTOS()){
                if(cartItem.getUsername().equals(userDTO.getUserName())){
                    if(cartItem.getProductQuantity() < Integer.parseInt(cartItem.getQuantity())){
                        cartItem.setQuantity(cartItem.getProductQuantity());
                    }
                    cartItemDTOs.add(cartItem);
                }
            }
        } else {
            for(CartItemDTO cartItem : cartDTO.getCartItemDTOS()){
                if(cartItem.getUsername().equals("null") ){
                    if(cartItem.getProductQuantity() < Integer.parseInt(cartItem.getQuantity())){
                        cartItem.setQuantity(cartItem.getProductQuantity());
                    }
                    cartItemDTOs.add(cartItem);
                }
            }
        }
        return  cartItemDTOs;
    }


    public static CartDTO getCartByCookieAndDeleteCookie(Cookie[] arr , List<ProductDTO> productDTOList, String txt, HttpServletResponse response) {
        CartDTO cartDTO = null;
        if(arr != null){
            for(Cookie c : arr){
                if(c.getName().equals("cart")){
                    txt = c.getValue();
                    c.setPath("/ChronoLuxWeb");
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }
        cartDTO = new CartDTO(txt,productDTOList);
        return cartDTO;
    }
    public static void DeleteCartItemByProductIdAndAuthentication( UserDTO userDTO,CartDTO cartDTO,String txt,HttpServletResponse response,long productId){
        if(userDTO != null){
            cartDTO.getCartItemDTOS().removeIf(cartItem -> cartItem.getUsername().equals(userDTO.getUserName()) && cartItem.getProductId() == productId);
        } else {
            cartDTO.getCartItemDTOS().removeIf(cartItem -> cartItem.getUsername().equals("null") && cartItem.getProductId() == productId);
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
    }

    public static void DeleteCartItemByAuthentication( UserDTO userDTO,CartDTO cartDTO,String txt,HttpServletResponse response){
        if(userDTO != null){
            cartDTO.getCartItemDTOS().removeIf(cartItem -> cartItem.getUsername().equals(userDTO.getUserName()));
        } else {
            cartDTO.getCartItemDTOS().removeIf(cartItem -> cartItem.getUsername().equals("null"));
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
    }
    public static int GetTotalCartItemByAuthentication(UserDTO userDTO,CartDTO cartDTO){
        return getCartItemByAuthentication(cartDTO,userDTO).size();
    }
}
