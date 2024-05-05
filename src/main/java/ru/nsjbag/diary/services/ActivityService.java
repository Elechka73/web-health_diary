package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.ActivityEntry;
import ru.nsjbag.diary.repositories.ActivityRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Page<ActivityEntry> getAllActivities(String username, Pageable pageable) {
        return activityRepository.getActivityEntriesByDiary_User_Username_OrderByDateOfActivityDesc(username, pageable);
    }
    public List<ActivityEntry> getAllActivities(String username) {
        return activityRepository.getActivityEntriesByDiary_User_Username_OrderByDateOfActivityDesc(username);
    }
    public List<ActivityEntry> getAllActivities(String username, String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateE;
        try {
            dateE = formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return activityRepository.getTodayActivityEntriesByDiary_User_UsernameAndDateOfActivity(username,dateE);
    }

    public void add(ActivityEntry entry) {
        activityRepository.save(entry);
    }
    public ActivityEntry findById(int id) {
        return activityRepository.findById(id).orElse(null);
    }
    public void delete(ActivityEntry entry) {
        activityRepository.delete(entry);
    }

    public int getDayDuration(String username) {
        Date today = new Date();
        List<ActivityEntry> entries = activityRepository.getTodayActivityEntriesByDiary_User_UsernameAndDateOfActivity(username, today);
        int duration = 0;
        for (ActivityEntry entry : entries) {
            duration += entry.getDurationInMinutes();
        }
        return duration;
    }
    public float getDaySpendCalories(String username) {
        Date today = new Date();
        List<ActivityEntry> entries = activityRepository.getTodayActivityEntriesByDiary_User_UsernameAndDateOfActivity(username, today);
        float calories = 0;
        for (ActivityEntry entry : entries) {
            calories += entry.getCalories();
        }
        return calories;
    }
}
