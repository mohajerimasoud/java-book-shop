package app.web.mohajeri.portfolio.bookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "books")
@Data
public class Book {
    @Id
    private String id;

    @NotBlank
    @Size(max = 30, message = "نام کتاب باید کمتر از 30 کارکتر باشد")
    private String name;

    @NotBlank
    @Size(max = 30, message = "نام نویسنده باید کمتر از 30 کارکتر باشد")
    private String author;

    @NotBlank
    @Size(max = 30, message = "شابک کتاب اجباری است")
    private String isbn;
}
