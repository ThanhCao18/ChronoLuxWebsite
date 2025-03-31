package com.hau.security;

import com.hau.util.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);

    }

    public String determineTargetUrl(Authentication authentication){
        String url = "";
        List<String> roles = SecurityUtil.getAuthorities(authentication);
        if(isAdmin(roles)){
            url = "/admin/home";
        }
        else if(isUser(roles)){
            url = "/home";
        }
        return url;
    }
    private boolean isAdmin(List<String> roles) {
        if(roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }
    private boolean isUser(List<String> roles) {
        if(roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }
}
