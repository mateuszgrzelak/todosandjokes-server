package pl.todosandjokes.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.todosandjokes.api.model.DTO.UserAccountDTO;
import pl.todosandjokes.api.model.DTO.UserDTO;
import pl.todosandjokes.api.model.pojo.Response;
import pl.todosandjokes.api.model.pojo.UserAccount;
import pl.todosandjokes.api.services.security.Security;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT} )
public class Authorization {

    private Security security;
    private BCryptPasswordEncoder encoder;

    public Authorization(Security security, BCryptPasswordEncoder encoder) {
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
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        UserAccount userAccount = new UserAccount(0L, userAccountDTO.getUsername(),
                encoder.encode(userAccountDTO.getPassword()), userAccountDTO.getEmail(), false);
        if(security.createAccount(userAccount)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}
