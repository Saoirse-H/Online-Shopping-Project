package cart.repository;

import model.Cart;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserIdAndItemId(int userId, int itemId);
    List<Cart> findByUserId(int userId);
}
