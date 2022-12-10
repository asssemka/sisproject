package kz.narxoz.springbootdemo1.service.impl;

import kz.narxoz.springbootdemo1.Entity.Product;
import kz.narxoz.springbootdemo1.Model.User;
import kz.narxoz.springbootdemo1.repository.UserRepository;
import kz.narxoz.springbootdemo1.service.PermissionService;
import kz.narxoz.springbootdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private PermissionService permissionService;


  @Override
  public List<User> findOnebyId(Long id) {
      return (List<User>) userRepository.findById(id).orElse(null);
    }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = getUserByEmail(username);
    if(user==null) throw new UsernameNotFoundException("User Not Found");
    return user;
  }

  @Override
  public User updateUserPassword(String oldPassword, String newPassword, String repeatNewPassword) {
    User currentUser = getCurrentUser();
    if(passwordEncoder.matches(oldPassword, currentUser.getPassword())){
      if(newPassword.equals(repeatNewPassword)){
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(currentUser);
      }
    }
    return null;
  }

  @Override
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)){
      User user = (User) authentication.getPrincipal();
      return user;
    }
    return null;
  }

  @Override
  public User registerUser(String email, String password, String rePassword, String fullName) {
    if(password.trim().equals(rePassword.trim())){
      User user = getUserByEmail(email);
      if(user==null){
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFullName(fullName);
        newUser.setPermissions(permissionService.simpleUserPermissions());
        return userRepository.save(newUser);
      }
    }
    return null;
  }
}
