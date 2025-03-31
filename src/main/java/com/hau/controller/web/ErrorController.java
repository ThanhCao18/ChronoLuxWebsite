package com.hau.controller.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @GetMapping("/403")
    public String accessDenied() {
        return "web/403";
    }

    @GetMapping("/404")
    public String notFound() {
        return "web/404";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "web/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "web/403";
            }
        }
        return "web/404";
    }
}
