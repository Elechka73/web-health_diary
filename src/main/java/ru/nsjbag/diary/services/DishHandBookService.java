package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.DIshHandBook;
import ru.nsjbag.diary.repositories.DishHandBookRepository;

import java.util.List;

@Service
public class DishHandBookService {

    @Autowired
    private DishHandBookRepository dishHandBookRepository;

    public List<DIshHandBook> getAllDish() {
        return dishHandBookRepository.findAll();
    }
    public DIshHandBook findById(int id) {
        return dishHandBookRepository.findById(id).orElse(null);
    }
}
