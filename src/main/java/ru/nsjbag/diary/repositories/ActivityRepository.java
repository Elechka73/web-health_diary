package ru.nsjbag.diary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsjbag.diary.entities.ActivityEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntry, Integer> {
    Page<ActivityEntry> getActivityEntriesByDiary_User_Username_OrderByDateOfActivityDesc(String username, Pageable pageable);

    List<ActivityEntry> getTodayActivityEntriesByDiary_User_UsernameAndDateOfActivity(String username, Date dateOfActivity);
    List<ActivityEntry> getActivityEntriesByDiary_User_Username_OrderByDateOfActivityDesc(String username);
}
