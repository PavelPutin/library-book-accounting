package edu.vsu.putin_p_a.repository;

import edu.vsu.putin_p_a.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
