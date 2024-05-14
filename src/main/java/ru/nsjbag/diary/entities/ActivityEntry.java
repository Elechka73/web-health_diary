package ru.nsjbag.diary.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "entryact")
public class ActivityEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateOfActivity;
    private float calories;
    private int durationInMinutes;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityHandBook activity;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private HealthDiary diary;
}
