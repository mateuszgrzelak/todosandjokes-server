package pl.todosandjokes.api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.todosandjokes.api.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String password;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

}
