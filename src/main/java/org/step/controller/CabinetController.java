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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@WebServlet(urlPatterns = {"/cabinet", "/update"})
public class CabinetController extends HttpServlet {

    private UserRepository<User> userRepository= new UserRepositoryImpl();
    private AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();
    private Random random = new Random();
    private UserService<User> userService = new UserServiceImpl(userRepository, authoritiesRepository, random);
    private AuthoritiesService<User> authoritiesService = new UserServiceImpl(userRepository, authoritiesRepository, random);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        Optional<User> userOptional = Optional.ofNullable(user);

        if (!userOptional.isPresent()) {
            resp.sendRedirect("/index.jsp");
        } else {
            String authority = authoritiesService.getAuthority(userOptional.get().getId());

            String username = req.getParameter("username");

            req.setAttribute("authority", authority);

            getServletContext().getRequestDispatcher("/cabinet.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        user.setUsername(username);
        user.setPassword(password);

        User afterUpdate = userService.update(user);

        session.setAttribute("user", afterUpdate);

        resp.sendRedirect("/cabinet");
    }
}
