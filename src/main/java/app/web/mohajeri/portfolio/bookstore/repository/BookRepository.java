package app.web.mohajeri.portfolio.bookstore.repository;

import app.web.mohajeri.portfolio.bookstore.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository  extends MongoRepository<Book,String> {
    Optional<Book> findBookByIsbn(String name);
}
