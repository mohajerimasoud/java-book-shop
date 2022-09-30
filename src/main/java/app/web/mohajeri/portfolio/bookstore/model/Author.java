package app.web.mohajeri.portfolio.bookstore.model;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Document(collection = "authors")
@Data
public class Author {

    @Id
    private String id;

    @NotBlank(message = "فیلد اسم اجباری است")
    @Size(max = 30, message = "نام کتاب باید کمتر از 30 کارکتر باشد")
    private String name;

    @NotNull(message = "فیلد سال تولد اجباری است")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date birthDate;

    @NotNull(message = "فیلد کد ملی اجباری است")
    @Min(value = 1 , message = "مقدار وارد شده برای کدملی معتبر نمی باشد")
    private Long nationalCode;

    @NotNull(payload = {})
    @DBRef()
    private List<Book> books;
}
