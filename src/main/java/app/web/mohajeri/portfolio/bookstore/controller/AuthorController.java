package app.web.mohajeri.portfolio.bookstore.controller;

import app.web.mohajeri.portfolio.bookstore.model.Author;
import app.web.mohajeri.portfolio.bookstore.model.Book;
import app.web.mohajeri.portfolio.bookstore.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> allAuthors = authorRepository.findAll();
        return ResponseEntity.status(200).body(allAuthors);
    }




    @PostMapping("")
    public ResponseEntity<String> createAuthor(@Valid @RequestBody Author author, Errors errors) throws Exception {
        log.info("=== author" + author.toString());
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.status(400).body(errors.getFieldError().toString());
            }
            var findWithNationalCode = authorRepository.findByNationalCode(author.getNationalCode());
            log.info("=== findWithNationalCode " + findWithNationalCode.toString());
            if (authorRepository.findByNationalCode(author.getNationalCode()).isPresent()) {
                return ResponseEntity.status(400).body("Author with nationalCode : " + author.getNationalCode() + "already exist .");
            }

            var result = authorRepository.save(author);
            if (result != null) {
                return ResponseEntity.status(200).body(result.toString());
            } else {
                return ResponseEntity.status(500).body("Author not saved : DB issue");
            }

        } catch (Exception e) {
            return ResponseEntity.status(200).body("Author not saved : Server issue " + e);
        }

    }

}
