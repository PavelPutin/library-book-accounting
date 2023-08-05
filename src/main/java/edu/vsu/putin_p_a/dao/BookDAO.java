package edu.vsu.putin_p_a.dao;

import edu.vsu.putin_p_a.models.Book;
import edu.vsu.putin_p_a.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> getBookById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new BeanPropertyRowMapper<>(Book.class), id)
                .stream().findAny();
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.owner_id=person.id WHERE book.id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name, author, publish_year) VALUES (?, ?, ?);",
                book.getName(), book.getAuthor(), book.getPublishYear());
    }

    public void edit(int id, Book editingBook) {
        jdbcTemplate.update("UPDATE book SET name=?, author=?, publish_year=? WHERE id=?",
                editingBook.getName(),
                editingBook.getAuthor(),
                editingBook.getPublishYear(),
                id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public List<Book> getBooksByOwnerId(int ownerId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE owner_id=?", new BeanPropertyRowMapper<>(Book.class), ownerId);
    }

    public void free(int id) {
        jdbcTemplate.update("UPDATE book SET owner_id=NULL WHERE id=?", id);
    }

    public void setOwner(int id, int ownerId) {
        jdbcTemplate.update("UPDATE book SET owner_id=? WHERE id=?", ownerId, id);
    }
}
