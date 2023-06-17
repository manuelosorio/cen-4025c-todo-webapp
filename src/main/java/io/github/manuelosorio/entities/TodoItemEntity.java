package io.github.manuelosorio.entities;

import jakarta.persistence.*;
import jakarta.persistence.Column;

@Entity
@Table(name = "todo_item", schema = "module-5")
public class TodoItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private int itemId;
    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "list_id", insertable = false, updatable = false)
    private Integer listId;

    @Column(name = "done")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "list_id", referencedColumnName = "list_id")
    private TodoListEntity todoList;


    public TodoItemEntity(String name) {
        this.name = name;
        this.done = false;
    }

    public TodoItemEntity() {

    }

    public int getId() {
        return itemId;
    }

    public void setId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }


    public TodoListEntity getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoListEntity todoList) {
        this.todoList = todoList;
    }
    public boolean isDone() {
        return done;
    }
    public void toggle() {
        done = !done;
    }
    @Override
    public String toString() {
        return itemId + " " + (done ?  "[✓] " + name : "[ ] " + name);
    }
}
