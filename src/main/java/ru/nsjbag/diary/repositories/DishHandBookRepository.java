package ru.nsjbag.diary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsjbag.diary.entities.DIshHandBook;

@Repository
public interface DishHandBookRepository extends JpaRepository<DIshHandBook, Integer> {
}
