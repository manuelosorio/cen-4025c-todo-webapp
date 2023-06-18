<%@ page import="io.github.manuelosorio.entities.TodoListEntity" %>
<%@ page import="io.github.manuelosorio.TodoCore" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
  <head>
    <title>${title}</title>
    <%@ include file="includes/head.jsp" %>
  </head>
  <body>
    <%@ include file="includes/nav.jsp" %>
    <h1>${title}</h1>
    <% String slug = request.getAttribute("slug").toString(); %>
    <% TodoListEntity list = new TodoCore().getListBySlug(slug); %>
    <ul>
      <% for (io.github.manuelosorio.entities.TodoItemEntity item : list.getItems()) { %>
      <li class="todo-item">
        <input type="checkbox"
            <% if (item.isDone()) { %> checked <% } %> id="<%= item.getId()%>"
        />
        <label for="<%= item.getId()%>" >
          <%= item.getName() %>
        </label>
        <button>
          <img src="https://cdn-manuelosorio.cyclic.app/api/icons/trash-2?color=red&size=24" alt="">
        </button>
      </li>
      <% } %>
    </ul>

  <script>
    const items = document.querySelectorAll("input[type=checkbox]");
    items.forEach(item => {
      item.addEventListener("click", (e) => {
        const id = e.target.id;
        fetch("/item/toggle?id=" + id, {
          method: "PUT",
        })
        .catch(err => {
          console.error(err);
        });
      });
    });
  </script>
</body>
</html>