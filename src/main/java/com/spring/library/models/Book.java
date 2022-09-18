package com.spring.library.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Book title cannot be empty")
    @Size(min = 2, max = 100, message = "Book title should be from 2 till 100 symbols")
    private String title;

    @NotEmpty(message = "Author name cannot be empty")
    @Size(min = 2, max = 100, message = "Author name  should be from 2 till 100 symbols")
    private String author;

    @Min(value = 1500, message = "Year must be more than 1500")
    private int year;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name="receiving_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivingDate;

    @Transient
    private boolean isLate;

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Book() {

    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
