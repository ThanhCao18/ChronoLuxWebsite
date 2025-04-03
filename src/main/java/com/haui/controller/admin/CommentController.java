package com.haui.controller.admin;

import com.haui.dto.CommentDTO;
import com.haui.service.CommentService;
import com.haui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/comment/view")
    public String viewComment(@RequestParam("id") Long productId,
                              @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                              @AuthenticationPrincipal Authentication authentication,
                              Model model) {
        List<CommentDTO> commentDTOList = commentService.getByProductIdOrderByCreateDateDesc(productId);
        if(commentDTOList.isEmpty()){
            return "redirect:/admin/products?page=" + currentPage;
        }
        model.addAttribute("commentList", commentService.getByProductIdOrderByCreateDateDesc(productId));
        model.addAttribute("totalComments", commentDTOList.size());
        List<Boolean> isLike = commentDTOList.stream()
                .map(comment -> commentService.isLike(comment.getId(),
                        userService.getCurrentLoggedInCustomer(authentication).getId())).toList();
        model.addAttribute("isLike", isLike);
        model.addAttribute("productName", commentDTOList.getFirst().getProductName());
        model.addAttribute("productId", commentDTOList.getFirst().getProductId());
        model.addAttribute("avgRating", commentService.calculateAverageRating(productId));
        model.addAttribute("currentPage", currentPage);
        return "admin/comment-view";
    }

    @GetMapping("/admin/comment/delete")
    public String deleteComment(@RequestParam("id") Long id,
                                @RequestParam("productId") Long productId,
                                @RequestParam("currentPage") int currentPage) {
        commentService.delete(id);
        return "redirect:/admin/comment/view?id=" + productId + "&currentPage=" + currentPage;
    }

}
