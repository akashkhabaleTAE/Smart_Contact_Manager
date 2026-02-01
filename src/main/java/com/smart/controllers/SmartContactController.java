package com.smart.controllers;

import com.smart.entitits.User;
import com.smart.entitits.UserRole;
import com.smart.helper.Message;
import com.smart.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class SmartContactController
{
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserService userService;

    @GetMapping
    public String home(Model model){
        model.addAttribute("title","HOME - Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "ABOUT - Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "SIGNUP - Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "terms", defaultValue = "false") boolean terms, Model model, HttpSession session){
        try {
            session.removeAttribute("message");
            if (!terms)
                throw new RuntimeException("Terms and conditions must be accepted!");
            if (result.hasErrors()){
                return "signup";
            }
            user.setUserRole(UserRole.USER);
            user.setEnabled(true);
            user.setPassword(encoder.encode(user.getPassword()));
            final User savedUser = this.userService.saveUser(user);
            System.out.println("Saved User:  " + savedUser.getEmail());
            model.addAttribute("user", new User());
            Message message =  new Message("User registered successfully.","alert-success");
            session.setAttribute("message",message);
            return "redirect:/signup";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            Message message = new Message("User registration failed! " + e.getMessage(), "alert-danger");
            session.setAttribute("message",message);
            return "signup";
        }
    }
}
