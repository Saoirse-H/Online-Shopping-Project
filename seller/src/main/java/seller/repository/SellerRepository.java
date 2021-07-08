package seller.repository;

import seller.model.Seller;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByUsernameAndPassword(String username, String password);
    Optional<Seller> findByUsername(String username);
}
