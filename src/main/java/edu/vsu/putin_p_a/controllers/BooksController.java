package edu.vsu.putin_p_a.controllers;

import edu.vsu.putin_p_a.dao.BookDAO;
import edu.vsu.putin_p_a.models.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;

    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
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
}
