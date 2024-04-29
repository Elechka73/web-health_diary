package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.repositories.BloodPressureRepository;

import java.util.List;

@Service
public class BloodPressureService {

    @Autowired
    private BloodPressureRepository repository;

    public List<BloodPressureEntry> getBloodPressureByUsername(String username) {
        return repository.getBloodPressureEntriesByDiary_User_Username(username);
    }

    public void add(BloodPressureEntry entry) {
        repository.save(entry);
    }
    public BloodPressureEntry findById(int id) {
        return repository.findById(id).orElse(null);
    }
    public void delete(BloodPressureEntry entry) {
        repository.delete(entry);
    }


}
