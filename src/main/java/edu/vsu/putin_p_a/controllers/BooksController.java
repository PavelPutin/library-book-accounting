package edu.vsu.putin_p_a.controllers;

import edu.vsu.putin_p_a.models.Book;
import edu.vsu.putin_p_a.service.BooksService;
import edu.vsu.putin_p_a.service.PeopleService;
import edu.vsu.putin_p_a.util.BookValidator;
import edu.vsu.putin_p_a.util.PaginationState;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookValidator bookValidator;
    private final BooksService booksService;
    private final PeopleService peopleService;

    public BooksController(BookValidator bookValidator, BooksService booksService, PeopleService peopleService) {
        this.bookValidator = bookValidator;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String showBooks(Model model,
                            @RequestParam(value = "page", required = false) final Optional<Integer> page,
                            @RequestParam(value = "books_per_page", required = false) final Optional<Integer> booksPerPage,
                            @RequestParam(value = "sort_by_year", required = false) final Optional<Boolean> sortByYear) {
        List<Book> books = booksService.getBooks(page, booksPerPage, sortByYear);
        model.addAttribute("books", books);

        PaginationState paginationState = booksService.getPaginationState();
        model.addAttribute("currentPage", paginationState.getCurrent());
        model.addAttribute("pagesAmount", paginationState.getPagesAmount());
        model.addAttribute("booksPerPage",paginationState.getPageSize());

        Map<String, String> paginationPaths = new HashMap<>();
        final int nextPage = Math.min(paginationState.getCurrent() + 1, paginationState.getPagesAmount() - 1);
        paginationPaths.put("nextPagePath", String.valueOf(nextPage));

        final int previousPage = Math.max(paginationState.getCurrent() - 1, 0);
        paginationPaths.put("previousPagePath", String.valueOf(previousPage));

        paginationPaths.put("enablePagingPath", String.valueOf(paginationState.getCurrent()));

        final String base = BooksController.class.getAnnotation(RequestMapping.class).value()[0];
        paginationPaths.keySet().forEach(key -> {
            String path = paginationPaths.get(key);
            path = base + "?page=" + path + "&books_per_page=" + paginationState.getPageSize();
            if (sortByYear.isPresent() && sortByYear.get()) {
                path += "&sort_by_year=true";
            }
            paginationPaths.put(key, path);
        });

        model.addAllAttributes(paginationPaths);

        String disablePagingPath = base + (sortByYear.isPresent() && sortByYear.get() ? "?sort_by_year=true" : "");
        model.addAttribute("disablePagingPath", disablePagingPath);

        return "books/books";
    }

    @GetMapping("/{id}")
    public String showBookById(@PathVariable int id, Model model) {
        Optional<Book> optBook = booksService.getBookById(id);
        if (optBook.isPresent()) {
            Book book = optBook.get();
            model.addAttribute("book", book);

            book.getOwner().ifPresentOrElse(
                    person -> model.addAttribute("owner", person),
                    () -> model.addAttribute("people", peopleService.getPeople())
            );

            return "books/bookById";
        }
        return "redirect:/books/{id}/notFound";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        booksService.free(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/setOwner")
    public String setOwner(@PathVariable("id") int id, @RequestParam("ownerId") int ownerId) {
        booksService.setOwner(id, ownerId);
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
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Optional<Book> optEditingBook = booksService.getBookById(id);
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
        booksService.edit(id, editingBook);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.remove(id);
        return "redirect:/books";
    }
}
