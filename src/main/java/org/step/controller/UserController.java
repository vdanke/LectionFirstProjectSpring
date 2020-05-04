package org.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.step.model.User;
import org.step.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService<User> userService;

    @Autowired
    public UserController(UserService<User> userService) {
        this.userService = userService;
    }

    @GetMapping("/cabinet")
    public String getPersonalCabinet(@SessionAttribute("user") User user) {
        Optional<User> userFromSession = Optional.ofNullable(user);

        if (userFromSession.isPresent()) {
            return "cabinet";
        } else {
            return "index";
        }
    }

    @GetMapping("/updating/{id}")
    public String getUserUpdatingPage(
            @PathVariable(name = "id") Long id,
            Model model
    ) {
        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "updating";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> all = userService.findAll();

        model.addAttribute("users", all);

        return "users";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        request.getSession().invalidate();

        return "index";
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            HttpServletRequest request,
            @PathVariable("id") Long id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password
    ) {
        User byId = userService.findById(id);

        byId.setUsername(username);
        byId.setPassword(password);

        HttpSession session = request.getSession();

        userService.update(byId);

        session.setAttribute("user", byId);
        return "cabinet";
    }
}
