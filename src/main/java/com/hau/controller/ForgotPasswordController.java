package com.hau.controller;

import com.hau.dto.UserDTO;
import com.hau.exception.CustomerNotFoundException;
import com.hau.service.UserService;
import com.hau.util.GetSiteURLUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;
    @RequestMapping(value = "/login/forgot-password",method = RequestMethod.GET)
    public ModelAndView registerPage(){
        ModelAndView mav = new ModelAndView("login/forgot-password");
        return mav;
    }
    @RequestMapping(value = "/login/forgot-password",method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(HttpServletRequest request, Model model){
        ModelAndView mav = new ModelAndView("login/forgot-password");
        String email = request.getParameter("email");
        String token = RandomStringUtils.randomAlphanumeric(45);
        try {
            userService.updateResetPasswordToken(token,email);
            String resetPasswordLink = GetSiteURLUtil.getSiteURL(request)+ "/login/reset_password?token="+token;
            sendEmail(email,resetPasswordLink);
            model.addAttribute("message","Chúng tôi đã gửi link thiết lập lại mật khẩu cho bạn. Hãy kiểm tra");
        } catch (CustomerNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Đã có lỗi khi gửi Email");
        }

        return mav;
    }

    @GetMapping("/login/reset_password")
    public ModelAndView showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
        String view = "login/reset-password";
        UserDTO user =  userService.findOneByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (user == null) {
            model.addAttribute("message", "Token không hợp lệ");
            view = "redirect:/login/forgot-password";
        }
        return new ModelAndView(view);
    }
    @PostMapping("/login/reset_password")
    public String processResetPassword(@RequestParam(value = "token") String token, @RequestParam(value = "password") String password,Model model){
        UserDTO user = userService.findOneByResetPasswordToken(token);
        if (user == null) {
            model.addAttribute("message", "Token không hợp lệ");
        }
        else{
            userService.updatePassword(user,password);
            model.addAttribute("message","Bạn đã đổi mật khẩu thành công!");
        }
        return "/login/reset-password";
    }


    public void sendEmail(String email , String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("contact@chronolux.com","ChronoLux support");
        helper.setTo(email);
        helper.setSubject("Đây là link đổi mật khẩu của bạn");
        String content = "<p>Xin chào,</p>"
                + "<p>Bạn vừa gửi yêu cầu để đổi mật khẩu.</p>"
                + "<p>Hãy click vào link dưới đây để cài đặt mật khẩu:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Đổi mật khẩu</a></p>"
                + "<br>"
                + "<p>bỏ qua email này nếu bạn đã nhớ mật khẩu, "
                + "hoặc không phải là bạn yêu cầu.</p>";
        helper.setText(content,true);
        mailSender.send(message);
    }
}
