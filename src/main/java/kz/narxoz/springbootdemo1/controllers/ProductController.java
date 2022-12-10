package kz.narxoz.springbootdemo1.controllers;
import kz.narxoz.springbootdemo1.Entity.Category;
import kz.narxoz.springbootdemo1.Entity.Product;
import kz.narxoz.springbootdemo1.service.CategoryService;
import kz.narxoz.springbootdemo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {
  @Autowired
  ProductService productService;
  @Autowired
  CategoryService categoryService;
  @Value("static/images/")
  private String viewPath;
  @Value("target/classes/static/images/")
  private String uploadPath;

  @GetMapping("/products")
  public String products(Model model) {
    model.addAttribute("products", productService.findAllProduct());
    model.addAttribute("categories", categoryService.findAllCategory());
    return "products";
  }
  @GetMapping("/buy")
  public String buy(){
    return "buy";
  }
  @GetMapping("/allproducts")
  public String all(Model model) {
    model.addAttribute("products", productService.findAllProduct());
    model.addAttribute("categories", categoryService.findAllCategory());
    return "allproducts";
  }

  @GetMapping("/index")
  public String index(Model model) {
    model.addAttribute("products", productService.findAllProduct());
    model.addAttribute("categories", categoryService.findAllCategory());
    return "index";
  }

  @GetMapping("products/new")
  @PreAuthorize("hasAnyRole('ROLE_SELLER')")
  public String addProductForm(Model model) {
    model.addAttribute("categories", categoryService.findAllCategory());
//    Product product = new Product();
//    model.addAttribute("product", product);
    return "add_product";
  }


  @PostMapping("/products")
  public String saveProduct(@RequestParam(name = "productname") String productname,
                            @RequestParam(name = "price") String price,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "star") int star,
                            @RequestParam(name = "image") MultipartFile image,
                            @RequestParam(name = "category_id") Long categoryId, Model model) {
    Product product = new Product();

    if (image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/jpg")) {
      try {
        String picName = DigestUtils.sha1Hex("image" + productname + "Picture");
        byte[] bytes = image.getBytes();
        Path path = Paths.get(uploadPath + picName + ".jpg");
        Files.write(path, bytes);

        product.setImage(picName);

        Category category = categoryService.finOneById(categoryId);
        if (category != null) {
          product.setCategory(category);
        }

        product.setProductname(productname);
        product.setPrice(price);
        product.setDescription(description);
        product.setStar(star);


        productService.saveProduct(product);

        return "redirect:/products";

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return "redirect:/products";
  }

  @GetMapping(value = "/img/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
  public @ResponseBody
  byte[] viewImage(@PathVariable(name = "url") String url) throws IOException {

    String imageUrl = viewPath + url + ".jpg";

    InputStream in;

    try {

      ClassPathResource resource = new ClassPathResource(imageUrl);
      in = resource.getInputStream();


    } catch (Exception e) {
      ClassPathResource resource = new ClassPathResource(imageUrl);
      in = resource.getInputStream();
      e.printStackTrace();
    }

    return IOUtils.toByteArray(in);

  }

  @GetMapping("/product/update/{id}")
  @PreAuthorize("hasAnyRole('ROLE_SELLER')")
  public String updateProductForm(@PathVariable("id") Long id, Model model) {
    model.addAttribute("product", productService.findOneById(id));
    List<Category> categories = categoryService.findAllCategory();
    model.addAttribute("categories", categories);
    return "updateProduct";

  }

  @GetMapping("/product/{id}")
  public String product(Model model, @PathVariable("id") Long id) {
    model.addAttribute("category", categoryService.findAllCategory());
    Product product = productService.findOneById(id);
    model.addAttribute("product", productService.findOneById(id));
    return "product";

  }


  @GetMapping("/product/delete/{id}")
  @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
  public String deleteProduct(@PathVariable("id") Long id) {
    productService.deleteProduct(id);
    return "redirect:/products";
  }

  @PostMapping("/product/update/{id}")
  @PreAuthorize("hasAnyRole('ROLE_SELLER')")
  public String updateProduct(@PathVariable(name = "id") Long id,
                              @RequestParam(name = "productname") String productname,
                              @RequestParam(name = "price") String price,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "star") int star,
                              @RequestParam(name = "image") MultipartFile image,
                              @RequestParam(name = "category_id") Long categoryId, Model model) {
    Product product = productService.findOneById(id);
    if (product!=null) {
      if (image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/jpg")) {
        try {

          String picName = DigestUtils.sha1Hex("image" + productname + "Picture");

          byte[] bytes = image.getBytes();
          Path path = Paths.get(uploadPath + picName + ".jpg");
          Files.write(path, bytes);

          product.setImage(picName);

          product.setProductname(productname);
          product.setPrice(price);
          product.setDescription(description);
          product.setStar(star);


          productService.saveProduct(product);

          return "redirect:/products";

        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      return "redirect:/products";
    }
    return "redirect:/product?error";
  }
}
