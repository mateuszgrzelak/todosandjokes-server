package pl.todosandjokes.api.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Joke implements Serializable {

    private Integer id;
    private String type;
    private String setup;
    private String punchline;


}
