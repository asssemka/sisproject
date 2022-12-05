package kz.narxoz.springbootdemo1.repository;
import kz.narxoz.springbootdemo1.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category,Long>{
}
