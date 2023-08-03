package edu.vsu.putin_p_a.service;

import edu.vsu.putin_p_a.models.Book;
import edu.vsu.putin_p_a.models.Person;
import edu.vsu.putin_p_a.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    public Optional<Book> getBookById(int id) {
        return booksRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void edit(int id, Book editingBook) {
        editingBook.setId(id);
        booksRepository.save(editingBook);
    }

    @Transactional
    public void remove(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void free(int id) {
        booksRepository.findById(id).ifPresent(book -> book.setOwner(null));
    }

    @Transactional
    public void setOwner(int id, Person owner) {
        booksRepository.findById(id).ifPresent(book -> book.setOwner(owner));
    }
}
