package pl.todosandjokes.api.controllers;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import pl.todosandjokes.api.model.dto.UserAccountDTO;
import pl.todosandjokes.api.model.dto.UserDTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SecurityController.class)
class SecurityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SecurityController securityController;


    @Test
    void loginShouldReturnStatusOk() throws Exception {
        String requestBody = new JSONObject()
                .put("username","username")
                .put("password","password").toString();

        given(securityController.login(any(UserDTO.class), any(BindingResult.class)))
                .willReturn(new ResponseEntity<>(HttpStatus.OK));

        mvc.perform(post("/login")
                .contentType(APPLICATION_JSON).content(requestBody).characterEncoding("UTF-8"))
                .andExpect(status().isOk());

    }

    @Test
    void registerShouldReturnStatusCreated() throws Exception {
        String requestBody = new JSONObject()
                .put("email","email@abc.pl")
                .put("password","password")
                .put("username","username").toString();

        given(securityController.register(any(UserAccountDTO.class), any(BindingResult.class)))
                .willReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mvc.perform(post("/registration")
                .contentType(APPLICATION_JSON).content(requestBody).characterEncoding("UTF-8"))
                .andExpect(status().isCreated());

    }
}