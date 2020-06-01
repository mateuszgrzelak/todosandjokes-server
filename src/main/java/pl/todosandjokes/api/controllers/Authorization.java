package pl.todosandjokes.api.controllers;

import pl.todosandjokes.api.model.DTO.UserDTO;
import pl.todosandjokes.api.model.DTO.UserAccountDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.todosandjokes.api.model.pojo.UserAccount;
import pl.todosandjokes.api.services.authorization.Security;

import javax.validation.Valid;

@RestController
public class Authorization {

    Security security;

    public Authorization(Security security) {
        this.security = security;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated UserDTO user, BindingResult errors){
        if(errors.hasErrors()){
            return ResponseEntity.ok("User doesn't exists.");
        }
        return ResponseEntity.ok("User exists.");
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserAccountDTO userAccountDTO, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        UserAccount userAccount = new UserAccount(0L, userAccountDTO.getUsername(),
                userAccountDTO.getPassword(), userAccountDTO.getEmail(), false);
        if(security.createAccount(userAccount)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
