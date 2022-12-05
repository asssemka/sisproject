package kz.narxoz.springbootdemo1.service;
import kz.narxoz.springbootdemo1.Entity.Category;

import java.util.List;

public interface CategoryService {
  List<Category> findAllCategory();
  Category saveCategory(Category category);
  Category finOneById(Long id);
  void deleteCategory(Long id);

}
