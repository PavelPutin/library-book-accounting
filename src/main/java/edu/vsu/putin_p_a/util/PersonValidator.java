package edu.vsu.putin_p_a.util;

import edu.vsu.putin_p_a.dao.PersonDAO;
import edu.vsu.putin_p_a.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDao;

    @Autowired
    public PersonValidator(PersonDAO personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personDao.getPersonById(person.getId()).isEmpty() &&
                personDao.getPersonByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Full name isn't unique");
        }

        if (person.getBirthdayYear() > Year.now().getValue()) {
            errors.rejectValue("birthdayYear", "", "Birthday year must be earlier than the current one");
        }
    }
}
