package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Item;
import com.example.demo.model.Size;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{
    List<Item> findBySize(Size size);
    List<Item> findByTitle(String title);
    List<Item> findByPrice(float price);
}

// Now we can use JpaRepository’s methods without implementing them.



