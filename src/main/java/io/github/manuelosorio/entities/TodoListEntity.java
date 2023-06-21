package io.github.manuelosorio.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "todo_list", schema = "module-5")
public class TodoListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Integer listId;
    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "todoList")
    private List<TodoItemEntity> items  = new ArrayList<>();

    @Basic
    @Column(name = "slug")
    private String slug;

    public TodoListEntity() {
    }

    public TodoListEntity(String name) {
        this.name = name;
    }



    public int getId() {
        return listId;
    }

    public void setId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TodoItemEntity> getItems() {
        return this.items;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return this.slug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoListEntity that = (TodoListEntity) o;

        if (listId != that.listId) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = listId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


    public void addItem(TodoItemEntity item) {
        this.items.add(item);
    }

    public void deleteItem(int itemId) {
        for (TodoItemEntity item : this.items) {
            if (item.getId() == itemId) {
                this.items.remove(item);
                System.out.println("Item: " + item.getName() + " deleted");
                break;
            }
        }
    }



}
