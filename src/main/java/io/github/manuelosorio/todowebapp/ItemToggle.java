package io.github.manuelosorio.todowebapp;

import io.github.manuelosorio.TodoCore;
import jakarta.servlet.http.*;

public class ItemToggle extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(405);
    }
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) {
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
    }
}
