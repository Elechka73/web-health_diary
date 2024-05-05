package ru.nsjbag.diary.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.nsjbag.diary.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserSpecification {

    public static Specification<User> hasLastName(String lastname) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (lastname == null || lastname.isBlank()) {
                return criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), "%" + lastname.toLowerCase() + "%"));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            return predicate;
        });
    }

    public static Specification<User> hasFirstName(String firstname) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (firstname == null || firstname.isBlank()) {
                return criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")), "%" + firstname.toLowerCase() + "%"));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            return predicate;
        });
    }

    public static Specification<User> hasDateOfBirth(String dob) {

        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (dob == null || dob.isBlank()) {
                return criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = formatter.parse(dob);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateOfBirth"), date));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            return predicate;
        });
    }

    public static Specification<User> hasInsuranceNumber(String insurance) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (insurance == null || insurance.isBlank()) {
                return criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("insuranceNumber"), "%" + insurance + "%"));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("authority").get("authority"), "ROLE_USER"));
            return predicate;
        });
    }


}
