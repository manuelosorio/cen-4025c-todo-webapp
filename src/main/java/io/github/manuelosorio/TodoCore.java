package io.github.manuelosorio;

import io.github.manuelosorio.entities.TodoItemEntity;
import io.github.manuelosorio.entities.TodoListEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

public class TodoCore {
    private final EntityManager entityManager;
    private final EntityTransaction transaction;

    public TodoCore() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.transaction = this.entityManager.getTransaction();
    }

    private TodoListEntity getList(String listName) throws NoResultException {
        TypedQuery<TodoListEntity> query = entityManager.createQuery(
                "SELECT l FROM TodoListEntity l WHERE l.name = :name", TodoListEntity.class);
        query.setParameter("name", listName);
        return query.getSingleResult();
    }

    public TodoListEntity getListBySlug(String slug) throws NoResultException {
        TypedQuery<TodoListEntity> query = entityManager.createQuery(
                "SELECT l FROM TodoListEntity l WHERE l.slug = :listSlug", TodoListEntity.class);
        query.setParameter("listSlug", slug);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public TodoListEntity getListById(int id) {
        try {
            return entityManager.find(TodoListEntity.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<TodoListEntity> getAllLists() {
        TypedQuery<TodoListEntity> query = entityManager.createQuery(
                "SELECT l FROM TodoListEntity l", TodoListEntity.class);
        return query.getResultList();
    }

    public void toggleItem(int id) {
        TodoItemEntity item = entityManager.find(TodoItemEntity.class, id);
        this.transaction.begin();
        item.toggle();
        this.entityManager.merge(item);
        this.transaction.commit();
        this.entityManager.clear();
    }

    public void deleteItem(int id) {
        TodoItemEntity item = entityManager.find(TodoItemEntity.class, id);
        this.transaction.begin();
        this.entityManager.remove(item);
        this.transaction.commit();
        this.entityManager.clear();
    }

    public TodoItemEntity addItem(String name, int i) {
        TodoListEntity list = entityManager.find(TodoListEntity.class, i);
        TodoItemEntity item = new TodoItemEntity(name);
        item.setListId(list.getId());
        item.setTodoList(list);
        list.addItem(item);
        this.transaction.begin();
        this.entityManager.persist(item);
        this.transaction.commit();
        this.entityManager.clear();
        return item;
    }

    public void deleteList(TodoListEntity list) {
        this.transaction.begin();
        this.entityManager.remove(list);
        this.transaction.commit();
        this.entityManager.clear();
    }

    public TodoListEntity createList(String name) {
        String slug = slugify(name);
        TodoListEntity existingList = this.getListBySlug(slug);
        if (existingList != null) {
            slug = slug + "-" + UUID.randomUUID().toString().substring(0,5);
        }

        this.transaction.begin();
        TodoListEntity list = new TodoListEntity(name);
        list.setSlug(slug);
        this.entityManager.persist(list);
        this.transaction.commit();
        this.entityManager.clear();
        return list;
    }

    private String slugify(String str) {
        String slug = str.toLowerCase()
                .trim()
                .replaceAll("[^\\w\\s-]", "")
                .replaceAll("[\\s_-]+", "-")
                .replaceAll("^-+|-+$", "");
        return slug;
    }
}