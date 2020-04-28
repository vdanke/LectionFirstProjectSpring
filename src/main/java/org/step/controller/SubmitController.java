package org.step.controller;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.service.UserService;
import org.step.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.step.controller.SubmitController.LOCATION;

@WebServlet(urlPatterns = "/registration")
@MultipartConfig(location = LOCATION)
public class SubmitController extends HttpServlet {

    static final String LOCATION = "/home/vielen/IdeaProjects/LectionFirstProject/images/";

    private UserRepository<User> userRepository= new UserRepositoryImpl();
    private AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();
    private Random random = new Random();
    private UserService<User> userService = new UserServiceImpl(userRepository, authoritiesRepository, random);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRepository<User> userRepository = new UserRepositoryImpl();
        AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();
        Random random = new Random();

        UserService<User> userService = new UserServiceImpl(userRepository, authoritiesRepository, random);

        List<User> all = userService.findAll();

        req.setAttribute("users", all);

        Cookie[] cookies = req.getCookies();

        Optional<Cookie> user = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("user"))
                .findAny();

        user.ifPresent(cookie -> req.setAttribute("value", cookie.getValue()));

        getServletContext().getRequestDispatcher("/submit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User(username, password);

//        Collection<Part> parts = req.getParts();
//        for (Part part : parts) {
//            boolean isTextField = part.getName().equals("username");
//            if (isTextField) {
//                InputStream inputStream = part.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                String usernameFromPartContent = new BufferedReader(inputStreamReader).lines().collect(Collectors.joining());
//                user.setUsername(usernameFromPartContent);
//            } else {
//                part.write("my file" + part.getContentType() + ".jpeg");
//            }
//        }
        File file = new File(LOCATION);
        Part part = req.getPart("file-name");

        if (!file.exists()) {
            file.mkdir();
        }

        if (part.getContentType().contains("jpeg")) {
            part.write("file_" + part.getName() + ".jpeg");
        } else {
            throw new IllegalArgumentException("Wrong file format");
        }

        userService.save(user);
    }
}
