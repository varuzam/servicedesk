package gm.servicedesk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gm.servicedesk.model.User;
import gm.servicedesk.service.OrgService;
import gm.servicedesk.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer/admin")
public class CustomerAdminController {

    private final OrgService orgService;
    private final UserService userService;

    CustomerAdminController(OrgService orgService, UserService userService) {
        this.orgService = orgService;
        this.userService = userService;
    }

    // --- USER ---
    @GetMapping("/users")
    public String users(@AuthenticationPrincipal User user, Model viewModel) {
        viewModel.addAttribute("users", userService.findByOrg(user.getOrg()));
        return "customer/admin/users.html";
    }

}
