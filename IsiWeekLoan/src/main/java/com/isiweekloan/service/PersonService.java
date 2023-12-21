package com.isiweekloan.service;

import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.repository.PersonRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<PersonEntity> getAllPeople() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<PersonEntity> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Transactional
    public PersonEntity createPerson(PersonEntity personEntity) {
        validatePerson(personEntity);
        return personRepository.save(personEntity);
    }

    @Transactional
    public Optional<PersonEntity> updatePerson(Long id, PersonEntity personEntity) {
        Objects.requireNonNull(id, "Person ID cannot be null");
        validatePerson(personEntity);

        return personRepository.findById(id)
                .map(existingPerson -> {
                    // Update fields as needed
                    existingPerson.setFirstName(personEntity.getFirstName());
                    existingPerson.setLastName(personEntity.getLastName());
                    existingPerson.setEmail(personEntity.getEmail());
                    // Update other fields similarly

                    return personRepository.save(existingPerson);
                });
    }

    @Transactional
    public boolean deletePerson(Long id) {
        Objects.requireNonNull(id, "Person ID cannot be null");

        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validatePerson(PersonEntity personEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}
