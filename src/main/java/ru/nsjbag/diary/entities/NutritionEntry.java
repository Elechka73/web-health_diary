package ru.nsjbag.diary.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "entrynutr")
public class NutritionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float dishWeight;
    private float calories;
    private float fats;
    private float proteins;
    private float carbohydrates;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timeOfNutrition;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private DIshHandBook dish;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private HealthDiary diary;
}
