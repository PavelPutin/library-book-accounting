package edu.vsu.putin_p_a.controllers;

import edu.vsu.putin_p_a.dao.PersonDAO;
import edu.vsu.putin_p_a.models.Person;
import edu.vsu.putin_p_a.util.BookValidator;
import edu.vsu.putin_p_a.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator personValidator;
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showPeople(Model model) {
        List<Person> people = personDAO.getPeople();
        model.addAttribute("people", people);
        return "people/people";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") int id, Model model) {
        Optional<Person> optPerson = personDAO.getPersonById(id);
        if (optPerson.isPresent()) {
            model.addAttribute("person", optPerson.get());
        } else {
            model.addAttribute("notFound", "Person with id " + id + " not found.");
        }
        return "people/personById";
    }

    @GetMapping("/new")
    public String showPersonCreationForm(@ModelAttribute("person") Person person) {
        return "people/personCreationForm";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/personCreationForm";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showPersonEditingForm(@PathVariable("id") int id, Model model) {
        Optional<Person> optPerson = personDAO.getPersonById(id);
        if (optPerson.isPresent()) {
            model.addAttribute("person", optPerson.get());
        } else {
            model.addAttribute("personNotFound", "Person with id " + id + "not found");
        }
        return "people/personEdit";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id,
                             @ModelAttribute("person") @Valid Person updatedPerson,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/personEdit";
        }
        personDAO.edit(id, updatedPerson);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.remove(id);
        return "redirect:/people";
    }
}
