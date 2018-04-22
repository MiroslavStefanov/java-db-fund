package com.spring_data.intro.homework.services;

import com.spring_data.intro.homework.model.entities.Book;
import com.spring_data.intro.homework.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveIntoDB(List<Book> books) {
        this.bookRepository.saveAll(books);
    }

    @Override
    public List<String> getAllTitlesAfter(Date date) {
        return this.bookRepository.findAllByReleaseDateAfter(date).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooksByAuthorName(String firstName, String lastName) {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastName(firstName, lastName);
    }
}
