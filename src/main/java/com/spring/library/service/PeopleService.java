package com.spring.library.service;

import com.spring.library.models.Book;
import com.spring.library.models.Person;
import com.spring.library.repository.BookRepository;
import com.spring.library.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PeopleService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }

    @Transactional
    public Person show(int id){
        return personRepository.getById(id);
    }

    public List<Book> books(int id){
        return bookRepository.findBooksByOwnerId(id);
    }


    public void save(Person person) {
        personRepository.save(person);
    }


    public void delete(int id) {
        personRepository.delete(personRepository.getById(id));
    }

    public void update(Person person) {
        personRepository.save(person);
    }
}
