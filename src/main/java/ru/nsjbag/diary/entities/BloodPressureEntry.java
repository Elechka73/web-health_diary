package ru.nsjbag.diary.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pressure")
public class BloodPressureEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int systolic;
    private int diastolic;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateOfEntry;

}
