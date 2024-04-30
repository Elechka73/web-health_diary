package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.ActivityHandBook;
import ru.nsjbag.diary.repositories.ActivityHandBookRepository;

import java.util.List;

@Service
public class ActivityHandBookService {
    @Autowired
    private ActivityHandBookRepository activityHandBookRepository;

    public List<ActivityHandBook> findAll() {
        return activityHandBookRepository.findAll();
    }
    public ActivityHandBook findById(int id) {
        return activityHandBookRepository.findById(id).orElse(null);
    }
}
