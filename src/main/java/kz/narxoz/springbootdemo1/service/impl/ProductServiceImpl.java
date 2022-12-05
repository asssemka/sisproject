package kz.narxoz.springbootdemo1.service.impl;
import kz.narxoz.springbootdemo1.Entity.Category;
import kz.narxoz.springbootdemo1.Entity.Product;
import kz.narxoz.springbootdemo1.repository.CategoryRepository;
import kz.narxoz.springbootdemo1.repository.ProductRepository;
import kz.narxoz.springbootdemo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  ProductRepository productRepository;

  @Override
  public List<Product> findAllProduct() {
    return productRepository.findAll();
  }

  @Override
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Product findOneById(Long id) {
    return productRepository.findById(id).orElse(null);
  }

  @Override
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);

  }




}

