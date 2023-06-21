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
    <ul class="list__container">
      <% for(TodoListEntity list : (List<TodoListEntity>) request.getAttribute("lists")) { %>
        <li><a href="/list/<%= list.getSlug() %>/"><%= list.getName() %></a></li>
      <% } %>
    </ul>
    <form>
      <input type="text" name="name" placeholder="List name" />
      <button type="submit" class="button button--primary">
        <img src="https://cdn-manuelosorio.cyclic.app/api/icons/plus?color=%23E5EFF0&size=24" alt="Plus icon" />
        Create List
      </button>
    </form>

    <script>
      const form = document.querySelector('form');
      form.addEventListener('submit', (e) => {
          e.preventDefault();
          const details = {
              name: e.target.elements.name.value
          };
          let formBody = [];
          for (const property in details) {
              const encodedKey = encodeURIComponent(property);
              const encodedValue = encodeURIComponent(details[property]);
              formBody.push(encodedKey + "=" + encodedValue);
          }
          formBody = formBody.join("&");
        fetch('/list/create', {
          method: 'POST',
          headers: {
              "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
          },
          body: formBody
        })
        .then((response) => {
          if (response.status === 200) {
            window.location.href = response.url;
          }
        })
        .catch(err => {
          console.error(err);
        });
      });
    </script>

  </body>
</html>

