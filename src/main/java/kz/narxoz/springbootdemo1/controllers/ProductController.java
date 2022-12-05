package kz.narxoz.springbootdemo1.controllers;
import kz.narxoz.springbootdemo1.Entity.Category;
import kz.narxoz.springbootdemo1.Entity.Product;
import kz.narxoz.springbootdemo1.repository.CategoryRepository;
import kz.narxoz.springbootdemo1.service.CategoryService;
import kz.narxoz.springbootdemo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
  @Autowired
  ProductService productService;
  @Autowired
  CategoryService categoryService;

  @GetMapping("/products")
  public String products(Model model) {
    model.addAttribute("products", productService.findAllProduct());
    model.addAttribute("categories", categoryService.findAllCategory());
    return "products";
  }

  @GetMapping("products/new")
  public String addProductForm(Model model) {
    model.addAttribute("categories", categoryService.findAllCategory());
    Product product = new Product();
    model.addAttribute("product", product);
    return "add_product";
  }

  @PostMapping("/products")
  public String saveProduct(@ModelAttribute("product") Product product) {
    productService.saveProduct(product);
    return "redirect:/products";
  }

  @GetMapping("/product/update/{id}")
  public String updateProductForm(@PathVariable("id") Long id, Model model) {
    model.addAttribute("product", productService.findOneById(id));
    return "updateProduct";

  }

  @GetMapping("/product/delete/{id}")
  public String deleteProduct(@PathVariable("id") Long id) {
    productService.deleteProduct(id);
    return "redirect:/products";
  }

  @PostMapping("/product/update/{id}")
  public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
    Product existingProduct = productService.findOneById(id);
    existingProduct.setId(id);
    existingProduct.setProductname(product.getProductname());
    existingProduct.setPrice(product.getPrice());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setStar(product.getStar());
    existingProduct.setImage(product.getImage());
    productService.saveProduct(existingProduct);
    return "redirect:/products";


  }
}
