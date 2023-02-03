package com.project.javaapi.repository;

import com.project.javaapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

     Optional<Person> findByUsername (String username);
//     Person findByUsername (String username);

//     @Query(value = "SELECT p from Person p JOIN FETCH p.roles where p.username = :username")
//     Person findByUsernameFetchRoles(@Param("username") String username);
}
