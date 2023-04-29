package com.nottpty.springmongodb.service;

import com.nottpty.springmongodb.model.Person;
import com.nottpty.springmongodb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        List<Person> persons = Objects.requireNonNull(redisTemplate.opsForList().range("users", 0, -1))
                .stream()
                .map(o -> (Person) o)
                .collect(Collectors.toList());
        if (persons.isEmpty()) {
            persons = personRepository.findAll();
            redisTemplate.opsForValue().set("persons", persons, 1, TimeUnit.MINUTES);
        }
        return persons;
    }

    public Person findPersonById(String id) {
        String key = "person_" + id;
        Person person = (Person) redisTemplate.opsForValue().get(key);
        if (person == null) {
            Optional<Person> optionalPerson = personRepository.findById(id);
            if (optionalPerson.isPresent()) {
                person = optionalPerson.get();
                redisTemplate.opsForValue().set(key, person, 1, TimeUnit.MINUTES);
            }
        }
        return person;
    }

    public void deletePersonById(String id) {
        personRepository.deleteById(id);
    }
}