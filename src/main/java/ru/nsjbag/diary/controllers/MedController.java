package ru.nsjbag.diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsjbag.diary.entities.*;
import ru.nsjbag.diary.services.ActivityService;
import ru.nsjbag.diary.services.BloodPressureService;
import ru.nsjbag.diary.services.NutritionService;
import ru.nsjbag.diary.services.UserService;

import java.util.List;

@Controller
public class MedController {
    @Autowired
    private UserService userService;

    @Autowired
    private BloodPressureService bloodPressureService;

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private ActivityService activityService;

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
    @GetMapping("/med/user/info/{username}")
    public String userInfo(@PathVariable String username,
                           Model model) {

        model.addAttribute("username", username);
        model.addAttribute("user", new User());

        List<BloodPressureEntry> bloodPressureEntryList = bloodPressureService.getBloodPressure(username);
        model.addAttribute("entriesPressure", bloodPressureEntryList);
        model.addAttribute("entryPressure", new BloodPressureEntry());

        List< NutritionEntry> nutritionEntryList = nutritionService.getAllNutritionEntries(username);
        model.addAttribute("entriesNutrition", nutritionEntryList);
        model.addAttribute("entryNutrition", new NutritionEntry());
        model.addAttribute("dish", new DIshHandBook());

        List<ActivityEntry> activityEntryList = activityService.getAllActivities(username);
        model.addAttribute("entriesActivity", activityEntryList);
        model.addAttribute("entryActivity", new ActivityEntry());
        model.addAttribute("activity", new ActivityHandBook());


        return "userInfoPage";
    }

    @GetMapping("/med/userData/filter")
    public String usersFilter(@RequestParam String username,
                              @RequestParam(value = "date", required = false) String date,
                              Model model) {

        model.addAttribute("username", username);
        model.addAttribute("user", new User());
        model.addAttribute("date", date);

        List<BloodPressureEntry> filteredBloodPressureEntryList = bloodPressureService.getBloodPressure(username, date);
        model.addAttribute("entriesPressure", filteredBloodPressureEntryList);
        model.addAttribute("entryPressure", new BloodPressureEntry());

        List< NutritionEntry> filteredNutritionEntryList = nutritionService.getAllNutritionEntries(username, date);
        model.addAttribute("entriesNutrition", filteredNutritionEntryList);
        model.addAttribute("entryNutrition", new NutritionEntry());
        model.addAttribute("dish", new DIshHandBook());

        List<ActivityEntry> filteredActivityEntryList = activityService.getAllActivities(username, date);
        model.addAttribute("entriesActivity", filteredActivityEntryList);
        model.addAttribute("entryActivity", new ActivityEntry());
        model.addAttribute("activity", new ActivityHandBook());

        return "userInfoPage";
    }

}
