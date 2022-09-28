package app.web.mohajeri.portfolio.bookstore.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BooksController {
    @GetMapping("/all")
    public String getAllBooks() {
        return "All Books 😎";
    }
}
