package gm.servicedesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gm.servicedesk.config.AppProperties;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    AppProperties config;

    static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/config")
    @ResponseBody
    public String print_config() {
        return String.valueOf(config.job().period());
    }

    @GetMapping("/str")
    @ResponseBody
    public String hello_string() {
        log.info("/hello/str is requested");
        return "Hello World";
    }

    @GetMapping("/template")
    public String hello_template(Model viewModel) {
        viewModel.addAttribute("content", "Hello from the template");
        return "hello.html";
    }

}
