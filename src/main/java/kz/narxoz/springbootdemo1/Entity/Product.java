package kz.narxoz.springbootdemo1.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "productname", nullable = false)
  private String productname;

  @Column(name = "star", nullable = false)
  private int star;

  @Column(name = "price", nullable = false)
  private String price;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "image", nullable = false)
  private String image;

  @ManyToOne(fetch = FetchType.EAGER)
  private Category category;
}
