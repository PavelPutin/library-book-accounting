package edu.vsu.putin_p_a.dao;

import edu.vsu.putin_p_a.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private static int PERSON_COUNT;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getPersonById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny();
    }

    public Person save(Person person) {
        person.setId(PERSON_COUNT++);

        jdbcTemplate.update("INSERT INTO person VALUES(?, ?, ?, ?)",
                person.getId(), person.getName(),
                person.getAge(), person.getEmail());

        return person;
    }

    public Optional<Person> update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getId());
        return getPersonById(id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
