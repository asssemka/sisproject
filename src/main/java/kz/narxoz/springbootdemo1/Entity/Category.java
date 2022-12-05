package kz.narxoz.springbootdemo1.Entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "categoryname", nullable = false)
  private String name;
}
