package pl.todosandjokes.api.controllers;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import pl.todosandjokes.api.model.dto.TodoDTO;
import pl.todosandjokes.api.model.entity.Todo;
import pl.todosandjokes.api.model.entity.UserAccount;
import pl.todosandjokes.api.repository.UserAccountRepository;
import pl.todosandjokes.api.services.SecurityService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.todosandjokes.api.configuration.SecurityConstants.HEADER_STRING;
import static pl.todosandjokes.api.configuration.SecurityConstants.TOKEN_PREFIX;

@WebMvcTest(TodosController.class)
class TodosControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodosController todosController;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private BCryptPasswordEncoder encoder;


    private SecurityService securityService = new SecurityService(userAccountRepository, encoder);

    private String token;

    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
         userAccount = new UserAccount(
                "username", "password", "email@doc.com", false, new LinkedList<>());
        this.token = securityService.createJWT(userAccount);
    }

    @Test
    void getAllTodosShouldReturnStatusOk() throws Exception {
        Todo firstTodo = new Todo();
        firstTodo.setUserAccount(userAccount);
        firstTodo.setDescription("take out the trash");
        firstTodo.setTargetDate(LocalDate.now());
        Todo secondTodo = new Todo();
        secondTodo.setUserAccount(userAccount);
        secondTodo.setDescription("clean the apartment");
        secondTodo.setTargetDate(LocalDate.now());

        List<Todo> todos = Arrays.asList(firstTodo, secondTodo);

        given(todosController.getAllTodos()).willReturn(todos);

        mvc.perform(get("/todos").header(HEADER_STRING, TOKEN_PREFIX+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].description", is(todos.get(1).getDescription())));
    }

    @Test
    void getTodoShouldReturnStatusOk() throws Exception {
        Todo firstTodo = new Todo();
        firstTodo.setUserAccount(userAccount);
        firstTodo.setDescription("take out the trash");
        firstTodo.setTargetDate(LocalDate.now());
        Todo secondTodo = new Todo();
        secondTodo.setUserAccount(userAccount);
        secondTodo.setDescription("clean the apartment");
        secondTodo.setTargetDate(LocalDate.now());

        List<Todo> todos = Arrays.asList(firstTodo, secondTodo);

        given(todosController.getTodo(1)).willReturn(new ResponseEntity<>(todos.get(1), HttpStatus.OK));

        mvc.perform(get("/todos/1").header(HEADER_STRING, TOKEN_PREFIX+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(todos.get(1).getDescription())));
    }

    @Test
    void deleteTodoShouldReturnStatusOk() throws Exception {

        given(todosController.deleteTodo(15)).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mvc.perform(delete("/todos/15").header(HEADER_STRING, TOKEN_PREFIX+token))
                .andExpect(status().isOk());
    }

    @Test
    void updateTodoShouldReturnStatusOk() throws Exception {
        String requestBody = new JSONObject()
                .put("description","take out the trash")
                .put("targetDate","2020-12-12").toString();

        given(todosController.updateTodo(any(TodoDTO.class), anyInt(), any(BindingResult.class))).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mvc.perform(put("/todos/1").header(HEADER_STRING, TOKEN_PREFIX+token)
                .contentType(APPLICATION_JSON).content(requestBody).characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void addTodoShouldReturnStatusCreated() throws Exception {
        TodoDTO todo = new TodoDTO();
        todo.setDescription("take out the trash");
        todo.setTargetDate(LocalDate.of(2020,12,12));

        String requestBody = new JSONObject()
                .put("description","take out the trash")
                .put("targetDate","2020-12-12").toString();

        given(todosController.addTodo(any(TodoDTO.class), any(BindingResult.class))).willReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mvc.perform(post("/todos").header(HEADER_STRING, TOKEN_PREFIX + token)
                .contentType(APPLICATION_JSON).content(requestBody).characterEncoding("UTF-8")).andDo(print())
                .andExpect(status().isCreated());


    }
}