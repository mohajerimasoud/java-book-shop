package app.web.mohajeri.portfolio.bookstore.controller;

import app.web.mohajeri.portfolio.bookstore.model.Book;
import app.web.mohajeri.portfolio.bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/book")
public class BooksController {
    private final BookRepository bookRepository;

    @Autowired
    public BooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        return ResponseEntity.status(200).body(allBooks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        Optional<Book> result = bookRepository.findById(id);
        if (result.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.status(200).body("Book deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Book not found ");

        }
    }


    @PostMapping("")
    public ResponseEntity<String> createBook(@Valid @RequestBody Book book, Errors errors) throws Exception {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.status(400).body(Objects.requireNonNull(errors.getFieldError()).toString());
            }

            if (bookRepository.findBookByIsbn(book.getIsbn()).isPresent()) {
                return ResponseEntity.status(400).body("Book with ISBN : " + book.getIsbn() + "already exist .");
            }

            var result = bookRepository.save(book);
            return ResponseEntity.status(200).body(result.toString());

        } catch (Exception e) {
            return ResponseEntity.status(200).body("Book not saved : Server issue" + e.toString());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> createBook(@Valid @RequestBody Book book, @PathVariable String id, Errors errors) throws Exception {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.status(400).body(Objects.requireNonNull(errors.getFieldError()).toString());
            }
            var result = bookRepository.findById(id);
            if (result.isEmpty()) {
                return ResponseEntity.status(200).body("Book with this id doesn't exist ");
            } else {
//                result.get().setAuthor(book.getAuthor());
                result.get().setName(book.getName());
                result.get().setIsbn(book.getIsbn());
                bookRepository.save(result.get());
                return ResponseEntity.status(200).body("Book updated successfully");
            }

        } catch (Exception e) {
            return ResponseEntity.status(200).body("Book not saved : Server issue" + e);
        }
    }


}
