package edu.vsu.putin_p_a.controllers;

import edu.vsu.putin_p_a.dao.BookDAO;
import edu.vsu.putin_p_a.models.Book;
import edu.vsu.putin_p_a.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    public BooksController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String showBooks(Model model) {
        List<Book> books = bookDAO.getBooks();
        model.addAttribute("books", books);
        return "books/books";
    }

    @GetMapping("/{id}")
    public String showBookById(@PathVariable int id, Model model) {
        Optional<Book> optBook = bookDAO.getBookById(id);
        if (optBook.isPresent()) {
            model.addAttribute("book", optBook.get());
            return "books/bookById";
        }
        return "redirect:/books/{id}/notFound";
    }

    @GetMapping("/{id}/notFound")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String showNotFound(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        return "books/notFound";
    }

    @GetMapping("/new")
    public String showBookCreationForm(@ModelAttribute("book") Book book) {
        return "books/bookCreationForm";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }
}
