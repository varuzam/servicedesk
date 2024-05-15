package gm.servicedesk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gm.servicedesk.model.User;
import gm.servicedesk.service.UserService;

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
        viewModel.addAttribute("users", userService.findByOrg(user.getOrg()));
        return "customer/admin/users.html";
    }

    @GetMapping("/users/invite")
    @ResponseBody
    public String user_invite(@AuthenticationPrincipal User user) {
        String token = userService.inviteUser(user.getOrg());
        return token;
    }

}
