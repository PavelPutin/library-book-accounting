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

    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?",new BeanPropertyRowMapper<>(Person.class), fullName)
                .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, birthday_year) VALUES(?, ?)",
                person.getFullName(), person.getBirthdayYear());
    }

    public Optional<Person> edit(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, birthday_year=? WHERE id=?", updatedPerson.getFullName(), updatedPerson.getBirthdayYear(), updatedPerson.getId());
        return getPersonById(id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
