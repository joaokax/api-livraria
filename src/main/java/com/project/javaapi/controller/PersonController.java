package com.project.javaapi.controller;

import com.project.javaapi.model.Person;
import com.project.javaapi.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;
    private final PasswordEncoder encoder;

    public PersonController(PersonRepository personRepository, PasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.encoder = encoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list-all")
    public ResponseEntity<List<Person>> listarTodos() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return ResponseEntity.ok(personRepository.save(person));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/validate-password")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String username,
                                                    @RequestParam String password){
        Person optPerson = personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

//        if (optPerson.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
//        }

//        Person person = optPerson;
//        Person person = optPerson.get();
        boolean valid = encoder.matches(password, optPerson.getPassword());
//        boolean valid = encoder.matches(password, person.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);

    }
}
