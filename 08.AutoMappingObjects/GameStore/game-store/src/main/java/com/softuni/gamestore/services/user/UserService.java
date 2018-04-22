package com.softuni.gamestore.services.user;

import com.softuni.gamestore.model.dto.binding.UserLoginBindingModel;
import com.softuni.gamestore.model.dto.binding.UserRegisterBindingModel;
import com.softuni.gamestore.model.dto.view.UserLoginViewModel;
import com.softuni.gamestore.model.entities.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    boolean register(UserRegisterBindingModel registerBinding);

    UserLoginViewModel login(UserLoginBindingModel loginBinding);

    String logout(Long userId);

    boolean isAdmin(Long userId);

    List<String> getOwnedGames(Long userId);

    boolean hasGame(Long userId, String title);

    void addGames(Long userId, Set<String> titles);
}
