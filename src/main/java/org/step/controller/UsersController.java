package org.step.controller;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.security.Role;
import org.step.service.AuthoritiesService;
import org.step.service.UserService;
import org.step.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@WebServlet(urlPatterns = "/users")
public class UsersController extends HttpServlet {

    private UserRepository<User> userRepository= new UserRepositoryImpl();
    private AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();
    private Random random = new Random();
    private UserService<User> userService = new UserServiceImpl(userRepository, authoritiesRepository, random);
    private AuthoritiesService<User> authoritiesService = new UserServiceImpl(userRepository, authoritiesRepository, random);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        Optional<User> optionalUser = Optional.ofNullable(user);

        if (!optionalUser.isPresent() || !user.getRole().equals(Role.ROLE_ADMIN)) {
            resp.sendRedirect("/index.jsp");
            return;
        }
        List<User> all = userService.findAll();

        req.setAttribute("users", all);

        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }
}
