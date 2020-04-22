package org.step.controller;

import org.step.util.URIParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        log("Method init");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("Method service started");
        super.service(req, resp);
        log("Method service finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("Method get");
        String param = URIParser.param(req);
        resp.getWriter().println(param);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String username = req.getParameter("username");
        System.out.println(phone);
        resp.getWriter().println(phone);
        resp.getWriter().println(username);
    }

    @Override
    public void destroy() {
        super.destroy();
        log("Method destroy");
    }
}
