package com.hau.controller.admin;

import com.hau.dto.UserDTO;
import com.hau.service.CartItemService;
import com.hau.service.FileService;
import com.hau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    private static final String UPLOAD_DIR = "user-logos";
    private static final String DEFAULT_ADMIN_AVATAR_DIR = "account-representation";

    @GetMapping("/admin/admin-profile")
    public String  adminProfile(Model model, @AuthenticationPrincipal Authentication authentication){
        model.addAttribute("account",userService.getCurrentLoggedInCustomer(authentication));
        return "admin/admin-account-update";
    }

    @GetMapping("/admin/accounts")
    public String showAccounts(Model model,
                                    @RequestParam(defaultValue = "1") int adminPage,
                                    @RequestParam(defaultValue = "1") int userPage,
                                    @RequestParam(defaultValue = "100") int limit) {
        model.addAttribute("currentAdminPage", adminPage);
        model.addAttribute("adminAccountPage", userService.findAllAdminAccounts(adminPage, limit));
        model.addAttribute("currentUserPage", userPage);
        model.addAttribute("userAccountPage", userService.findAllUserAccounts(userPage, limit));
        return "admin/accounts-tab-view";
    }

    @GetMapping("/admin/account/create")
    public String showAdminCreateForm(Model model) {
        model.addAttribute("account", new UserDTO());
        return "admin/admin-account-add";
    }

    @PostMapping("/admin/account/save")
    public String saveAdminAccounts(@ModelAttribute("account") UserDTO account,
                                    @RequestParam("img")MultipartFile img,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) throws Exception {
        request.setCharacterEncoding("UTF-8");
        if(!img.isEmpty() && account.getId() == null){ // create new account, avatar != null
            String avatar = fileService.saveFile(img, UPLOAD_DIR);
            account.setImgUrl(avatar);
        }
        else if(img.isEmpty() && account.getId() == null){ // create new account, avatar == null
            img = fileService.handleDefaultFile("admin.png", DEFAULT_ADMIN_AVATAR_DIR);
            String avatar = fileService.saveFile(img, UPLOAD_DIR);
            account.setImgUrl(avatar);
        }
        else if(img.isEmpty() && account.getId() != null){ // update account, avatar == null
            UserDTO userDTO = userService.findOneByUserNameAndStatus(account.getUserName(), 1);
            account.setImgUrl(userDTO.getImgUrl());
        }
        else{ // update account, avatar != null
            String avatar = fileService.saveFile(img, UPLOAD_DIR);
            account.setImgUrl(avatar);
        }
        userService.save(account, "admin");
        redirectAttributes.addFlashAttribute("successMessage", "Cấp tài khoản Admin thành công");
        return "redirect:/admin/accounts";
    }

    @PostMapping("/admin/account/update")
    public String updateAdminAccounts(@ModelAttribute("account") UserDTO account,
                                        @RequestParam("img")MultipartFile img,
                                        @RequestParam Long id,
                                        HttpServletRequest request, RedirectAttributes redirectAttributes,
                                      @AuthenticationPrincipal Authentication authentication) throws Exception {
        request.setCharacterEncoding("UTF-8");
        if(!img.isEmpty() && account.getId() == null){ // create new account, avatar != null
            String avatar = fileService.saveFile(img, UPLOAD_DIR);
            account.setImgUrl(avatar);
        }
        else if(img.isEmpty() && account.getId() == null){ // create new account, avatar == null
            img = fileService.handleDefaultFile("admin.png", DEFAULT_ADMIN_AVATAR_DIR);
            String avatar = fileService.saveFile(img, UPLOAD_DIR);
            account.setImgUrl(avatar);
        }
        else if(img.isEmpty() && account.getId() != null){ // update account, avatar == null
            UserDTO userDTO = userService.findOneById(id);
            account.setImgUrl(userDTO.getImgUrl());
        }
        else if(!img.isEmpty() && account.getId() != null){ // update account, avatar != null
            String avatar = fileService.saveFile(img, UPLOAD_DIR);
            account.setImgUrl(avatar);
        }
        userService.update(account);
        /*userService.updateSecurityContext(account, authentication);*/
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin tài khoản Admin thành công");
        return "redirect:/admin/admin-profile";
    }

    @GetMapping("/admin/account/view")
    public String viewHistory(@RequestParam Long id,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int limit,
                              Model model) {
        model.addAttribute("account", userService.findOneById(id));
        model.addAttribute("history", cartItemService.findHistoryByUser(id, page, limit));
        model.addAttribute("currentPage", page);
        return "admin/account-bill-history-view";
    }
}
