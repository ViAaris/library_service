package com.spring.library.service;

import com.spring.library.models.Book;
import com.spring.library.models.Person;
import com.spring.library.repository.BookRepository;
import com.spring.library.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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


    public Person show(int id){
        return personRepository.findById(id).orElse(null);
    }

    public List<Book> books(int id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
        List<Book> books = person.get().getBooks();

            Hibernate.initialize(books);
            for(Book book : books){
                if(book.getReceivingDate()!=null) {
                    if (isTheBookLate(new Date(), book.getReceivingDate())) {
                        book.setLate(true);
                    }
                }
            }
            return books;
        }else return Collections.emptyList();
    }

    public boolean isTheBookLate(Date currentDate, Date takenAt){
        Calendar lastDayToReturn = Calendar.getInstance();
        lastDayToReturn.setTime(takenAt);
        lastDayToReturn.add(Calendar.DATE, 10);

        Calendar current = Calendar.getInstance();
        current.setTime(currentDate);

        return current.after(lastDayToReturn);
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
