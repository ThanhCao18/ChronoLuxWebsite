package com.hau.controller;

import com.hau.dto.UserDTO;
import com.hau.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.hau.service.UserService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;

@Controller

public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    private static final String UPLOAD_DIR = "user-logos";
    private static final String DEFAULT_USER_AVATAR_DIR = "account-representation";

    @PostMapping(value="/login/add" )
    public String addNewUser(@ModelAttribute UserDTO user,
                             @RequestParam("img") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (multipartFile.isEmpty()) {
            multipartFile = fileService.handleDefaultFile("user.png", DEFAULT_USER_AVATAR_DIR);
            String avatar = fileService.saveFile(multipartFile, UPLOAD_DIR);
            user.setImgUrl(avatar);
        }
        else{
            String avatar = fileService.saveFile(multipartFile, UPLOAD_DIR);
            user.setImgUrl(avatar);
        }
        userService.save(user, "user");
        return "redirect:/login"; // Redirect lại sau khi xử lý
    }

    @GetMapping("/login/register")
    public String showRegisterPage(Model model){
        return "login/register";
    }
}
