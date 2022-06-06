package com.spring.library.service;

import com.spring.library.models.Book;
import com.spring.library.models.Person;
import com.spring.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
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


    public List<Book> getAll(String ... properties) {
        String sort = properties[2];

        if(properties[0]!=null && properties[1] != null){
            int page = Integer.parseInt(properties[0]);
            int booksPerPage = Integer.parseInt(properties[1]);

            if(sort != null){
               return bookRepository.findAll(PageRequest.of(page, booksPerPage,
                        Sort.by(sort))).getContent();
            }else{
                return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
            }
        }else if(sort!= null){
            return bookRepository.findAll(Sort.by(sort));
        } else return bookRepository.findAll();

    }

    public Book show(int id) {
       return bookRepository.findById(id).orElse(null);
    }

    public Optional<Person> hasOwner(int id) {
        return Optional.ofNullable(bookRepository.findById(id).get().getOwner());
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
        Book book = bookRepository.findById(id).get();
        book.setOwner(person);
        bookRepository.save(book);
    }


    public void cancel(int id) {
        Book book = bookRepository.getById(id);
        book.setOwner(null);
        bookRepository.save(book);
    }


    public List<Book> search(String line) {
       return bookRepository.findBooksByTitleStartingWith(line);
    }
}
