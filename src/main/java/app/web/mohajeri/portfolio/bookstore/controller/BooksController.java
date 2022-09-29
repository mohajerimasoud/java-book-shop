package app.web.mohajeri.portfolio.bookstore.controller;


import app.web.mohajeri.portfolio.bookstore.model.Book;
import app.web.mohajeri.portfolio.bookstore.payload.response.GenericResponse;
import app.web.mohajeri.portfolio.bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/book")
public class BooksController {
    private BookRepository bookRepository;

    @Autowired
    public BooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    @GetMapping("/all")
    public String getAllBooks() {

        List<Book> allBooks = bookRepository.findAll();
        var res = new GenericResponse<List<Book>>();
        res.setResponseCode(200);
        res.setPayload(allBooks);
        return res.toString();

    }

    @PostMapping("")
    public String createBook(@Valid @RequestBody Book book, Errors errors) throws Exception {
        try {
            if (errors.hasErrors()) {
                log.info("req payload : " + book.toString());
                var res = new GenericResponse<String>();
                res.setResponseCode(400);
                res.setPayload(errors.getFieldError().toString());
                return res.toString();
            }
            var findWithIsbn = bookRepository.findBookByIsbn(book.getIsbn());
            log.info("=== findWithIsbn" + findWithIsbn.toString());
            if (bookRepository.findBookByIsbn(book.getIsbn()).isPresent()) {
                var res = new GenericResponse<String>();
                res.setResponseCode(400);
                res.setPayload("Book with ISBN : " + book.getIsbn() + "already exist .");
                return res.toString();
            }

            var result = bookRepository.save(book);
            if (result != null) {
                var res = new GenericResponse<Book>();
                res.setPayload(result);
                res.setResponseCode(200);
                return res.toString();
            } else {
                var res = new GenericResponse<String>();
                res.setPayload("Book not saved : DB issue");
                res.setResponseCode(500);
                return res.toString();
            }

        } catch (Exception e) {
            var res = new GenericResponse<String>();
            res.setPayload("Book not saved : Server issue" + e.toString());
            res.setResponseCode(500);
            return res.toString();
        }

    }

}
