package com.nottpty.springmongodb.repository;

import com.nottpty.springmongodb.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}
