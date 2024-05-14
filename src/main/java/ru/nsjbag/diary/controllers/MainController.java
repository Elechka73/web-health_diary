package ru.nsjbag.diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsjbag.diary.services.UserService;

import java.security.Principal;

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
                return "redirect:/";
            }
        }
        model.addAttribute("error", true);
        return "mainUnauthorized";
    }
    @GetMapping("")
    public String mainPage(Principal principal) {
        if (principal != null) {
            if (userService.getAuthorityByusername(principal.getName()).equals("ROLE_USER")) {
                return "redirect:/user/main";
            }
            if (userService.getAuthorityByusername(principal.getName()).equals("ROLE_MED")) {
                return "redirect:/med/main";
            }
        }
        return "mainUnauthorized";
    }
}
