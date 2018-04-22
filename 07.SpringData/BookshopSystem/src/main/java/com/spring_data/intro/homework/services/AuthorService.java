package com.spring_data.intro.homework.services;

import com.spring_data.intro.homework.model.entities.Author;

import java.util.Date;
import java.util.List;

public interface AuthorService {

    void saveIntoDB(List<Author> authors);

    List<Author> getAll();

    List<String> getAllWithBooksBefore(Date date);
}
