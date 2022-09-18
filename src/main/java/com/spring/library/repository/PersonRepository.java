package com.spring.library.repository;


import com.spring.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {


    Optional<Person> findPersonByFullName(String name);
}
