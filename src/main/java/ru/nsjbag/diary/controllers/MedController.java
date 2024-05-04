package ru.nsjbag.diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsjbag.diary.entities.User;
import ru.nsjbag.diary.services.UserService;

import java.security.Principal;
import java.util.Date;

@Controller
public class MedController {
    @Autowired
    private UserService userService;

    @GetMapping("/med/main")
    public String mainMedPage(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsersWithUserRole());
        return "mainMed";
    }
    @GetMapping("/med/users/filter")
    public String usersFilter(@RequestParam(value = "lastName", required = false) String lastName,
                              @RequestParam(value = "firstName", required = false) String firstName,
                              @RequestParam(value = "dob", required = false) String dob,
                              @RequestParam(value = "insurance", required = false) String insurance,
                              Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsersWithUserRole(lastName, firstName,dob, insurance));
        model.addAttribute("insurance", insurance);
        model.addAttribute("dob", dob);
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);

        return "mainMed";
    }
}
