package com.spring.library.controllers;

import com.spring.library.models.Book;
import com.spring.library.models.Person;
import com.spring.library.service.PeopleService;
import com.spring.library.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String allPeople(Model model){
        model.addAttribute("people", peopleService.getAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        model.addAttribute("person", peopleService.show(id));
        List<Book> books = peopleService.books(id);

        if(books.size()!=0){
            model.addAttribute("books", books);
        }

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson (@ModelAttribute("person") Person person  ) {
        return "people/new";
    }
    @PostMapping()
    public String addNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())return "people/new";
       peopleService.save(person);
        return"redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update( @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())return "people/edit";
        peopleService.update(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
       peopleService.delete(id);
        return "redirect:/people";
    }
}
