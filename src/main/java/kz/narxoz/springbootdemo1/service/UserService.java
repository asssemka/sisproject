package kz.narxoz.springbootdemo1.service;
import kz.narxoz.springbootdemo1.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
  List<User> findOnebyId(Long id);
  User getUserByEmail(String email);
  User updateUserPassword(String oldPassword, String newPassword, String repeatNewPassword);
  User getCurrentUser();
  User registerUser(String email, String password, String rePassword, String fullName);

}
