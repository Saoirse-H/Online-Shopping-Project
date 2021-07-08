package stock.repository;

import model.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Item, Integer> {
    Iterable<Item> findAllByCategory(String category);
    List<Item> findBySellerId(int sellerId);
}
