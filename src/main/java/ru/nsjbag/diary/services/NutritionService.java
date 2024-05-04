package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.NutritionEntry;
import ru.nsjbag.diary.repositories.NutritionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class NutritionService {

    @Autowired
    private NutritionRepository nutritionRepository;

    public Page<NutritionEntry> getAllNutritionEntries(String username, Pageable pageable) {
        return nutritionRepository.getNutritionEntriesByDiary_User_Username_OrderByTimeOfNutritionDesc(username, pageable);
    }
    public void add(NutritionEntry entry) {
        nutritionRepository.save(entry);
    }
    public NutritionEntry findById(int id) {
        return nutritionRepository.findById(id).orElse(null);
    }
    public void delete(NutritionEntry entry) {
        nutritionRepository.delete(entry);
    }
    public float getReceivedCalories(String username) {
        List<NutritionEntry> nutritionEntries = nutritionRepository.getNutritionEntriesByDiary_User_Username(username);
        float calories = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (NutritionEntry nutritionEntry : nutritionEntries) {
            String formattedDate = nutritionEntry.getTimeOfNutrition().format(formatter);
            String today = LocalDate.now().format(formatter);
            if (formattedDate.equals(today)) calories += nutritionEntry.getCalories();
        }
        return calories;
    }

}
