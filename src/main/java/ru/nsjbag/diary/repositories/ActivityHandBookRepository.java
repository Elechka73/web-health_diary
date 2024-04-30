package ru.nsjbag.diary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsjbag.diary.entities.ActivityHandBook;

@Repository
public interface ActivityHandBookRepository extends JpaRepository<ActivityHandBook, Integer> {

}
