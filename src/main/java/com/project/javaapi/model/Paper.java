package com.project.javaapi.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;
    public String picture;
    public String categoty;
    public String language;
    public String synopses;
    @ManyToOne
    @JoinColumn(name = "author_id")
    public Author author;

}
