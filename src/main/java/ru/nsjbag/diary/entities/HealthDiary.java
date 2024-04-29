package ru.nsjbag.diary.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "diaries")
public class HealthDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @OneToMany(mappedBy = "diary")
    private List<ActivityEntry> activityEntries = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<NutritionEntry> nutritionEntries = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<BloodPressureEntry> bloodPressureEntries = new ArrayList<>();


}
