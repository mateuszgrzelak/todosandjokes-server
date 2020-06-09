package pl.todosandjokes.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.todosandjokes.api.model.pojo.Todo;
import pl.todosandjokes.api.repository.TodoRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT} )
public class TodosController {

    TodoRepository todoRepository = new TodoRepository();

    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        return todoRepository.getTodos();
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable("id") int id) {
        Todo todoById = todoRepository.getTodoById(id);
        if(todoById==null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(todoById, HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") int id) {
        if(todoRepository.removeTodo(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/todos/{index}")
    public ResponseEntity<?> updateTodo(@RequestBody Todo todo, @PathVariable int index) throws URISyntaxException {
        Todo responseTodo = todoRepository.updateTodo(index, todo);
        URI uri = new URI("/todos/"+index);
        if(responseTodo!=null){
            return ResponseEntity.created(uri).build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/todos")
    public ResponseEntity<Void> addTodo(@RequestBody @Valid Todo todo, BindingResult result) {
        if(result.hasErrors()){
            System.out.println("ERROR");
        }
        todoRepository.addTodo(todo);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/"+(todoRepository.size()-1)).build().toUri();
        return ResponseEntity.created(uri).build();
    }

}
