package kz.narxoz.springbootdemo1.Model;
import kz.narxoz.springbootdemo1.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name="comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "comment", nullable = false)
  private String comment;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  private Product product;

}
