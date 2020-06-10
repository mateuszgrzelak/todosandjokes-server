package pl.todosandjokes.api.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String description;

    private LocalDate targetDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private UserAccount userAccount;

    public Todo(String description, LocalDate targetDate) {
        this.description = description;
        this.targetDate = targetDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id == todo.id &&
                Objects.equals(description, todo.description) &&
                Objects.equals(targetDate, todo.targetDate) &&
                Objects.equals(userAccount, todo.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, targetDate, userAccount);
    }
}
