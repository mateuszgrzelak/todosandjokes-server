package pl.todosandjokes.api.controllers;

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
import pl.todosandjokes.api.model.entity.UserAccount;
import pl.todosandjokes.api.model.pojo.Joke;
import pl.todosandjokes.api.repository.UserAccountRepository;
import pl.todosandjokes.api.services.SecurityService;

import java.util.LinkedList;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.todosandjokes.api.configuration.SecurityConstants.HEADER_STRING;
import static pl.todosandjokes.api.configuration.SecurityConstants.TOKEN_PREFIX;

@WebMvcTest(JokesController.class)
class JokesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JokesController jokesController;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    private SecurityService securityService = new SecurityService(userAccountRepository, encoder);

    private String token;

    @BeforeEach
    void setUp(){
        UserAccount userAccount = new UserAccount(
                "username", "password", "email@doc.com", false, new LinkedList<>());
        this.token = securityService.createJWT(userAccount);
        given(userAccountRepository.findByUsername(userAccount.getUsername()))
                .willReturn(Optional.of(userAccount));
    }

    @Test
    void getOneJoke() throws Exception {
        Joke joke = new Joke();
        joke.setType("general");
        joke.setSetup("What do you call a singing Laptop");
        joke.setPunchline("A Dell");
        Joke[] jokes = new Joke[]{joke};

        given(jokesController.getJokes()).willReturn(new ResponseEntity<>(jokes, HttpStatus.OK));

        mvc.perform(get("/jokes").header(HEADER_STRING, TOKEN_PREFIX+token)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].punchline", is(joke.getPunchline())));

    }

    @Test
    void getTwoJokes() throws Exception {
        Joke firstJoke = new Joke();
        firstJoke.setType("general");
        firstJoke.setSetup("What do you call a singing Laptop");
        firstJoke.setPunchline("A Dell");
        Joke secondJoke = new Joke();
        secondJoke.setType("general");
        secondJoke.setSetup("What's the difference between a hippo and a zippo?");
        secondJoke.setPunchline("One is really heavy, the other is a little lighter.");
        Joke[] jokes = new Joke[]{firstJoke, secondJoke};

        given(jokesController.getJokes()).willReturn(new ResponseEntity<>(jokes, HttpStatus.OK));

        mvc.perform(get("/jokes").header(HEADER_STRING, TOKEN_PREFIX+token)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].punchline", is(secondJoke.getPunchline())));
    }
}