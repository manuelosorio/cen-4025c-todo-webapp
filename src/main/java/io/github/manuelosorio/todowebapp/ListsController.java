package io.github.manuelosorio.todowebapp;

import io.github.manuelosorio.TodoCore;
import io.github.manuelosorio.entities.TodoListEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ListsController extends HttpServlet {

    private String createRoute = "/list/create";
    private String deleteRoute = "/list/delete";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String route = req.getRequestURI();
        TodoCore core = new TodoCore();
        if (route.equals(createRoute)) {
            String name = req.getParameter("name");
            System.out.println(name);
            if (name == null) {
                res.setStatus(400);
                return;
            }
            TodoListEntity list = core.createList(name);
            System.out.println(list);
            if (list == null) {
                res.setStatus(400);
                return;
            }
            System.out.println(list.getSlug());
            res.sendRedirect("/list/" + list.getSlug());
            res.setStatus(200);
            return;
        }
        res.setStatus(405);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String route = req.getRequestURI();
        TodoCore core = new TodoCore();
        if (route.equals(deleteRoute)) {
            int id = req.getParameter("id") != null
                    ? Integer.parseInt(req.getParameter("id")) : -1;
            if (id == -1) {
                res.setStatus(400);
                return;
            }
            TodoListEntity list = core.getListById(id);
            if (list != null) {
                core.deleteList(list);
                res.sendRedirect("/");
                res.setStatus(200);
                return;
            }
            res.setStatus(405);
        }

    }


}
