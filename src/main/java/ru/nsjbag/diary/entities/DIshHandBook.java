package ru.nsjbag.diary.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dishes")
public class DIshHandBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dishName;

    private float proteins;

    private float fats;

    private float carbohydrates;

    private float energyValue;

}
