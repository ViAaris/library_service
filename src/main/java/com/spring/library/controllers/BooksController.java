package com.spring.library.controllers;


import com.spring.library.models.Book;
import com.spring.library.models.Person;
import com.spring.library.service.BookService;
import com.spring.library.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {

        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String allBooks(@RequestParam(required = false, name = "page") String page,
                           @RequestParam(required = false, name = "books_per_page") String booksPerPage,
                           @RequestParam(required = false, name = "sort_by") String sortBy,
                           Model model) {

        model.addAttribute("books", bookService.getAll(page, booksPerPage, sortBy));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        model.addAttribute("book", bookService.show(id));
        Optional<Person> owner = bookService.hasOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("owner", new Person());
            model.addAttribute("people", peopleService.getAll());
        }


        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String addNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/assign/{id}")
    public String assign(@PathVariable("id") int id, @ModelAttribute("owner") Person person) {
        bookService.assign(id, person);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/cancel/{id}")
    public String cancelOwner(@PathVariable("id") int id) {
        bookService.cancel(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String enterSearch() {
        return "books/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = true, name = "line") String line, Model model) {
        List<Book> booksFound = bookService.search(line);
        if (booksFound.size() != 0) {
            model.addAttribute("booksFound", booksFound);
            for (Book book : booksFound) {
                Person owner = booksFound.get(0).getOwner();
                if (owner != null) model.addAttribute("owner", owner);
            }
        }
            return "books/search";
    }
}
