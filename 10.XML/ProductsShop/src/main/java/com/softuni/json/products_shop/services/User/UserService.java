package com.softuni.json.products_shop.services.User;

import com.softuni.json.products_shop.models.dto.binding.UserCreateBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query2.UserWithSoldProductsViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.UserWithAgeAndSoldProductViewModel;

import java.util.List;

public interface UserService {

    void save(UserCreateBindingModel[] userModels);

    List<UserWithSoldProductsViewModel> findAllWithSoldProducts();

    List<UserWithAgeAndSoldProductViewModel> findAllWhoSoldProducts();
}
