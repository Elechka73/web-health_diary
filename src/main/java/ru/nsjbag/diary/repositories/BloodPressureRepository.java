package ru.nsjbag.diary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import java.util.List;


@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressureEntry, Integer> {
    Page<BloodPressureEntry> findByDiary_User_Username_OrderByDateOfEntryDesc(String username, Pageable pageable);
    BloodPressureEntry getFirstBloodPressureEntryByDiary_User_UsernameOrderByDateOfEntryDesc( String username);
    List<BloodPressureEntry> findByDiary_User_Username_OrderByDateOfEntryDesc(String username);
}
