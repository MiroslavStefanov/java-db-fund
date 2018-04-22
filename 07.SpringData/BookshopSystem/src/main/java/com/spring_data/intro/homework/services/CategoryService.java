package com.spring_data.intro.homework.services;

import com.spring_data.intro.homework.model.entities.Category;

import java.util.List;

public interface CategoryService {

    void saveIntoDB(List<Category> categories);
}
