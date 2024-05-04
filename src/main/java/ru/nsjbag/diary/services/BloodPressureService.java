package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.repositories.BloodPressureRepository;

import java.time.LocalDateTime;


@Service
public class BloodPressureService {

    @Autowired
    private BloodPressureRepository repository;

    public Page<BloodPressureEntry> getBloodPressure(String username, Pageable pageable) {
        return repository.findByDiary_User_Username_OrderByDateOfEntryDesc(username, pageable);
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
    public BloodPressureEntry getLastBloodPressure(String username) {
        return repository.getFirstBloodPressureEntryByDiary_User_UsernameOrderByDateOfEntryDesc(username);
    }


}
