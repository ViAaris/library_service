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
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE ID=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO PERSON (FULL_NAME, YEAR_OF_BIRTH) VALUES (?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(Person person, int id) {
        jdbcTemplate.update("UPDATE PERSON SET FULL_NAME=?, YEAR_OF_BIRTH=? WHERE ID=?", person.getFullName(),
                person.getYearOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM PERSON WHERE ID=?", id);
    }

    public List<Book> books(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK INNER JOIN PERSON ON PERSON.ID = BOOK.PERSON_ID WHERE PERSON.ID = ?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE FULL_NAME=?", new Object[]{fullName},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
}
