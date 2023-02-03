package com.project.javaapi.controller;

import com.project.javaapi.model.Author;
import com.project.javaapi.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Author>> findByName(@RequestParam(value = "name", defaultValue = "") String name) {
        List<Author> authors = authorRepository.findByName(name.trim().toUpperCase());
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/all")
    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Author add(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author author) {
        Author updateAuthor = authorRepository.findById(id).get();

        updateAuthor.setName(author.getName());
        updateAuthor.setUsername(author.getUsername());
        updateAuthor.setPicture(author.getPicture());
        updateAuthor.setCountry(author.getCountry());
        updateAuthor.setBio(author.getBio());

        authorRepository.save(updateAuthor);
        return ResponseEntity.ok(updateAuthor);
    }

}
