package com.rest.webservices.restfulwebservices.controllers;

import com.rest.webservices.restfulwebservices.pojo.Todo;
import com.rest.webservices.restfulwebservices.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodosController {

    TodoRepository todoRepository = new TodoRepository();

    @GetMapping("/todos/{user}")
    public List<Todo> getAllTodos() {
        return todoRepository.getTodos();
    }

    @GetMapping("/todos/{name}/{id}")
    public Todo getTodo(@PathVariable("id") long id) {
        return todoRepository.getTodoById(id);
    }

    @DeleteMapping("/todos/{name}/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") long id) {
        if(todoRepository.removeTodo(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/todos/{name}/")
    public ResponseEntity<Todo> updateTodo(@PathVariable String name, @RequestBody Todo todo){
        Todo newTodo = todoRepository.addTodo(todo);
        return new ResponseEntity<Todo>(newTodo, HttpStatus.OK);
    }

    @PostMapping("/todos/{name}/")
    public ResponseEntity<Void> addTodo(@PathVariable String name, @RequestBody Todo todo){
        Todo newTodo = todoRepository.addTodo(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
