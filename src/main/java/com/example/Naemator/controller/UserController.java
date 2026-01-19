package com.example.Naemator.controller;

import com.example.Naemator.service.UserService;
import com.example.Naemator.model.User;
import com.example.Naemator.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {
    UserService userService;
    UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/home"})
    public String getHome() {
        return "home";
    }

    @GetMapping("/login")
    public String getSignIn(Principal principal) {
        if (principal != null) {
            return "redirect:/access-denied";
        }
        return "login";
    }

    @GetMapping("/register")
    public String getSignUp(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/submit")
    public String submitUser(@Valid User user, BindingResult bindingResult, Model model) {
        return userService.submitUser(user, bindingResult, model);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String getProfilePage(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "access-denied";
    }
}