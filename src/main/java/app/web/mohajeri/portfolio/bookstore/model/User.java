package app.web.mohajeri.portfolio.bookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(min = 5 ,message = "نام کاربری باید حداقل 5 کارکتر باشد.")
    private String username;

    @NotBlank
    private String password;
}
