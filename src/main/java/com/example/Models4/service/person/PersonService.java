package com.example.Models4.service.person;

import com.example.Models4.model.Person;
import com.example.Models4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepo;

    @Override
    public List<Person> getPersons() {
        return personRepo.findAll();
    }

    @Override
    public void savePerson(Person person) {
        personRepo.save(person);
    }

    @Override
    public void deletePerson(Long id) {
        personRepo.deleteById(id);
    }

    @Override
    public Person findPerson(Long id) {
        return personRepo.findById(id).orElse(null);
    }
}
