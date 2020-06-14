package pl.todosandjokes.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.todosandjokes.api.model.entity.Todo;
import pl.todosandjokes.api.model.entity.UserAccount;
import pl.todosandjokes.api.repository.UserAccountRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class TodoServiceTest {

    UserAccountRepository repository = Mockito.mock(UserAccountRepository.class);
    TodoService todoService = new TodoService(repository);
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    UserAccount account;

    @BeforeEach
    void setUp() {
        account = new UserAccount();
        account.setPassword("password");
        account.setEmail("email@wp.pl");
        account.setActivated(false);
        account.setUsername("username");

        Todo firstTodo = new Todo();
        firstTodo.setUserAccount(account);
        firstTodo.setDescription("take out the trash");
        firstTodo.setTargetDate(LocalDate.now());
        Todo secondTodo = new Todo();
        secondTodo.setUserAccount(account);
        secondTodo.setDescription("clean the apartment");
        secondTodo.setTargetDate(LocalDate.now());

        account.setTodos(new ArrayList<>(Arrays.asList(firstTodo, secondTodo)));

        SecurityContextHolder.setContext(securityContext);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).willReturn(account.getUsername());
        given(repository.findByUsername(anyString())).willReturn(Optional.of(account));
        given(repository.findAll()).willReturn(Collections.singletonList(account));

    }


    @Test
    void getTodoByIndexShouldBeEqual() {
        Todo todo = new Todo();
        todo.setUserAccount(account);
        todo.setDescription("take out the trash");
        todo.setTargetDate(LocalDate.now());

        assertEquals(todo,todoService.getTodoByIndex(0));
    }

    @Test
    void getTodoByIndexShouldNotBeEqual() {
        Todo todo = new Todo();
        todo.setUserAccount(account);
        todo.setDescription("take out the trash");
        todo.setTargetDate(LocalDate.now());

        assertNotEquals(todo,todoService.getTodoByIndex(1));
    }

    @Test
    void getTodosShouldReturnAppropriateList() {

        Todo firstTodo = new Todo();
        firstTodo.setUserAccount(account);
        firstTodo.setDescription("take out the trash");
        firstTodo.setTargetDate(LocalDate.now());
        Todo secondTodo = new Todo();
        secondTodo.setUserAccount(account);
        secondTodo.setDescription("clean the apartment");
        secondTodo.setTargetDate(LocalDate.now());

        List<Todo> todos = Arrays.asList(firstTodo, secondTodo);

        assertEquals(todos, todoService.getTodos());

    }

    @Test
    void removeTodoShouldReturnTrue() {
        assertTrue(todoService.removeTodo(0));
    }

    @Test
    void removeTodoShouldReturnFalse() {
        assertFalse(todoService.removeTodo(2));
    }

    @Test
    void addTodoShouldIncreaseListSizeOfTodos() {
        Todo todo = new Todo();
        todo.setUserAccount(account);
        todo.setDescription("test");
        todo.setTargetDate(LocalDate.now());

        todoService.addTodo(todo);

        assertEquals(3,todoService.size());
    }

    @Test
    void updateTodoShouldReturnUpdatedTodo() {
        Todo todo = new Todo();
        todo.setUserAccount(account);
        todo.setDescription("test");
        todo.setTargetDate(LocalDate.now());
        todoService.updateTodo(0, todo);
        Todo todoByIndex = todoService.getTodoByIndex(0);
        assertEquals(todoByIndex, todo);
    }

    @Test
    void sizeShouldReturn2Length() {
        assertEquals(2, todoService.size());
    }

}