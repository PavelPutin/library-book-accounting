package edu.vsu.putin_p_a.repository;

import edu.vsu.putin_p_a.models.Book;
import edu.vsu.putin_p_a.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> getBooksByOwner(Person owner);
}
