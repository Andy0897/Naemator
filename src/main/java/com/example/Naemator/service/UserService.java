package com.example.Naemator.service;

import com.example.Naemator.model.User;
import com.example.Naemator.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class UserService {
    BCryptPasswordEncoder encoder;
    UserRepository userRepository;

    public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public String submitUser(User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors() || checkIfExistsUserByUsername(user.getUsername()) || checkIfExistsUserByEmail(user.getEmail())) {
            model.addAttribute("user", user);
            model.addAttribute("existsUserByUsername", checkIfExistsUserByUsername(user.getUsername()));
            model.addAttribute("existsUserByEmail", checkIfExistsUserByEmail(user.getEmail()));
            return "register";
        }
        user.setEnable(true);
        user.setRole("USER");
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    public String blockUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setEnable(false);
        userRepository.save(user);
        return "redirect:/";
    }

    public String unblockUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setEnable(true);
        userRepository.save(user);
        return "redirect:/";
    }

    public boolean checkIfExistsUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }

    public boolean checkIfExistsUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return true;
    }
}