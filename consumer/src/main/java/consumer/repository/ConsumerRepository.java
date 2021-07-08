package consumer.repository;

import consumer.model.Consumer;

import java.util.Optional;

import model.User;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
    Optional<Consumer> findByUsernameAndPassword(String username, String password);
    Optional<Consumer> findByUsername(String username);
}
