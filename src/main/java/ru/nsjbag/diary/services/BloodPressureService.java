package ru.nsjbag.diary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsjbag.diary.entities.BloodPressureEntry;
import ru.nsjbag.diary.repositories.BloodPressureRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    public List<BloodPressureEntry> getBloodPressure(String username) {
        return repository.findByDiary_User_Username_OrderByDateOfEntryDesc(username);
    }
    public List<BloodPressureEntry> getBloodPressure(String username, String date) {
        List<BloodPressureEntry> temp = repository.findByDiary_User_Username_OrderByDateOfEntryDesc(username);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<BloodPressureEntry> result = new ArrayList<>();
        Date dateE;
        try {
            dateE = formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        for (BloodPressureEntry entry : temp) {
            Date d;
            try {
                d = formatter.parse(String.valueOf(entry.getDateOfEntry()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(d.equals(dateE)) {
                result.add(entry);
            }
        }
        return result;
    }





}
