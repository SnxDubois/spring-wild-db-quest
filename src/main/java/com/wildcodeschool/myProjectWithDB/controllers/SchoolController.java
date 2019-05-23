package com.wildcodeschool.myProjectWithDB.controllers;

import com.wildcodeschool.myProjectWithDB.entities.School;
import com.wildcodeschool.myProjectWithDB.repositories.SchoolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
public class SchoolController {

    @PostMapping("/api/schools")
    @ResponseStatus(HttpStatus.CREATED)
    public School store(
            @RequestParam String name,
            @RequestParam int capacity,
            @RequestParam String country
    ) {
        int idGeneratedByInsertion = SchoolRepository.insert(
                name,
                capacity,
                country
        );
        return SchoolRepository.selectById(idGeneratedByInsertion);
    }

    @PutMapping("/api/schools/{id}")
    public School update(
            @PathVariable int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) String country
    ) {
        School school = SchoolRepository.selectById(id);
        SchoolRepository.update(
                id,
                name != null ? name : school.getName(),
                capacity != null ? capacity : school.getCapacity(),
                country != null ? country : school.getCountry()
        );
        return SchoolRepository.selectById(id);
    }

    @DeleteMapping("/api/schools/{id}")
    public void delete(@PathVariable int id) {
        SchoolRepository.delete(id);
    }
}
