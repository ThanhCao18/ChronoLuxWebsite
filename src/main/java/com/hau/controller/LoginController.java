package com.hau.controller;

import com.hau.constant.SystemConstant;
import com.hau.dto.UserDTO;
import com.hau.dto.UserFaceBookDto;
import com.hau.dto.UserGoogleDto;
import com.hau.service.UserService;
import com.hau.util.AuthenticationProviderUtil;
import com.hau.util.UserFaceBookUtil;
import com.hau.util.UserGoogleUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public ModelAndView Login(){
        ModelAndView mav = new ModelAndView("login/sign-in");
        return  mav;
    }
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView mav = new ModelAndView("redirect:/login?accessDenied");
        return mav;
    }
    @RequestMapping(value = "/login-google", method = RequestMethod.GET)
    public ModelAndView LoginGoogle(@RequestParam String code) {
        ModelAndView mav = new ModelAndView("redirect:/home");
        try {
            String token = UserGoogleUtil.getToken(code);
            UserGoogleDto userGoogleDto = UserGoogleUtil.toUser(token).getUserGoogleDto();
            System.out.println(userGoogleDto);
            UserDTO user =  userService.findOneByEmailAndRoleCode(userGoogleDto.getEmail(),"ROLE_USER_GOOGLE");

            if(user == null){
                String userName = RandomStringUtils.randomAlphanumeric(10);
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(userGoogleDto.getEmail());
                userDTO.setFullName(userGoogleDto.getFamily_name() +" "+ userGoogleDto.getGiven_name());
                userDTO.setPassword(null);
                List<String> rolescode = new ArrayList<>();
                rolescode.add("ROLE_USER_GOOGLE");
                userDTO.setRoleCode(rolescode);
                userDTO.setStatus(SystemConstant.ACTIVE_STATUS);
                userDTO.setUserName(userName);
                userDTO.setImgUrl(userGoogleDto.getPicture());
                userService.save(userDTO, "user_google");
                userGoogleDto.setUserName(userName);
            }
            else{
                userGoogleDto.setUserName(user.getUserName());
            }
            AuthenticationProviderUtil.GrantedPermissionO2Auth(userGoogleDto);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mav;

    }
    @RequestMapping(value = "/login-facebook", method = RequestMethod.GET)
    public ModelAndView LoginFacebook(@RequestParam (value = "code",required = false) String code) {
        ModelAndView mav = new ModelAndView("redirect:/home");
        try {
            String token = UserFaceBookUtil.getToken(code);
            UserFaceBookDto userFaceBookDto = UserFaceBookUtil.toUser(token).getUserFaceBookDto();
            UserDTO user = userService.findOneByEmailAndRoleCode(userFaceBookDto.getEmail(),"ROLE_USER_FACEBOOK");
            if(user == null){
                String userName = RandomStringUtils.randomAlphanumeric(10);
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(userFaceBookDto.getEmail());
                userDTO.setFullName(userFaceBookDto.getName());
                userDTO.setPassword(null);
                List<String> rolescode = new ArrayList<>();
                rolescode.add("ROLE_USER_FACEBOOK");
                userDTO.setRoleCode(rolescode);
                userDTO.setStatus(SystemConstant.ACTIVE_STATUS);
                userDTO.setUserName(userName);
                userDTO.setImgUrl(userFaceBookDto.getImgUrl());
                userService.save(userDTO, "user_facebook");
                userFaceBookDto.setUserName(userName);
            }
            else{
                userFaceBookDto.setUserName(user.getUserName());
            }
            AuthenticationProviderUtil.GrantedPermissionO2Auth(userFaceBookDto);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mav;

    }
    @PostMapping("/check-username")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUsername(@RequestParam String userName) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.usernameExists(userName); // Kiểm tra trong cơ sở dữ liệu
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/check-email")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String Email) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(Email);
        boolean exists = userService.emailExits(Email); // Kiểm tra trong cơ sở dữ liệu
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

}
