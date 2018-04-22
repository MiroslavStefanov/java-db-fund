package com.spring_data.intro.homework.services;

import com.spring_data.intro.homework.model.entities.Author;
import com.spring_data.intro.homework.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void saveIntoDB(List<Author> authors) {

        authorRepository.saveAll(authors);
    }

    @Override
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public List<String> getAllWithBooksBefore(Date date) {
        return this.authorRepository.findDistinctByBooksReleaseDateBefore(date)
                .stream()
                .map(x -> x.getFirstName() + " " + x.getLastName())
                .collect(Collectors.toList());
    }
}
