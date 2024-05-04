package ru.nsjbag.diary.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsjbag.diary.entities.*;
import ru.nsjbag.diary.services.*;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


@Controller
public class UserController {

    @Autowired
    private BloodPressureService bloodPressureService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityHandBookService activityHandBookService;

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private DishHandBookService dishHandBookService;

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



    @GetMapping("/user/main")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String mainPageUser(Model model, Principal principal) {
        Pageable pageable = PageRequest.of(0, 5);

        Page<BloodPressureEntry> bloodPressureEntries = bloodPressureService.getBloodPressure(principal.getName(), pageable);
        model.addAttribute("entriesPressure", bloodPressureEntries.getContent());
        model.addAttribute("entryPressure", new BloodPressureEntry());

        Page<ActivityEntry> activityEntries = activityService.getAllActivities(principal.getName(), pageable);
        model.addAttribute("entriesActivity", activityEntries.getContent());
        model.addAttribute("activity", new ActivityHandBook());
        model.addAttribute("entry", new ActivityEntry());

        Page<NutritionEntry> nutritionEntries = nutritionService.getAllNutritionEntries(principal.getName(), pageable);
        model.addAttribute("entriesNutrition", nutritionEntries.getContent());
        model.addAttribute("entryNutrition", new NutritionEntry());
        model.addAttribute("dish", new DIshHandBook());
        model.addAttribute("dishes", dishHandBookService.getAllDish());

        int dayDuration = activityService.getDayDuration(principal.getName());
        model.addAttribute("dayHourDuration", dayDuration/60);
        model.addAttribute("dayMinuteDuration", dayDuration%60);
        BloodPressureEntry bpEntry = bloodPressureService.getLastBloodPressure(principal.getName());
        model.addAttribute("lastBloodPressure", Objects.requireNonNullElseGet(bpEntry, BloodPressureEntry::new));

        float todayReceivedCalories = nutritionService.getReceivedCalories(principal.getName());
        model.addAttribute("todayReceivedCalories", todayReceivedCalories);
        float todaySpentCalories = activityService.getDaySpendCalories(principal.getName());
        model.addAttribute("todaySpentCalories", todaySpentCalories);
        return "mainUser";
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
    public String deletePressure(@PathVariable(value = "id") int id,
                                 @RequestHeader String referer) {
        BloodPressureEntry entry = bloodPressureService.findById(id);
        bloodPressureService.delete(entry);
        return "redirect:" + referer;
    }
    @GetMapping("/user/activity")
    public String userActivity(@RequestParam(defaultValue = "0") int page,
                               Principal principal,
                               Model model) {
        if (page < 0)  page = 0;
        Pageable pageable = PageRequest.of(page, 15);

        Page<ActivityEntry> activityEntries = activityService.getAllActivities(principal.getName(), pageable);
        model.addAttribute("entries", activityEntries.getContent());
        model.addAttribute("entry", new ActivityEntry());
        model.addAttribute("activity", new ActivityHandBook());
        model.addAttribute("activities", activityHandBookService.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activityEntries.getTotalPages());
        return "training";
    }
    @GetMapping("/user/activity/delete/{id}")
    public String deleteActivityEntry(@PathVariable(value = "id") int id,
                                      @RequestHeader String referer) {
        ActivityEntry entry = activityService.findById(id);
        activityService.delete(entry);
        return "redirect:" + referer;
    }
    @PostMapping("/user/activity/add")
    public String addActivityEntry(@RequestParam int activity,
                              @RequestParam String duration,
                              Principal principal) {
        ActivityEntry entry = new ActivityEntry();
        entry.setDateOfActivity(new Date());
        ActivityHandBook activityHandBookEntry = activityHandBookService.findById(activity);
        entry.setActivity(activityHandBookEntry);
        User user = userService.getUserByUserName(principal.getName());
        entry.setDiary(user.getHealthDiary());
        int dur = Integer.parseInt(duration.split("\\.")[0]);
        entry.setDurationInMinutes(dur);
        entry.setCalories(dur * activityHandBookEntry.getCaloriesPerMinute());
        activityService.add(entry);

        return "redirect:/user/activity";
    }
    @GetMapping("/user/nutrition")
    public String userNutrition(@RequestParam(defaultValue = "0") int page,
                               Principal principal,
                               Model model) {
        if (page < 0)  page = 0;
        Pageable pageable = PageRequest.of(page, 15);

        Page<NutritionEntry> nutritionEntries = nutritionService.getAllNutritionEntries(principal.getName(), pageable);
        model.addAttribute("entries", nutritionEntries.getContent());
        model.addAttribute("entry", new NutritionEntry());
        model.addAttribute("dish", new DIshHandBook());
        model.addAttribute("dishes", dishHandBookService.getAllDish());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", nutritionEntries.getTotalPages());
        return "nutrition";
    }
    @GetMapping("/user/nutrition/delete/{id}")
    public String deleteNutritionEntry(@PathVariable(value = "id") int id,
                                      @RequestHeader String referer) {
        NutritionEntry entry = nutritionService.findById(id);
        nutritionService.delete(entry);
        return "redirect:" + referer;
    }
    @PostMapping("/user/nutrition/add")
    public String addNutritionEntry(@RequestParam int dish,
                                   @RequestParam String weight,
                                   Principal principal) {
        NutritionEntry entry = new NutritionEntry();
        entry.setTimeOfNutrition(LocalDateTime.now());
        float weightDish = Integer.parseInt(weight.split("\\.")[0]);
        entry.setDishWeight(weightDish);
        DIshHandBook dIshHandBookEntry = dishHandBookService.findById(dish);
        entry.setDish(dIshHandBookEntry);
        entry.setCalories((float) (Math.round((weightDish / 100) * dIshHandBookEntry.getEnergyValue() * 100.0) / 100.0));
        entry.setProteins((float) (Math.round((weightDish / 100) * dIshHandBookEntry.getProteins() * 100.0) / 100.0));
        entry.setFats((float) (Math.round((weightDish / 100) * dIshHandBookEntry.getFats() * 100.0) / 100.0));
        entry.setCarbohydrates((float) (Math.round((weightDish / 100) * dIshHandBookEntry.getCarbohydrates() * 100.0) / 100.0));
        User user = userService.getUserByUserName(principal.getName());
        entry.setDiary(user.getHealthDiary());
        nutritionService.add(entry);
        return "redirect:/user/nutrition";
    }

}
