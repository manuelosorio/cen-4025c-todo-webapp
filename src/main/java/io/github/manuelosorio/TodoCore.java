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
//    public void menu() {
//        EntityTransaction transaction = this.entityManager.getTransaction();
//
//        try (Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {
//            while (true) {
//                System.out.println("Please select an option from the menu below");
//                System.out.println("3. Delete a list");
//                System.out.println("6. Toggle an item on a list");
//                System.out.println("7. Exit the application");
//
//                int option;
//                try {
//                    option = scanner.nextInt();
//                } catch (InputMismatchException e) {
//                    System.out.println("Please enter a valid number.");
//                    scanner.next();
//                    continue;
//                }
//
//                String listName, itemName;
//                TodoListEntity list;
//                int itemIndex;
//
//                transaction.begin();
//
//                switch (option) {
//                    case 1 -> {
//                        System.out.println("What would you like to name the list?");
//                        listName = scanner.next().toLowerCase();
//                        try {
//                            list = getList(listName);
//                        } catch (NoResultException e) {
//                            list = null;
//                        }
//                        if (list == null) {
//                            list = new TodoListEntity(listName);
//                            entityManager.persist(list);
//                        } else {
//                            System.out.println("List already exists.");
//                        }
//                    }
//                    case 2 -> {
//                        System.out.println("Lists:\n"
//                                + "-------------------");
//                        for (TodoListEntity l : getAllLists()) {
//                            System.out.println("- " + l.getName() + " (" + l.getSlug() + ")");
//                        }
//                        System.out.println("-------------------");
//                        System.out.println("Which list would you like to view?");
//                        listName = scanner.next().toLowerCase();
//                        try {
//                            list = getListBySlug("shopping-list-6-5-23");
//                        } catch (NoResultException e) {
//                            list = null;
//                        }
//                        if (list != null) {
//                            try {
//                                for (TodoItemEntity item : list.getItems()) {
//                                    item = entityManager.find(TodoItemEntity.class, item.getId());
//                                    System.out.println(item.toString());
//                                }
//                            } catch (NullPointerException e) {
//                                System.out.println("No items in list.");
//                            }
//                        } else {
//                            System.out.println("No such list exists.");
//                        }
//                    }
//                    case 3 -> {
//                        System.out.println("Which list would you like to delete?");
//                        listName = scanner.next().toLowerCase();
//                        try {
//                            list = getList(listName);
//                        } catch (NoResultException e) {
//                            list = null;
//                        }
//                        if (list != null) {
//                            entityManager.remove(entityManager.find(TodoListEntity.class, list.getId()));
//                        } else {
//                            System.out.println("No such list exists.");
//                        }
//                    }
//                    case 4 -> {
//                        System.out.println("Which list would you like to add an item to?");
//                        listName = scanner.next().toLowerCase();
//                        try {
//                            list = getList(listName);
//                        } catch (NoResultException e) {
//                            list = null;
//                        }
//                        if (list != null) {
//                            System.out.println("What is the name of the item you would like to add?");
//                            itemName = scanner.next();
//                            TodoItemEntity item = new TodoItemEntity(itemName);
//                            item.setListId(list.getId());
//                            item.setTodoList(list);
//                            list.addItem(item);
//                            entityManager.persist(item);
//
//                            System.out.println("Would you like to add another item? (y/n)");
//                            String answer = scanner.next();
//                            while (answer.equalsIgnoreCase("y")) {
//                                System.out.println("What is the name of the item you would like to add?");
//                                itemName = scanner.next();
//                                item = new TodoItemEntity(itemName);
//                                item.setListId(list.getId());
//                                item.setTodoList(list);
//                                list.addItem(item);
//                                entityManager.persist(item);
//                                System.out.println("Would you like to add another item? (y/n)");
//                                answer = scanner.next();
//                            }
//                        } else {
//                            System.out.println("No such list exists.");
//                        }
//                    }
//                    case 5 -> {
//                        System.out.println("What is the name of the list?");
//                        listName = scanner.next().toLowerCase();
//                        try {
//                            list = getList(listName);
//                        } catch (NoResultException e) {
//                            list = null;
//                        }
//                        if (list != null) {
//                            System.out.println("What is the id of the item you would like to delete? ");
//                            itemIndex = scanner.nextInt();
//                            entityManager.remove(entityManager.find(TodoItemEntity.class, itemIndex));
//                        } else {
//                            System.out.println("No such list exists.");
//                        }
//                    }
//                    case 6 -> {
//                        System.out.println("What is the name of the list?");
//                        listName = scanner.next().toLowerCase();
//                        System.out.println("What is the id of the item you would like to toggle?");
//                        itemIndex = scanner.nextInt();
//                        try {
//                            list = getList(listName);
//                        } catch (NoResultException e) {
//                            list = null;
//                        }
//                        if (list != null) {
//                            for (TodoItemEntity item : list.getItems()) {
//                                item = entityManager.find(TodoItemEntity.class, item.getId());
//                                if (item.getId() == itemIndex) {
//                                    item.toggle();
//                                    entityManager.merge(item);
//                                }
//                            }
//                        } else {
//                            System.out.println("No such list exists.");
//                        }
//                    }
//                    case 7 -> {
//                        System.out.println("Thank you for using the Todo List Application");
//                        transaction.commit();
//                        entityManager.close();
//                        entityManagerFactory.close();
//                        System.exit(0);
//                    }
//                    default -> System.out.println("Please enter a valid option");
//                }
//                transaction.commit();
//                entityManager.clear();
//            }
//        } finally {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//        }
//    }


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