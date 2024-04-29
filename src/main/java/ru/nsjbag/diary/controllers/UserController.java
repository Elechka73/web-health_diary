package ru.nsjbag.diary.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.entities.User;
import ru.nsjbag.diary.services.BloodPressureService;
import ru.nsjbag.diary.services.UserService;


import java.security.Principal;
import java.time.LocalDateTime;


@Controller
public class UserController {

    @Autowired
    private BloodPressureService bloodPressureService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/pressure")
    public String userPressure(@RequestParam(defaultValue = "0") int page,
                               Principal principal,
                               Model model) {
        if (page < 0)  page = 0;

        Pageable pageable = PageRequest.of(page, 15);

        Page<BloodPressureEntry> bloodPressureEntries = bloodPressureService.getBloodPressure(principal.getName(), pageable);
        model.addAttribute("entries", bloodPressureEntries.getContent());
        model.addAttribute("entry", new BloodPressureEntry());

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bloodPressureEntries.getTotalPages());

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
