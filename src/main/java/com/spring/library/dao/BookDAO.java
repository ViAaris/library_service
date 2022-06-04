package com.spring.library.dao;

import com.spring.library.models.Book;
import com.spring.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK WHERE ID=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
        .stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO BOOK (TITLE, AUTHOR, YEAR) VALUES (?, ?, ?)", book.getTitle(),
                book.getAuthor(), book.getYear());
    }

    public void update(Book book, int id) {
        jdbcTemplate.update("UPDATE BOOK SET TITLE=?, AUTHOR=?, YEAR=? WHERE ID=?", book.getTitle(),
                book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM BOOK WHERE ID=?", id);
    }

    public Integer hasOwner(int id){
        return jdbcTemplate.queryForObject("SELECT PERSON_ID FROM BOOK WHERE ID=?",
                new Object[]{id}, Integer.class);
    }

    public void assign(int id, Person person){
        jdbcTemplate.update("UPDATE BOOK SET PERSON_ID=? WHERE ID=?",
                person.getId(), id);
    }

    public void cancel(int id) {
        jdbcTemplate.update("UPDATE BOOK SET PERSON_ID = NULL WHERE ID=?", id);
    }
}
