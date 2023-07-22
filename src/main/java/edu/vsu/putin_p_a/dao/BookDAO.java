package edu.vsu.putin_p_a.dao;

import edu.vsu.putin_p_a.models.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

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
}
