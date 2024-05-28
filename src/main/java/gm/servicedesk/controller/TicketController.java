package gm.servicedesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gm.servicedesk.model.TicketPriority;
import gm.servicedesk.model.TicketStatus;
import gm.servicedesk.model.User;
import gm.servicedesk.dto.TicketAddReq;
import gm.servicedesk.dto.UserAddReq;
import gm.servicedesk.service.OrgService;
import gm.servicedesk.service.TicketService;
import gm.servicedesk.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final OrgService orgService;
    private final UserService userService;
    private final TicketService ticketService;
    static final Logger log = LoggerFactory.getLogger(AdminController.class);

    TicketController(OrgService orgService, UserService userService, TicketService ticketService) {
        this.orgService = orgService;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("")
    public String tickets(Model viewModel) {
        viewModel.addAttribute("tickets", ticketService.findAll());
        return "tickets.html";
    }

    @GetMapping("/add")
    public String add_ticket_dialog(Model viewModel) {
        viewModel.addAttribute("priorities", TicketPriority.values());
        viewModel.addAttribute("statuses", TicketStatus.values());
        return "_add_ticket_dialog.html";
    }

    @PostMapping("/add")
    public String add_ticket_form(@AuthenticationPrincipal User user, @Valid TicketAddReq form) {
        ticketService.add(form, user.getOrg());
        return "redirect:/tickets";
    }

    @GetMapping("/users/{id}/update")
    public String update_user_dialog(@PathVariable Integer id, Model viewModel) {
        User user = userService.find(id);
        viewModel.addAttribute("user", user);
        viewModel.addAttribute("roles", TicketStatus.values());
        viewModel.addAttribute("orgs", orgService.findAll());
        return "admin/_update_user_dialog.html";
    }

    @PostMapping("/users/{id}/update")
    public String update_user_form(@PathVariable Integer id, @Valid UserAddReq form) {
        userService.update(id, form);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/updatepassword")
    public String update_user_password(@PathVariable Integer id,
            @Size(min = 8, max = 32) @RequestParam String password) {
        userService.updatePassword(id, password);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/delete")
    public String delete_user(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
