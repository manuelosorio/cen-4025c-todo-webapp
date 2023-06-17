<%@ page import="io.github.manuelosorio.entities.TodoListEntity" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>${title}</title>
    <%@ include file="includes/head.jsp" %>
  </head>
  <body>
    <%@ include file="includes/nav.jsp"%>
    <h1>${title}</h1>
    <ul>
      <% for(TodoListEntity list : (List<TodoListEntity>) request.getAttribute("lists")) { %>
        <li><a href="/list/<%= list.getSlug() %>/"><%= list.getName() %></a></li>
      <% } %>
    </ul>
      <a href="#">Create new list</a>
    <br/>
  </body>
</html>

