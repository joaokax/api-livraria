package com.project.javaapi.controller;

import com.project.javaapi.model.Author;
import com.project.javaapi.model.Paper;
import com.project.javaapi.repository.PaperRepository;
import com.project.javaapi.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/papers")
public class PaperController {

    private final PaperRepository paperRepository;
    private final AuthorRepository authorRepository;

    public PaperController(PaperRepository paperRepository, AuthorRepository authorRepository) {
        this.paperRepository = paperRepository;
        this.authorRepository = authorRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Paper>> findByTitleAndSynopses(@RequestParam(value = "title", defaultValue = "") String title,
                                                              @RequestParam(value = "synopses", defaultValue = "") String synopses) {

        if (title.equals("")) {
            List<Paper> synopses1 = paperRepository.findBySynopses(synopses.trim().toUpperCase());
            return new ResponseEntity<>(synopses1, HttpStatus.OK);

        } else if (synopses.equals("")) {
            List<Paper> titles = paperRepository.findByTitle(title.trim().toUpperCase());
            return new ResponseEntity<>(titles, HttpStatus.OK);
        }

        List<Paper> papers = paperRepository.findByTitleAndSynopses(title.trim().toUpperCase(), synopses.trim().toUpperCase());
        return new ResponseEntity<>(papers, HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @GetMapping
//    public ResponseEntity<List<Author>> findByName(@RequestParam(value = "name", defaultValue = "") String name) {
//        List<Author> authors = authorRepository.findByName(name.trim().toUpperCase());
//        return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
//    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/all")
    public List<Paper> listAllPapers() {
        return paperRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Paper add(@RequestBody Paper paper) {
        return paperRepository.save(paper);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<Paper> update(@PathVariable Long id, @RequestBody Paper paper) {
        Paper updatePaper = paperRepository.findById(id).get();
        Author author = authorRepository.findById(paper.getAuthor().getId()).get();

        updatePaper.setTitle(paper.getTitle());
        updatePaper.setPicture(paper.getPicture());
        updatePaper.setCategoty(paper.getCategoty());
        updatePaper.setLanguage(paper.getLanguage());
        updatePaper.setSynopses(paper.getSynopses());
        updatePaper.setAuthor(author);

        paperRepository.save(updatePaper);
        return ResponseEntity.ok(updatePaper);
    }

}
