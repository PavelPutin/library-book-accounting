package edu.vsu.putin_p_a.controllers;

import edu.vsu.putin_p_a.dao.BookDAO;
import edu.vsu.putin_p_a.dao.PersonDAO;
import edu.vsu.putin_p_a.models.Book;
import edu.vsu.putin_p_a.models.Person;
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
    private final PersonDAO personDAO;

    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
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
            Book book = optBook.get();
            model.addAttribute("book", book);

            if (book.getOwnerId() != null) {
                Optional<Person> owner = personDAO.getPersonById(book.getOwnerId());
                owner.ifPresent(person -> model.addAttribute("owner", person));
            }

            return "books/bookById";
        }
        return "redirect:/books/{id}/notFound";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        bookDAO.free(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/{id}/notFound")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String showNotFound(@PathVariable("id") int id, Model model) {
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
            return "books/bookCreationForm";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Optional<Book> optEditingBook = bookDAO.getBookById(id);
        if (optEditingBook.isPresent()) {
            model.addAttribute("editingBook", optEditingBook.get());
            return "books/bookEdit";
        }
        return "redirect:/books/{id}/notFound";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("editingBook") @Valid Book editingBook,
                           BindingResult bindingResult) {
        bookValidator.validate(editingBook, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/bookEdit";
        }
        bookDAO.edit(id, editingBook);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.remove(id);
        return "redirect:/books";
    }
}
