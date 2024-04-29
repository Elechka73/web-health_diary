package ru.nsjbag.diary.controllers;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.entities.User;
import ru.nsjbag.diary.services.BloodPressureService;
import ru.nsjbag.diary.services.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private BloodPressureService bloodPressureService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/pressure")
    public String userPressure(Principal principal, Model model) {

        List<BloodPressureEntry> bloodPressureEntryList = bloodPressureService.getBloodPressureByUsername(principal.getName());
        model.addAttribute("entries", bloodPressureEntryList);
        model.addAttribute("entry", new BloodPressureEntry());
        return "pressure";
    }

    @PostMapping("/user/pressure/add")
    public String addPressure(@RequestParam String systolic,
                              @RequestParam String diastolic,
                              Principal principal) {
        BloodPressureEntry entry = new BloodPressureEntry();
        entry.setDateOfEntry(LocalDateTime.now());
        entry.setSystolic(Integer.parseInt(systolic.split("\\.")[0]));
        entry.setDiastolic(Integer.parseInt(diastolic.split("\\.")[0]));
        User user = userService.getUserByUserName(principal.getName());
        entry.setDiary(user.getHealthDiary());
        bloodPressureService.add(entry);

        return "redirect:/user/pressure";
    }
    @GetMapping("/user/pressure/delete/{id}")
    public String deleteReaction(@PathVariable(value = "id") int id) {
        BloodPressureEntry entry = bloodPressureService.findById(id);
        bloodPressureService.delete(entry);
        return "redirect:/user/pressure";
    }

}
