package pl.todosandjokes.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.todosandjokes.api.model.dto.TodoDTO;
import pl.todosandjokes.api.model.entity.Todo;
import pl.todosandjokes.api.repository.UserAccountRepository;
import pl.todosandjokes.api.services.SecurityService;
import pl.todosandjokes.api.services.TodoService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT} )
public class TodosController {

    SecurityService security;
    TodoService todoService;
    UserAccountRepository repository;

    TodosController(SecurityService security, TodoService todoService, UserAccountRepository repository){
        this.security = security;
        this.todoService = todoService;
        this.repository = repository;
    }

    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable("id") int id) {
        Todo todoById = todoService.getTodoById(id);
        if(todoById==null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(todoById, HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") int id) {
        if(todoService.removeTodo(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/todos/{index}")
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todo, @PathVariable int index) throws URISyntaxException {
        Todo responseTodo = todoService.updateTodo(index,
                new Todo(todo.getDescription(), todo.getTargetDate()));
        URI uri = new URI("/todos/"+index);
        if(responseTodo!=null){
            return ResponseEntity.created(uri).build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/todos")
    public ResponseEntity<Void> addTodo(@RequestBody @Valid TodoDTO todo, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        todoService.addTodo(new Todo(todo.getDescription(), todo.getTargetDate()));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/"+(todoService.size()-1)).build().toUri();
        return ResponseEntity.created(uri).build();
    }

}
