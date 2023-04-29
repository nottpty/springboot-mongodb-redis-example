package com.nottpty.springmongodb.controller;

import com.nottpty.springmongodb.model.Person;
import com.nottpty.springmongodb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping
    public Person savePerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPerson() {
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        Person person = personService.findPersonById(id);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonById(@PathVariable String id) {
        personService.deletePersonById(id);
        return new ResponseEntity<>("Person deleted successful", HttpStatus.OK);
    }

}
