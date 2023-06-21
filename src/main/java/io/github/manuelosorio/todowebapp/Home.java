package io.github.manuelosorio.todowebapp;

import java.io.*;

import io.github.manuelosorio.TodoCore;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class Home extends HttpServlet {
    private String title;
    private String message;

    private final TodoCore core = new TodoCore();

    @Override
    public void init() {
        title = "All Todo Lists";
        message = "Hello World!";
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        req.setAttribute("message", message);
        req.setAttribute("title", title);
        req.setAttribute("lists", core.getAllLists());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/home.jsp");
        dispatcher.forward(req, res);
    }
    @Override
    public void destroy() {
    }
}