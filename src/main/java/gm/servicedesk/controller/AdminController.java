package gm.servicedesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import gm.servicedesk.model.Role;
import gm.servicedesk.dto.OrgAddReq;
import gm.servicedesk.dto.UserAddReq;
import gm.servicedesk.service.OrgService;
import gm.servicedesk.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrgService orgService;
    private final UserService userService;
    static final Logger log = LoggerFactory.getLogger(AdminController.class);

    AdminController(OrgService orgService, UserService userService) {
        this.orgService = orgService;
        this.userService = userService;
    }

    // --- ORG ---
    @GetMapping("/orgs")
    public String orgs(Model viewModel) {
        viewModel.addAttribute("orgs", orgService.findAll());
        return "admin/orgs.html";
    }

    @GetMapping("/orgs/add")
    public String add_org_dialog() {
        return "admin/_add_org_dialog.html";
    }

    @PostMapping("/orgs/add")
    public String add_org_form(@Valid OrgAddReq form) {
        orgService.addOrg(form);
        return "redirect:/admin/orgs";
    }

    @GetMapping("/orgs/{id}/delete")
    public String delete_org(@PathVariable Integer id) {
        orgService.deleteOrg(id);
        return "redirect:/admin/orgs";
    }

    // --- USER ---
    @GetMapping("/users")
    public String users(Model viewModel) {
        viewModel.addAttribute("users", userService.findAll());
        return "admin/users.html";
    }

    @GetMapping("/users/add")
    public String add_user_dialog(Model viewModel) {
        viewModel.addAttribute("roles", Role.values());
        viewModel.addAttribute("orgs", orgService.findAll());
        return "admin/_add_user_dialog.html";
    }

    @PostMapping("/users/add")
    public String add_user_form(@Valid UserAddReq form) {
        userService.add(form);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/delete")
    public String delete_user(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
