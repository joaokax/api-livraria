package com.project.javaapi.repository;

import com.project.javaapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "select a from Author a where upper(trim(a.name)) like %?1%")
    List<Author> findByName(String name);
}
