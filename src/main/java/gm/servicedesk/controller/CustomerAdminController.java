package gm.servicedesk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import gm.servicedesk.model.User;
import gm.servicedesk.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/customer/admin")
public class CustomerAdminController {

    private final UserService userService;

    CustomerAdminController(UserService userService) {
        this.userService = userService;
    }

    // --- USER ---
    @GetMapping("/users")
    public String users(@AuthenticationPrincipal User user, Model viewModel) {
        viewModel.addAttribute("users", userService.findAll(user.getOrg()));
        return "customer/admin/users.html";
    }

    @GetMapping("/users/invite")
    public String invite_user_dialog(HttpServletRequest req, @AuthenticationPrincipal User user, Model viewModel) {
        String token = userService.generateInviteToken(user.getOrg());
        viewModel.addAttribute("inviteUrl", String.format("http://%s/register/%s", req.getServerName(), token));
        return "customer/admin/_invite_user_dialog.html";
    }

    @GetMapping("/users/{id}/delete")
    public String delete_user(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        userService.delete(id, user.getOrg());
        return "redirect:/customer/admin/users";
    }

}
