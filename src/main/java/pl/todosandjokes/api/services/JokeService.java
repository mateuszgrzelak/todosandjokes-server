package pl.todosandjokes.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.todosandjokes.api.model.pojo.Joke;

@Service
public class JokeService {

    RestTemplate restTemplate = new RestTemplate();

    public Joke[] getJokes(){
        return restTemplate.getForObject("https://official-joke-api.appspot.com/random_ten", Joke[].class);
    }

}
