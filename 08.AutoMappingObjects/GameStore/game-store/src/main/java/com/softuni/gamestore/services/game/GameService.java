package com.softuni.gamestore.services.game;

import com.softuni.gamestore.model.dto.binding.GameAddBindingModel;
import com.softuni.gamestore.model.dto.binding.GameEditBindingModel;
import com.softuni.gamestore.model.dto.view.AllGameViewModel;
import com.softuni.gamestore.model.dto.view.DetailGameViewModel;

import java.util.List;

public interface GameService {

    boolean add(GameAddBindingModel model);

    String edit(GameEditBindingModel model);

    String delete(Long id);

    List<AllGameViewModel> getAllGames();

    DetailGameViewModel getDetailGame(String title);

    boolean exists(String title);
}
