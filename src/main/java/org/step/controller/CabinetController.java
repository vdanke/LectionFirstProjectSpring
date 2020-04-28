package org.step.controller;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.service.AuthoritiesService;
import org.step.service.UserService;
import org.step.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = "/cabinet")
public class CabinetController extends HttpServlet {

    private UserRepository<User> userRepository= new UserRepositoryImpl();
    private AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();
    private Random random = new Random();
    private UserService<User> userService = new UserServiceImpl(userRepository, authoritiesRepository, random);
    private AuthoritiesService<User> authoritiesService = new UserServiceImpl(userRepository, authoritiesRepository, random);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        String authority = authoritiesService.getAuthority(user.getId());

        String username = req.getParameter("username");
        System.out.println(username);

        req.setAttribute("authority", authority);

        getServletContext().getRequestDispatcher("/cabinet.jsp").forward(req, resp);
    }
}
