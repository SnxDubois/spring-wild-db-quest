package com.wildcodeschool.myProjectWithDB.controllers;

import com.wildcodeschool.myProjectWithDB.repositories.SchoolRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wildcodeschool.myProjectWithDB.entities.Wizard;
import com.wildcodeschool.myProjectWithDB.repositories.WizardRepository;

import java.sql.Date;
import java.util.List;

@Controller
@ResponseBody
public class WizardController {

    @GetMapping("/api/wizards")
    public List<Wizard> getWizards(@RequestParam(defaultValue = "%") String family) {
        return WizardRepository.selectByLastname(family);
    }

    @PostMapping("/api/wizards")
    @ResponseStatus(HttpStatus.CREATED)
    public Wizard store(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam Date birthday,
            @RequestParam(required = false, name = "birth_place") String birthPlace,
            @RequestParam(required = false) String biography,
            @RequestParam(name = "is_muggle") Boolean isMuggle
    ) {
        int idGeneratedByInsertion = WizardRepository.insert(
                firstname,
                lastname,
                birthday,
                birthPlace,
                biography,
                isMuggle
        );
        return WizardRepository.selectById(idGeneratedByInsertion);
    }

    @PutMapping("/api/wizards/{id}")
    public Wizard update(
            @PathVariable int id,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) Date birthday,
            @RequestParam(required = false, name = "birth_place") String birthPlace,
            @RequestParam(required = false) String biography,
            @RequestParam(required = false, name = "is_muggle") Boolean isMuggle
    ) {
        Wizard wizard = WizardRepository.selectById(id);
        WizardRepository.update(
                id,
                firstname != null ? firstname : wizard.getFirstname(),
                lastname != null ? lastname : wizard.getLastname(),
                birthday != null ? birthday : wizard.getBirthday(),
                birthPlace != null ? birthPlace : wizard.getBirthPlace(),
                biography != null ? biography : wizard.getBiography(),
                isMuggle != null ? isMuggle : wizard.isMuggle()
        );
        return WizardRepository.selectById(id);
    }

    @DeleteMapping("/api/wizards/{id}")
    public void delete(@PathVariable int id) {
        WizardRepository.delete(id);
    }
}
