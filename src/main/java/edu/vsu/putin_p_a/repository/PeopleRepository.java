package edu.vsu.putin_p_a.repository;

import edu.vsu.putin_p_a.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> getPeopleByIdIsNotAndFullName(int id, String fullName);
}
