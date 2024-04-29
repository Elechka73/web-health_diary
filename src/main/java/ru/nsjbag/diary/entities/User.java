package ru.nsjbag.diary.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;

    private String password;

    private String firstname;
    private String lastname;
    private String patronymic;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String insuranceNumber;

    private Boolean enabled;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Authority authority;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private HealthDiary healthDiary;
}
