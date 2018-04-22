package com.spring_data.intro.homework.services;

import com.spring_data.intro.homework.model.entities.Category;
import com.spring_data.intro.homework.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveIntoDB(List<Category> categories) {
        this.categoryRepository.saveAll(categories);
    }
}
