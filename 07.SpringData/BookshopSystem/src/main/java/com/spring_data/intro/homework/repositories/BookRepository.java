package com.spring_data.intro.homework.repositories;

import com.spring_data.intro.homework.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(Date date);

    List<Book> findAllByAuthorFirstNameAndAuthorLastName(String firstName, String lastName);
}
