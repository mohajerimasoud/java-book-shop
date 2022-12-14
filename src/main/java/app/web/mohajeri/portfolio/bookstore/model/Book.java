package app.web.mohajeri.portfolio.bookstore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "books")
@Data
public class Book {
    @Id
    private String id;

    @NotBlank
    @Size(max = 30, message = "نام کتاب باید کمتر از 30 کارکتر باشد")
    private String name;

    @NotNull
    @Size(max = 30, message = "نام نویسنده باید کمتر از 30 کارکتر باشد")
    private List<String> author = new ArrayList<>();

    @NotBlank
    @Size(max = 30, message = "شابک کتاب اجباری است")
    private String isbn;

    @NotNull
    @Min(value = 10_000, message = "مقدار وارد شده برای قیمت کمتر از حداقل مجاز است")
    private Long price;
}
