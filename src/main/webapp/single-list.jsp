<%@ page import="io.github.manuelosorio.entities.TodoListEntity" %>
<%@ page import="io.github.manuelosorio.TodoCore" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
  <head>
    <title>${title}</title>
    <%@ include file="includes/head.jsp" %>
  </head>
  <body>
    <% String slug = request.getAttribute("slug").toString(); %>
    <% TodoListEntity list = new TodoCore().getListBySlug(slug); %>
    <%@ include file="includes/nav.jsp" %>
    <h1>${title}</h1>
    <button class="button button--delete" data-type="list" data-listId=<%= list.getId() %>  >
      <img src="https://cdn-manuelosorio.cyclic.app/api/icons/trash-2?color=%23E5EFF0&size=1rem" alt="">
      &nbsp; Delete List
    </button>
    <ul class="todo-item__container">
      <% for (io.github.manuelosorio.entities.TodoItemEntity item : list.getItems()) { %>
      <li class="todo-item">
        <input type="checkbox"
            <% if (item.isDone()) { %> checked <% } %> id="<%= item.getId()%>"
        />
        <label for="<%= item.getId()%>" >
          <span></span>
          <%= item.getName() %>
        </label>
        <button class="button button--delete" data-type="item" data-itemId=<%=item.getId() %>>
          <img src="https://cdn-manuelosorio.cyclic.app/api/icons/trash-2?color=%23E5EFF0&size=1rem" alt="">
        </button>
      </li>
      <% } %>
    </ul>
  <h2>Add new item</h2>
  <form>
    <input type="text" name="name" placeholder="Name" />
    <input type="hidden" name="list" value="<%= list.getId() %>" />
    <button type="submit" class="button button--primary"><img src="https://cdn-manuelosorio.cyclic.app/api/icons/plus?color=%23E5EFF0&size=24" /> add</button>
  </form>

  <script>
    const items = document.querySelectorAll("input[type=checkbox]");
    const deleteButtons = document.querySelectorAll(".button--delete");
    const form = document.querySelector("form");
    for (const item of items) {
      item.addEventListener("click", (e) => {
        const id = e.target.id;
        fetch("/item/toggle?id=" + id, {
            method: "PUT",
        })
        .catch(err => {
            console.error(err);
        });
      });
    }
    // Delete Item
    function deleteItem (e)  {
        //item dataset
        const item = this.parentNode;
        const id = item.children[0].id;
        fetch("/item/delete?id=" + id, {
            method: "DELETE",
        }).then(() => {
            item.remove();
        })
            .catch(err => {
                console.error(err);
        });
    };

    // Delete List
    const deleteList = (e) => {
        const id = e.target.dataset.listid;
        fetch("/list/delete?id=" + id, {
            method: "DELETE",
        }).then((resposne) => {
            if (resposne.status !== 400 || resposne.status !== 500 || resposne.status !== 405) {
                window.location.href = resposne.url;
            }
            console.log(resposne)
        })
        .catch(err => {
            console.error(err);
        });
    };

    for (const button of deleteButtons) {
      if (button.dataset.type === "item") {
          button.addEventListener("click", deleteItem);
      } else if (button.dataset.type === "list") {
          button.addEventListener("click", deleteList);
      } else {
          console.error("Invalid button type");
      }
    }

    // Add Item
    form.addEventListener("submit", (e) => {
      e.preventDefault();
        const ul = document.querySelector(".todo-item__container");
        const name = e.target.elements.name.value;
        const list = e.target.elements.list.value;

        const details = {
            name: name,
            list: list
        };
        let formBody = [];
        for (const property in details) {
            const encodedKey = encodeURIComponent(property);
            const encodedValue = encodeURIComponent(details[property]);
            formBody.push(encodedKey + "=" + encodedValue);
        }
        formBody = formBody.join("&");

        fetch("/item/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
            },
            body: formBody,
        }).then(response => {
            if (response.ok || response.status === 201 || response.status === 200) {
                form.reset();
                return response.json();
            } else {
                throw new Error('Error: ' + response.status);
            }
        }).then((data) => {
            const { id, name } = data;
            const li = document.createElement("li");
            li.classList.add("todo-item");
            li.innerHTML = "" +
                "<input type='checkbox' id='" + id + "' />" +
                "<label for='" + id + "'>" +
                  "<span></span>" +
                  name +
                "</label>" +
                "<button class='button button--delete'>" +
                  "<img src='https://cdn-manuelosorio.cyclic.app/api/icons/trash-2?color=%23E5EFF0&size=1rem' alt=''>" +
                "</button>";
            const button = li.querySelector(".button--delete");
            button.dataset.type = "item";
            button.dataset.itemid = id;
            button.addEventListener("click", deleteItem);
            ul.appendChild(li);
      })
      .catch(err => {
          console.error(err);
      });
    });
  </script>
</body>
</html>