package com.hau.controller.admin;

import com.hau.dto.PostDTO;
import com.hau.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.hau.service.PostService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    // For client (tu sua)
    @GetMapping("/home/posts")
    public String getPostsHomePage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "admin/post-view";
    }

    // For admin
    @GetMapping("/admin/posts")
    public String getPostsAdminPage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "admin/post-view";
    }

    @GetMapping("/admin/post/create")
    public String showCreatePostForm(Model mode) {
        mode.addAttribute("post", new PostDTO());
        return "admin/post-add";
    }

    @PostMapping("/admin/post/save")
    public String savePost(@ModelAttribute("post") PostDTO postDTO,
                           @RequestParam("img_file") MultipartFile img,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes) throws IOException {
        request.setCharacterEncoding("UTF-8");

        if (!img.isEmpty()) {
            String imgName = fileService.saveFile(img, "posts");
            postDTO.setImg(imgName);
        } else {
            postDTO.setImg(postService.getPostById(postDTO.getId()).getImg());
        }
        postService.savePost(postDTO);
        String message = (postDTO.getId() == null || postDTO.getId() == 0) ? "Thêm bài viết thành công" : "Cập nhật bài viết thành công";
        redirectAttributes.addFlashAttribute("successMessage", message);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/post/update")
    public String updatePost(@RequestParam("id") Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        return "admin/post-update";
    }

    @GetMapping("/admin/post/delete")
    public String deletePost(@RequestParam("id") Long id) throws IOException {
        postService.deletePostById(id);
        return "redirect:/admin/posts";
    }
}
