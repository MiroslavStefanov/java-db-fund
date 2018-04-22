package com.softuni.json.products_shop.services.User;

import com.softuni.json.products_shop.models.dto.binding.UserCreateBindingModel;
import com.softuni.json.products_shop.models.dto.view.Query2.UserWithSoldProductsViewModel;
import com.softuni.json.products_shop.models.dto.view.Query4.UserWithAgeAndSoldProductViewModel;
import com.softuni.json.products_shop.models.entities.User;
import com.softuni.json.products_shop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(UserCreateBindingModel[] userModels) {
        List<User> users = new ArrayList<>();
        for (UserCreateBindingModel userModel : userModels) {
            if(userModel.validate())
                users.add(this.mapper.map(userModel, User.class));
        }
        this.userRepository.saveAll(users);
    }

    @Override
    public List<UserWithSoldProductsViewModel> findAllWithSoldProducts() {
        List<User> users = this.userRepository.findAllWithSoldProdcuts();
        return users.stream().map(u -> this.mapper.map(u, UserWithSoldProductsViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserWithAgeAndSoldProductViewModel> findAllWhoSoldProducts() {
        List<User> users = this.userRepository.findAllWithSoldProdcuts();
        return users
                .stream()
                .map(u -> this.mapper.map(u, UserWithAgeAndSoldProductViewModel.class))
                .sorted((x, y) -> {
                    if(x.getSoldProducts().getCount() == y.getSoldProducts().getCount())
                    return x.getLastName().compareTo(y.getLastName());

                    return Integer.compare(y.getSoldProducts().getCount(), x.getSoldProducts().getCount());
                })
                .collect(Collectors.toList());
    }
}
