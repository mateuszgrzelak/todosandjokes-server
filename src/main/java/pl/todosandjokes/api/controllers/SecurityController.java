package pl.todosandjokes.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.todosandjokes.api.model.dto.UserAccountDTO;
import pl.todosandjokes.api.model.dto.UserDTO;
import pl.todosandjokes.api.model.pojo.Response;
import pl.todosandjokes.api.model.entity.UserAccount;
import pl.todosandjokes.api.services.SecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class SecurityController {

    private SecurityService security;
    private BCryptPasswordEncoder encoder;

    public SecurityController(SecurityService security, BCryptPasswordEncoder encoder) {
        this.security = security;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Validated UserDTO user, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.notFound().build();
        }
        Optional<UserAccount> userAccount = security.getUserAccount(user);
        if(userAccount.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String jwt = security.createJWT(userAccount.get());
        return new ResponseEntity<>(new Response(jwt) ,HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody @Valid UserAccountDTO userAccountDTO, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserAccount userAccount = new UserAccount(userAccountDTO.getUsername(),
               userAccountDTO.getPassword(), userAccountDTO.getEmail(), false, new ArrayList<>());
        if(security.createAccount(userAccount)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
