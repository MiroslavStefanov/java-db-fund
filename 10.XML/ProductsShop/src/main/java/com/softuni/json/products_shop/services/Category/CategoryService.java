package com.softuni.json.products_shop.services.Category;

import com.softuni.json.products_shop.models.dto.binding.CategoryBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query3.CategoryWithProductCountViewModel;

import java.util.List;

public interface CategoryService {

    void save(CategoryBindingModel[] categories);

    List<CategoryWithProductCountViewModel> findAllWithProductCount();
}
