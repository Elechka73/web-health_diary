package ru.nsjbag.diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsjbag.diary.entities.User;
import ru.nsjbag.diary.services.UserService;

@Controller
public class MainController {
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    @PostMapping("/auth")
    public String authenticateUser(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   Model model) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails != null) {
            String storedPassword = userDetails.getPassword();
            if (password.equals(storedPassword)) {
                model.addAttribute("username", username);
                return "redirect:/main";
            }
        }
        model.addAttribute("error", true);
        return "main";
    }
    @GetMapping("")
    public String mainPage() {
        return "main";
    }

}
