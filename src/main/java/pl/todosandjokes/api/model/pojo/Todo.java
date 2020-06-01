package pl.todosandjokes.api.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class Todo {

    @NotBlank
    private String description;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate targetDate;

    public Todo(String description, LocalDate targetDate) {
        this.description = description;
        this.targetDate = targetDate;
    }
    public Todo(){};

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

}
