package pl.todosandjokes.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import pl.todosandjokes.api.model.pojo.Joke;
import pl.todosandjokes.api.services.JokeService;

@RestController
public class JokesController {

    JokeService jokesService;

    public JokesController(JokeService jokesService) {
        this.jokesService = jokesService;
    }

    @GetMapping("/jokes")
    public ResponseEntity<Joke[]> getJokes(){
        try {
            return new ResponseEntity<>(jokesService.getJokes(), HttpStatus.OK);
        }catch(RestClientException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
