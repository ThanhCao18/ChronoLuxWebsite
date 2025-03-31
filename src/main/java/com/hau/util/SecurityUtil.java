package com.hau.util;

import com.hau.dto.CustomerO2Auth;
import com.hau.dto.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtil {

    public static MyUser getPrincipal() {
        MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(myUser == null) {
            return null;
        }
        return myUser;
    }
    public static CustomerO2Auth getPrincipalO2Auth() {
        CustomerO2Auth myUser = (CustomerO2Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(myUser == null) {
            return null;
        }
        return myUser;
    }

    public static List<String> getAuthorities(Authentication authentication){
        List<String> results = new ArrayList<>();
        List<GrantedAuthority> authories = (List<GrantedAuthority>) authentication.getAuthorities();
        for(GrantedAuthority authority : authories) {
            results.add(authority.getAuthority());
        }
        return results;
    }
}
