package gm.servicedesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gm.servicedesk.dto.UserProfileUpdateReq;
import gm.servicedesk.model.User;
import gm.servicedesk.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User user, Model viewModel) {
        User userInfo = userService.find(user.getId());
        viewModel.addAttribute("user", userInfo);
        return "_update_profile_dialog.html";
    }

    @PostMapping("/profile")
    public String update_user_profile(@AuthenticationPrincipal User user, @Valid UserProfileUpdateReq form) {
        userService.update(user.getId(), form);
        return "redirect:/";
    }

    @PostMapping("/profile/updatepassword")
    public String update_user_password(@AuthenticationPrincipal User user,
            @Size(min = 8, max = 32) @RequestParam String password) {
        userService.updatePassword(user.getId(), password);
        return "redirect:/";
    }
}
