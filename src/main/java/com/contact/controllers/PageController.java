package com.contact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contact.entities.User;
import com.contact.forms.UserForm;
import com.contact.helper.Message;
import com.contact.helper.MessageType;
import com.contact.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

  @Autowired
  UserService userService;

  @RequestMapping("/home")
  public String home() {
    System.out.println("home page loading");
    return "home";
  }

  // about route

  @RequestMapping("/about")
  public String aboutPage(Model model) {

    model.addAttribute("isLogin", true);
    System.out.println("about pate loading");
    return "about";
  }

  // services route

  @RequestMapping("/services")
  public String servicepage() {
    System.out.println("about pate loading");
    return "services";
  }

  // contaact page

  @RequestMapping("/contact")
  public String contactPage() {
    System.out.println("contact  page loading");
    return new String("contact");
  }

  // login page

  @RequestMapping("/login")
  public String loginPage() {
    System.out.println("login page loading");
    return new String("login");
  }

  // signup page

  @RequestMapping("/signup")
  public String signupPage(Model model) {
    // we can also add default data

    UserForm userForm = new UserForm();

    model.addAttribute("userForm", userForm);

    return new String("signup");
  }

  // Procession Register page

  @RequestMapping(value = "/do-register", method = RequestMethod.POST)
  public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,
      HttpSession session) {

    System.out.println(userForm);
    // fetch data from form (creating form class..)

    // validate data

    if (bindingResult.hasErrors()) {
      System.out.println("Error" + bindingResult);
      return "signup";
    }
    // save data to database

    // User user = User.builder()
    // .name(userForm.getName())
    // .email(userForm.getEmail())
    // .password(userForm.getPassword())
    // .phoneNumber(userForm.getPhoneNumber())
    // .about(userForm.getAbout())
    // .profilePic("")
    // .build();

    User user = new User();
    user.setName(userForm.getName());
    user.setEmail(userForm.getEmail());
    user.setPassword(userForm.getPassword());
    user.setPhoneNumber(userForm.getPhoneNumber());
    user.setAbout(userForm.getAbout());
    user.setProfilePic("https://www.pexels.com/photo/close-up-photo-of-lion-s-head-2220336/");

    User savedUser = userService.saveUser(user);

    System.out.println(savedUser);
    // if any message to show

    Message message = Message.builder().content("Registration Successfull !!").type(MessageType.red).build();
    session.setAttribute("message", message);
    // redirect to signup page
    return "redirect:/signup";
  }

}
