package org.step.controller;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.service.UserService;
import org.step.service.impl.UserServiceImpl;
import org.step.util.URIParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@WebServlet(urlPatterns = "/registration")
public class RenderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRepository<User> userRepository = new UserRepositoryImpl();
        AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();
        Random random = new Random();

        UserService<User> userService = new UserServiceImpl(userRepository, authoritiesRepository, random);

        List<User> all = userService.findAll();

        req.setAttribute("users", all);

        getServletContext().getRequestDispatcher("/first.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(URIParser.param(req));
    }
}
