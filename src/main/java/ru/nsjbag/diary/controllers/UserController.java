package ru.nsjbag.diary.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsjbag.diary.entities.ActivityEntry;
import ru.nsjbag.diary.entities.ActivityHandBook;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.entities.User;
import ru.nsjbag.diary.services.ActivityHandBookService;
import ru.nsjbag.diary.services.ActivityService;
import ru.nsjbag.diary.services.BloodPressureService;
import ru.nsjbag.diary.services.UserService;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;


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

}
