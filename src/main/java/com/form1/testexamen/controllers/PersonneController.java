package com.form1.testexamen.controllers;

import com.form1.testexamen.dao.PaysRepository;
import com.form1.testexamen.dao.PersonneRepository;
import com.form1.testexamen.entities.Pays;
import com.form1.testexamen.entities.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/personnes")
public class PersonneController {
    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PaysRepository paysRepository;

    @RequestMapping("/all")
    public String listePersonne(Model model)
    {
        Personne pers = new Personne();
        pers.setPays(new ArrayList<>());
        model.addAttribute("personne", pers);
        model.addAttribute("personnes", personneRepository.findAll());
        model.addAttribute("allpays", paysRepository.findAll());
        return "personnes";
    }
    @RequestMapping("/add")
    public String addPersonne(@ModelAttribute("personne") Personne personne, long[] pays)
    {
        List<Pays> paysList = new ArrayList<>();
        for (long sp: pays) {
            paysList.add(paysRepository.findById(sp).get());
        }
        personne.setPays(paysList);
        personneRepository.save(personne);
        return "redirect:/personnes/all";
    }
    @GetMapping("/{id}")
    public @ResponseBody
    Personne OnePersonne(@PathVariable(name = "id") Long personne_id){
        return personneRepository.findById(personne_id).get();
    }

    @PostMapping("/remove")
    public String removePersonne(Long personne_id) {
        Personne personne = personneRepository.getOne(personne_id);
        personneRepository.delete(personne);
        return "redirect:/personnes/all";
    }

}
