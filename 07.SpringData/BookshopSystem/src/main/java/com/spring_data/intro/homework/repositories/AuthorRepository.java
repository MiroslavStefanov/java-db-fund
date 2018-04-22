package com.spring_data.intro.homework.repositories;

import com.spring_data.intro.homework.model.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findDistinctByBooksReleaseDateBefore(Date date);
}
