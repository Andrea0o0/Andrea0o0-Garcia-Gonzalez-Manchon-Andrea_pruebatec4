package com.example.Models4.service.person;

import com.example.Models4.model.Person;

import java.util.List;

public interface IPersonService {
    public List<Person> getPersons();

    public void savePerson(Person person);

    public void deletePerson(Long id);

    public Person findPerson(Long id);
}
