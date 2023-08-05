package edu.vsu.putin_p_a.service;

import edu.vsu.putin_p_a.models.Book;
import edu.vsu.putin_p_a.repository.BooksRepository;
import edu.vsu.putin_p_a.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> getBooks(Integer page, Integer booksPerPage) {
        if (page == null || booksPerPage == null) {
            return booksRepository.findAll();
        }
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
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
    public void setOwner(int id, int ownerId) {
        booksRepository.findById(id)
                .ifPresent(book -> book.setOwner(peopleRepository.getReferenceById(ownerId)));
    }
}
