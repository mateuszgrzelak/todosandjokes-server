package pl.todosandjokes.api.repository;

import pl.todosandjokes.api.model.pojo.Todo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TodoRepository {
    private static List<Todo> todos = new LinkedList<>();

    static {
        todos.add(new Todo("Jakis opis", LocalDate.now()));
        todos.add(new Todo("Jakis inny opis", LocalDate.now()));
        todos.add(new Todo("Trzeci opis", LocalDate.now()));
    }

    public Todo getTodoById(int id) {
        try{
            return todos.get(id);
        }catch(Exception e) {
            return null;
        }
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public boolean removeTodo(int id) {
        try {
            todos.remove((int) (id));
        }catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    public Todo addTodo(Todo todo){
        todos.add(todo);
        return todo;
    }

    public Todo updateTodo(int index,Todo todo){
        try{
            return todos.set(index, todo);
        }catch(Exception e){
            return null;
        }
    }

    public int size(){
        return todos.size();
    }
}
