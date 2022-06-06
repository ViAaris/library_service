package com.spring.library.repository;

import com.spring.library.models.Book;
import com.spring.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    public List<Book> findBooksByTitleStartingWith(String line);

}
