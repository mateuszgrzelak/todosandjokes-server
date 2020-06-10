package pl.todosandjokes.api.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "userAccount",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JsonManagedReference
    private List<Todo> todos;

    private String email;

    private boolean isActivated;

    public UserAccount(String username, String password, String email, boolean isActivated, List<Todo> todos){
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActivated = isActivated;
        this.todos = todos;
    }

    public void addTodo(Todo todo){
        todos.add(todo);
        todo.setUserAccount(this);
    }

    public void removeTodo(int index){
        todos.remove(index).setUserAccount(null);
    }

    public Todo updateTodo(int index, Todo todo){
        todo.setUserAccount(this);
        return todos.set(index, todo);
    }


}
