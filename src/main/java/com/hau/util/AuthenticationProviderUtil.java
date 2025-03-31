package com.hau.util;

import com.hau.dto.*;
import javassist.compiler.Parser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationProviderUtil {
    public static void  GrantedPermissionO2Auth(Object account){
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (account instanceof UserGoogleDto) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER_GOOGLE"));
            UserGoogleDto userGoogleDto = (UserGoogleDto) account;
            CustomerO2Auth user = new CustomerO2Auth(userGoogleDto.getUserName(), "", true, true, true, true, authorities);

            user.setFullName(userGoogleDto.getGiven_name());
            user.setFirstName(userGoogleDto.getGiven_name());
            user.setSurName(userGoogleDto.getFamily_name());
            user.setImgUrl(userGoogleDto.getPicture());
            user.setEmail(userGoogleDto.getEmail());
            user.setRoleCode("ROLE_USER_GOOGLE");
            BigInteger bigNumber = new BigInteger(userGoogleDto.getId());
            user.setUid(bigNumber.longValue());
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else if (account instanceof UserFaceBookDto) {
            // Xử lý nếu object là kiểu AnotherClass
            UserFaceBookDto userFaceBookDto = (UserFaceBookDto) account;
            authorities.add(new SimpleGrantedAuthority("ROLE_USER_FACEBOOK"));
            CustomerO2Auth user = new CustomerO2Auth(userFaceBookDto.getUserName(), "", true, true, true, true, authorities);
            user.setFullName(userFaceBookDto.getName());
            //
            String[] parts = userFaceBookDto.getName().split(" ");
            String firstName = parts[parts.length -1];
            user.setFirstName(firstName);
            //
            int lastSpaceIndex = userFaceBookDto.getName().lastIndexOf(" ");
            String surName = userFaceBookDto.getName().substring(0,lastSpaceIndex);
            user.setSurName(surName);
            //
            user.setImgUrl(userFaceBookDto.getImgUrl());
            user.setEmail(userFaceBookDto.getEmail());
            user.setUid(userFaceBookDto.getId());
            user.setRoleCode("ROLE_USER_FACEBOOK");

            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new IllegalArgumentException("Unsupported object type");
        }
    }
    public static void GrantedPermission(UserDTO userDTO) {
        String encodedPassword = EncodePasswordUtil.encode(userDTO.getPassword());
        MyUser user = new MyUser(userDTO.getUserName(), encodedPassword, true, true, true, true, SecurityUtil.getPrincipal().getAuthorities());
        user.setFullName(userDTO.getFullName());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
