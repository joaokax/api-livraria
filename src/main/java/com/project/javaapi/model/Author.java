package com.project.javaapi.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    @Column(unique = true)
    public String username;
    public String picture;
    public String country;
    public String bio;

}
