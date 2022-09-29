package app.web.mohajeri.portfolio.bookstore.repository;

import app.web.mohajeri.portfolio.bookstore.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;


public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByNationalCode(Long NationalCode);
}
