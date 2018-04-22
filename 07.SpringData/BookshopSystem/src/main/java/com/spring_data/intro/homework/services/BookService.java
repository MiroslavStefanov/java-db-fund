package com.spring_data.intro.homework.services;

import com.spring_data.intro.homework.model.entities.Book;

import java.util.Date;
import java.util.List;

public interface BookService {

    void saveIntoDB(List<Book> books);

    List<String> getAllTitlesAfter(Date date);

    List<Book> getAllBooksByAuthorName(String firstName, String lastName);
}
