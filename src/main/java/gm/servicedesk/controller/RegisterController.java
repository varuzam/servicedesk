package gm.servicedesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gm.servicedesk.dto.UserRegisterReq;
import gm.servicedesk.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/{token}")
    public String registerPage(@PathVariable String token) {
        return "register_user.html";
    }

    @PostMapping("/{token}")
    @ResponseBody
    public String registerByInvite(@PathVariable String token, @Valid UserRegisterReq form) {
        if (userService.registerUserByInviteToken(token, form) == null) {
            return "User not added";
        }
        return "User Added";
    }
}
