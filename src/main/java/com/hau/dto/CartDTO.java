package com.hau.dto;

import com.hau.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CartDTO extends  AbstractDTO<CartDTO>{

    List<CartItemDTO> cartItemDTOS ;
    public CartDTO(){
        cartItemDTOS = new ArrayList<>();
    }

    public List<CartItemDTO> getCartItemDTOS() {
        return cartItemDTOS;
    }

    public void setCartItemDTOS(List<CartItemDTO> cartItemDTOS) {
        this.cartItemDTOS = cartItemDTOS;
    }


    private Integer getQuantityByIdAndUserName(Integer id, String username){
        return Integer.parseInt(getItemByIdAndUserName(id,username).getQuantity());
    }
    private  CartItemDTO getItemByIdAndUserName(long id, String username){
        for(CartItemDTO cartItemDTO : cartItemDTOS){
            if((cartItemDTO.getProductId() == id) && cartItemDTO.getUsername().equals(username)) {
                return cartItemDTO;
            }
        }
        return null;
    }
    public void addItem(CartItemDTO cartItemDTO){
        if(getItemByIdAndUserName(cartItemDTO.getProductId(),cartItemDTO.getUsername()) != null){
            CartItemDTO m = getItemByIdAndUserName(cartItemDTO.getProductId(),cartItemDTO.getUsername());
            m.setQuantity(Integer.parseInt( m.getQuantity()) + Integer.parseInt(cartItemDTO.getQuantity()));
        }
        else{
            cartItemDTOS.add(cartItemDTO);
        }
    }
    public void removeItem(int id,String username){
        if(getItemByIdAndUserName(id,username) != null){
            cartItemDTOS.remove(getItemByIdAndUserName(id,username));
        }
    }
    public long getTotalByUser(UserDTO user){
        long t = 0;
        String username;
        if (user == null){
            username = "null";
        }
        else{
            username = user.getUserName();
        }
        for(CartItemDTO item : cartItemDTOS){
            if(item.getUsername().equals(username) ){
                t += Integer.parseInt(item.getQuantity())  * Long.parseLong(item.getProductPrice()) ;
            }
        }
        return  t;
    }
    private ProductDTO getProductById(int id,List<ProductDTO> list){
        for(ProductDTO i : list){
            if(i.getId() == id){
                return i;
            }
        }
        return  null;
    }
    public CartDTO(String txt,List<ProductDTO> list){
        cartItemDTOS = new ArrayList<>();
        if(txt != null && txt.length() != 0){
            String [] s = txt.split("\\|");
            for(String i : s){
                String [] n = i.split(":");
                String userName = n[0];
                int productId = Integer.parseInt(n[1]);
                Integer quantity = Integer.parseInt(n[2]) ;
                ProductDTO productDTO = getProductById(productId,list);
                CartItemDTO cartItemDTO = new CartItemDTO(quantity,productDTO.getName(),productDTO.getPrice() ,productId,productDTO.getImgUrl(),userName,productDTO.getStock(),productDTO.getWatchType());
                addItem(cartItemDTO);
            }
        }
    }

}
