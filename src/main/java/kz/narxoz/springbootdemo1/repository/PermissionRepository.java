package kz.narxoz.springbootdemo1.repository;
import kz.narxoz.springbootdemo1.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Long> {
  Permission findByPermission(String permission);

}