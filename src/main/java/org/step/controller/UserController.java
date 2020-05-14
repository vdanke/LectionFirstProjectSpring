package org.step.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.step.model.User;
import org.step.model.UserDetailsImpl;
import org.step.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.step.util.URIConstantUtil.USERS_URI;

@Controller
public class UserController {

//    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

    private final UserService<User> userService;

    @Autowired
    public UserController(UserService<User> userService) {
        this.userService = userService;
    }

    @GetMapping("/cabinet")
    public String getPersonalCabinet(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("user", userDetails);

        return "cabinet";
    }

    @GetMapping("/updating/{id}")
    public String getUserUpdatingPage(
            @NonNull @PathVariable(name = "id") Long id,
            Model model
    ) {
        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "updating";
    }

    @PostMapping("/updating/{id}")
    public String updateUserById(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password
    ) {
        User user = userService.findById(id);

        user.setUsername(username);
        user.setPassword(password);

        userService.update(user);

        return "users";
    }

    @GetMapping(USERS_URI)
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
            @PathVariable("id") Long id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            Model model
    ) {
        User byId = userService.findById(id);

        byId.setUsername(username);
        byId.setPassword(password);

        userService.update(byId);

        UserDetailsImpl userDetails = UserDetailsImpl.create(byId);
        model.addAttribute("user", userDetails);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities()
        ));

        return "cabinet";
    }
}
