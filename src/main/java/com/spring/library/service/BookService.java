package com.spring.library.service;

import com.spring.library.models.Book;
import com.spring.library.models.Person;
import com.spring.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book show(int id) {
       return bookRepository.getById(id);
    }

    public Person hasOwner(int id) {
        return bookRepository.getById(id).getOwner();
    }


    public void save(Book book) {
        bookRepository.save(book);
    }


    public void update(Book book) {
        bookRepository.save(book);
    }


    public void delete(int id) {
        bookRepository.delete(bookRepository.getById(id));
    }


    public void assign(int id, Person person) {
        Book book = bookRepository.getById(id);
        book.setOwner(person);
        bookRepository.save(book);
    }


    public void cancel(int id) {
        Book book = bookRepository.getById(id);
        book.setOwner(null);
        bookRepository.save(book);
    }
}
