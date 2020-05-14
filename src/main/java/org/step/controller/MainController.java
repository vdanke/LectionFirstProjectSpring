package org.step.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.step.dto.MessageDTO;
import org.step.model.Message;
import org.step.model.User;
import org.step.service.MessageService;
import org.step.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/home")
public class MainController {

    private final UserService<User> userService;
    private final MessageService<Message> messageService;

    @Autowired
    public MainController(UserService<User> userService,
                          MessageService<Message> messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        val messageList = messageService.findAll();

        model.addAttribute("messages", messageList);

        return "index";
    }

    @GetMapping("/submit")
    public ModelAndView getRegistrationPage() {
        ModelAndView registration = new ModelAndView("submit");

        registration.addObject("value", "Hello, friend");

        return registration;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("value", "Hello, friend");

        return "login";
    }

    @PostMapping("/submit")
    public String registration(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "role", required = false, defaultValue = "user") String role
    ) {
        val user = User.field(username, password);

        boolean isAdmin = false;

        if (role.equals("admin")) {
            isAdmin = true;
        }
        userService.save(user, isAdmin);

        return "index";
    }

}
