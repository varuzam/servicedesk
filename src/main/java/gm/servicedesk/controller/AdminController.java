package gm.servicedesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gm.servicedesk.config.AppProperties;
import gm.servicedesk.dto.UserAddReq;
import gm.servicedesk.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AppProperties config;

    private final UserService userService;
    static final Logger log = LoggerFactory.getLogger(AdminController.class);

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/orgs")
    public String orgs(Model viewModel) {
        return "admin/orgs.html";
    }

    @PostMapping("/orgs/add")
    public String orgs_add() {
        return "admin/orgs.html";
    }

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
