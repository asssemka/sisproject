package kz.narxoz.springbootdemo1.Model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseModel implements UserDetails {

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "full_name")
  private String fullName;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Permission> permissions;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return permissions;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
