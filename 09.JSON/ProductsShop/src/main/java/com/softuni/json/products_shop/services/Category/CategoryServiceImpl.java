package com.softuni.json.products_shop.services.Category;

import com.softuni.json.products_shop.models.dto.binding.CategoryBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query3.CategoryWithProductCountViewModel;
import com.softuni.json.products_shop.models.entities.Category;
import com.softuni.json.products_shop.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(CategoryBindingModel[] categories) {

        List<Category> list = new ArrayList<>();

        for (CategoryBindingModel category : categories) {
            list.add(this.mapper.map(category, Category.class));
        }

        this.categoryRepository.saveAll(list);
    }

    @Override
    public List<CategoryWithProductCountViewModel> findAllWithProductCount() {
        return this.categoryRepository.findAllWithProductCount();
    }
}
