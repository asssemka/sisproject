package kz.narxoz.springbootdemo1.repository;
import kz.narxoz.springbootdemo1.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product,Long> {
}
