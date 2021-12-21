package ua.kogutenko.springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.UsesJava8;
import org.springframework.stereotype.Component;
import ua.kogutenko.springmvc.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PersonDAO {
    private static int PEOPLE_ID ;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        PEOPLE_ID = 0;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(
                "SELECT * FROM \"public\".person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM \"public\".person WHERE id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                            .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO \"public\".person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE \"public\".person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM \"public\".person WHERE id=?", id);
    }

    public boolean isEmpty() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM \"public\".person", Integer.class).intValue() == 0;
    }
}
