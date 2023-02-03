package com.project.javaapi.repository;

import com.project.javaapi.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {

    @Query(value = "select p from Paper p where upper(trim(p.title)) like %?1% or upper(trim(p.synopses)) like %?2%")
    List<Paper> findByTitleAndSynopses(String title, String synopses);

    @Query(value = "select p from Paper p where upper(trim(p.title)) like %?1%")
    List<Paper> findByTitle(String title);

    @Query(value = "select p from Paper p where upper(trim(p.synopses)) like %?1%")
    List<Paper> findBySynopses(String synopses);

//    List<Paper> findPaperByTitleIsLikeIgnoreCaseOrSynopsesIsLikeIgnoreCase(String title, String synopses);
//    List<Paper> findByTitleIgnoreCaseOrSynopsesIgnoreCase(String title, String synopses);

}
