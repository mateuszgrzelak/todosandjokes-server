package com.rest.webservices.restfulwebservices.repository;

import com.rest.webservices.restfulwebservices.pojo.Todo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TodoRepository {
    private static List<Todo> todos = new LinkedList<>();
    private static long id = 0;

    static {
        todos.add(new Todo(++id, "Jakis opis", new Date(), false));
        todos.add(new Todo(++id, "Jakis inny opis", new Date(), false));
        todos.add(new Todo(++id, "Trzeci opis", new Date(), false));
    }

    public Todo getTodoById(long id) {
        for (Todo todo : todos) {
            if (id == todo.getId()) {
                return todo;
            }
        }
        return null;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public boolean removeTodo(long id) {
        return todos.remove(new Todo(id, null, null, false));
    }

    public Todo addTodo(Todo todo){
        if(todo.getId()<=0){
            Todo newTodo = new Todo(++id, todo.getDescription(), todo.getTargetDate(), false);
            todos.add(newTodo);
            return newTodo;
        }else{
            Todo todoById = getTodoById(todo.getId());
            todoById.setDescription(todo.getDescription());
            todoById.setTargetDate(todo.getTargetDate());
            return todoById;
        }
    }
}
