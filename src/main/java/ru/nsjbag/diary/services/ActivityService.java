package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.ActivityEntry;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.repositories.ActivityRepository;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Page<ActivityEntry> getAllActivities(String username, Pageable pageable) {
        return activityRepository.getActivityEntriesByDiary_User_Username_OrderByDateOfActivityDesc(username, pageable);
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
}
