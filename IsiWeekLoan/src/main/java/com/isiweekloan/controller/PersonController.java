package com.isiweekloan.controller;

import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.exception.PersonNotFoundException;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.repository.PersonRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    // Get All Persons
    // URL: http://localhost:8080/api/people/1
    @GetMapping("/people")
    public ResponseEntity<List<PersonEntity>> getAllPerson() {
        List<PersonEntity> persons = personRepository.findAll();
        return ResponseEntity.ok(persons);
    }

    // Create a new Person
    // URL: http://localhost:8080/api/createPerson
    // Object json: {"name":"Rosa3333","username":"Marfi3333l"}
    @PostMapping("/createPerson")
    public ResponseEntity<?> createPerson(@Valid @RequestBody PersonEntity person) {
        try {
            personRepository.save(person);
            return ResponseEntity.ok(person);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }
    }

    // Get a Person by id
    // URL: http://localhost:8080/api/people/1
    @GetMapping("/person/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable(value = "id") Long id) {
        try {
            PersonEntity person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("ID no encontrado: " + id));
            return ResponseEntity.ok(person);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    // Update a Person
    // URL: http://localhost:8080/api/updatePerson/1
    // Object json: {"name":"RosaUpdate","username":"Marfil"}
    @PutMapping("/updatePerson/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable(value = "id") Long id, @Valid @RequestBody PersonEntity inPerson) {
        try {
            PersonEntity person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID no encontrado: " + id));

            person.setFirstName(inPerson.getFirstName());
            person.setLastName(inPerson.getLastName());

            personRepository.save(person);
            return ResponseEntity.ok(person);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    // Delete a Person
    // URL: http://localhost:8080/api/deletePerson/6
    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable(value = "id") Long id) {
        try {
            PersonEntity person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID no encontrado: " + id));

            personRepository.delete(person);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }
}
