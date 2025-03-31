package com.hau.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePasswordUtil {

    public static String encode(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        return encodedPassword;
    }
}
