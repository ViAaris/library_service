package com.spring.library.util;


import com.spring.library.models.Person;
import com.spring.library.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonRepository personRepository;

    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if(personRepository.findPersonByFullName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "Person with this name already exists");
        }
    }
}
