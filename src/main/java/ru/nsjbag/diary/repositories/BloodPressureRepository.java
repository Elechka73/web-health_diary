package ru.nsjbag.diary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.entities.User;

import java.util.List;

@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressureEntry, Integer> {

    List<BloodPressureEntry> getBloodPressureEntriesByDiary_User_Username(String username);
}
