package edu.vsu.putin_p_a.repository;

import edu.vsu.putin_p_a.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
