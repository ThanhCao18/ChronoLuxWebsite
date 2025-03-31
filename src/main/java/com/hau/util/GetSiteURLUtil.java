package com.hau.util;

import javax.servlet.http.HttpServletRequest;

public class GetSiteURLUtil {
    public static String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");

    }
}
