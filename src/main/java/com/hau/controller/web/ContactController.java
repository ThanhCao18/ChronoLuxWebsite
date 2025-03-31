package com.hau.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contactPage() {
        ModelAndView mav = new ModelAndView("web/contact");
        return mav;
    }
}
