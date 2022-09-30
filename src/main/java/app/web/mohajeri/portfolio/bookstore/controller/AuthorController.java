package app.web.mohajeri.portfolio.bookstore.controller;

import app.web.mohajeri.portfolio.bookstore.model.Author;
import app.web.mohajeri.portfolio.bookstore.model.Book;
import app.web.mohajeri.portfolio.bookstore.repository.AuthorRepository;
import app.web.mohajeri.portfolio.bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> allAuthors = authorRepository.findAll();
        return ResponseEntity.status(200).body(allAuthors);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable String id) {
        Optional<Author> result = authorRepository.findById(id);
        if (result.isPresent()) {
            authorRepository.deleteById(id);
            return ResponseEntity.status(200).body("author deleted successfully");
        } else {
            return ResponseEntity.status(404).body("author not found ");

        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Object> assignBookToAuthor(@PathVariable(name = "id") String authorID, @RequestBody String bookId) {
        try {
            Optional<Author> authorResult = authorRepository.findById(authorID);
            if (authorResult.isEmpty()) {
                return ResponseEntity.status(404).body("Author not found");
            }

            log.info("Book Id : " + bookId);
            Optional<Book> bookResult = bookRepository.findById("6335a6155c790258bf62532b");
            if (bookResult.isEmpty()) {
                return ResponseEntity.status(404).body("Book not found");
            }
            if (authorResult.get().getBooks() != null) {
                List<Book> currentBooks = authorResult.get().getBooks();
                currentBooks.add(bookResult.get());
                authorResult.get().setBooks(currentBooks);
            } else {
                List<Book> currentBooks = new ArrayList<>();
                currentBooks.add(bookResult.get());
                authorResult.get().setBooks(currentBooks);

            }

//            authorResult.get().setBooks(currentBooks);
//            authorResult.get().setBooks();
            authorRepository.save(authorResult.get());
            return ResponseEntity.status(200).body(authorResult.get());
        } catch (Exception e) {
            log.error("=== error in adding book to author " + e);
            return ResponseEntity.status(500).body("Some error occurred " + e);

        }
    }


    @PostMapping("")
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody Author author, Errors errors) throws Exception {
        log.info("=== author" + author.toString());
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.status(400).body(Objects.requireNonNull(errors.getFieldError()).toString());
            }

            if (authorRepository.findByNationalCode(author.getNationalCode()).isPresent()) {
                return ResponseEntity.status(400).body("Author with nationalCode : " + author.getNationalCode() + "already exist .");
            }

            var result = authorRepository.save(author);
            return ResponseEntity.status(200).body(result);

        } catch (Exception e) {
            return ResponseEntity.status(200).body("Author not saved : Server issue " + e);
        }

    }

}
