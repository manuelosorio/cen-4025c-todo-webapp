package io.github.manuelosorio.todowebapp;

import io.github.manuelosorio.TodoCore;
import io.github.manuelosorio.entities.*;
import jakarta.persistence.NoResultException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;


public class SingleList extends HttpServlet {
    private String title;
    private List<TodoItemEntity> items;
    private final TodoCore core = new TodoCore();
    public void init() {
        this.title = "Single Todo Lists";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        String pathInfo = req.getPathInfo();
        RequestDispatcher dispatcher;
        if (pathInfo != null && pathInfo.length() > 1) {
            String slug = pathInfo.split("/")[1];
            TodoListEntity list;
            try {
                list = core.getListBySlug(slug);
            } catch (NoResultException e) {
                list = null;
            }

            if (list != null) {
                title = list.getName();
                req.setAttribute("title", title);
                req.setAttribute("slug", slug);
                dispatcher = req.getRequestDispatcher("/single-list.jsp");
                dispatcher.forward(req, res);
                return;
            }
        }
        dispatcher = req.getRequestDispatcher("/404.jsp");
        dispatcher.forward(req, res);
    }

    public void destroy() {
    }
}
