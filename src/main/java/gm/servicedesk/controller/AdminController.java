package gm.servicedesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String org_add_form() {
        return "admin/orgs_add.html";
    }

    @PostMapping("/orgs/add")
    @ResponseBody
    public String org_add_form(@Valid OrgAddReq form) {
        orgService.addOrg(form);
        return "Org added";
    }

    // --- USER ---
    @GetMapping("/users")
    public String users(Model viewModel) {
        viewModel.addAttribute("users", userService.findAll());
        return "admin/users.html";
    }

    @GetMapping("/users/add")
    public String user_add_form() {
        return "admin/users_add.html";
    }

    @PostMapping("/users/add")
    @ResponseBody
    public String user_add_form(@Valid UserAddReq form) {
        userService.addUser(form);
        return "User added";
    }

}
