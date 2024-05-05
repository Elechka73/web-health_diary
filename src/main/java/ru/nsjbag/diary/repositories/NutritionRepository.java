package ru.nsjbag.diary.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsjbag.diary.entities.NutritionEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface NutritionRepository extends JpaRepository<NutritionEntry, Integer> {
    Page<NutritionEntry> getNutritionEntriesByDiary_User_Username_OrderByTimeOfNutritionDesc(String username, Pageable pageable);
    List<NutritionEntry> getNutritionEntriesByDiary_User_Username_OrderByTimeOfNutritionDesc(String username);
}
