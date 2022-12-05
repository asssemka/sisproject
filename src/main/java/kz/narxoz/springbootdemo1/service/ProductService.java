package kz.narxoz.springbootdemo1.service;
import  kz.narxoz.springbootdemo1.Entity.Product ;

import java.util.List;

public interface ProductService {
  List<Product>findAllProduct();
  Product saveProduct(Product product);
  Product findOneById(Long id);
  void deleteProduct(Long id);

}


