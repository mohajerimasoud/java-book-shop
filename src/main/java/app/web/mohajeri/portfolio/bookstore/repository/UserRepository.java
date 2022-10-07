package app.web.mohajeri.portfolio.bookstore.repository;

import app.web.mohajeri.portfolio.bookstore.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findUserByUsername(String username);
}
