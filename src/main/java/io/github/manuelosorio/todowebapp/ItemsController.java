package io.github.manuelosorio.todowebapp;

import io.github.manuelosorio.TodoCore;
import io.github.manuelosorio.entities.TodoItemEntity;
import jakarta.servlet.http.*;

public class ItemsController extends HttpServlet {

    private final TodoCore core = new TodoCore();

    private final String deleteRoute = "/item/delete";
    private final String toggleRoute = "/item/toggle";

    private final String postRoute = "/item/add";



    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) {
        String route = req.getRequestURI();
        if (route.equals(toggleRoute)) {
            int id = req.getParameter("id") != null ? Integer.parseInt(req.getParameter("id")) : -1;
            if (id == -1) {
                res.setStatus(400);
                return;
            }

            try {
                TodoCore core = new TodoCore();
                core.toggleItem(id);
                res.setStatus(200);
            } catch (Exception e) {
                res.setStatus(500);
            }
            return;
        }
        res.setStatus(405);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) {
        String route = req.getRequestURI();
        if (route.equals(deleteRoute)) {
            int id = req.getParameter("id") != null
                    ? Integer.parseInt(req.getParameter("id")) : -1;
            if (id == -1) {
                res.setStatus(400);
                return;
            }

            try {
                TodoCore core = new TodoCore();
                core.deleteItem(id);
                res.setStatus(200);
            } catch (Exception e) {
                res.setStatus(500);
            }
            return;
        }
        res.setStatus(405);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        String route = req.getRequestURI();
        if (route.equals(postRoute)) {

            // request body
            String name = req.getParameter("name");
            String listId = req.getParameter("list");
            if (name == null) {
                res.setStatus(400);
                return;
            }

            try {
                TodoCore core = new TodoCore();
                TodoItemEntity item = core.addItem(name, Integer.parseInt(listId));
                // send status code 201 (created) and some name and id for the new item
                res.setContentType("application/json");
                res.getWriter().write("{\"name\":\"" + item.getName() + "\",\"id\":" + item.getId() + "}");
                res.setStatus(201);
            } catch (Exception e) {
                res.setStatus(500);
            }
            return;
        }
        res.setStatus(405);
    }
}
