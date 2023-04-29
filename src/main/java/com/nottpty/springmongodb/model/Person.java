package com.nottpty.springmongodb.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Person {
    @Id
    private String id;
    private String firstName;
    private String lastName;
}