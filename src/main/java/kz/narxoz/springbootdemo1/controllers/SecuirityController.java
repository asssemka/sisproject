package kz.narxoz.springbootdemo1.controllers;
import kz.narxoz.springbootdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecuirityController {

  @Autowired
  private UserService userService;

  @GetMapping(value = "/signin")
  public String signIn(Model model){
    return "signin";
  }

  @GetMapping(value = "/profile")
  @PreAuthorize("isAuthenticated()")
  public String profile(Model model){
    return "profile";
  }

  @GetMapping(value = "/admin")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public String admin(Model model){
    return "admin";
  }

  @PostMapping(value = "/updatepassword")
  @PreAuthorize("isAuthenticated()")
  public String updatePassword(@RequestParam(name = "old_password") String oldPassword,
                               @RequestParam(name = "new_password") String newPassword,
                               @RequestParam(name = "repeat_new_password") String repeatNewPassword){
    userService.updateUserPassword(oldPassword, newPassword, repeatNewPassword);
    return "redirect:/profile";
  }

  @GetMapping(value = "/signup")
  public String signUp(Model model){
    return "signup";
  }

  @PostMapping(value = "/register")
  public String toRegister(@RequestParam(name = "user_email") String userEmail,
                           @RequestParam(name = "user_password") String userPassword,
                           @RequestParam(name = "user_re_password") String userRePassword,
                           @RequestParam(name = "user_full_name") String userFullName){
    if(userService.registerUser(userEmail, userPassword, userRePassword, userFullName)!=null){
      return "redirect:/signup?success";
    }
    return "redirect:/signup?error";
  }

  @GetMapping("/403")
  public String err(){
    return "403";
  }
}
