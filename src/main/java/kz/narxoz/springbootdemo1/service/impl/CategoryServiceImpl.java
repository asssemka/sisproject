package kz.narxoz.springbootdemo1.service.impl;
import kz.narxoz.springbootdemo1.Entity.Category;
import kz.narxoz.springbootdemo1.repository.CategoryRepository;
import kz.narxoz.springbootdemo1.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  CategoryRepository categoryRepository;
  @Override
  public List<Category> findAllCategory() {
    return categoryRepository.findAll();
  }

  @Override
  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public Category finOneById(Long id) {
    return categoryRepository.findById(id).orElse(null);
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }
}
