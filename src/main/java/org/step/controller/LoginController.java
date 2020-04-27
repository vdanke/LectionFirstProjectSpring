package org.step.controller;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.service.UserService;
import org.step.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private UserRepository<User> userRepository= new UserRepositoryImpl();
    private AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();
    private Random random = new Random();
    private UserService<User> userService = new UserServiceImpl(userRepository, authoritiesRepository, random);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User(username, password);

        User login = userService.login(user);

        HttpSession session = req.getSession(true);

        session.setAttribute("user", login);

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            Cookie cookie = new Cookie("user", login.getUsername());
            cookie.setMaxAge(3600);

            resp.addCookie(cookie);
        }

        resp.sendRedirect("/index.jsp");
    }
}
