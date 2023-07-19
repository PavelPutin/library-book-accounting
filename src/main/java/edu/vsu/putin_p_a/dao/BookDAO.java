package edu.vsu.putin_p_a.dao;

import edu.vsu.putin_p_a.models.Book;
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
}
