package com.softuni.gamestore.services.order;

import com.softuni.gamestore.model.entities.Game;
import com.softuni.gamestore.model.entities.User;

import java.util.Set;

public interface OrderService {

    void createOrder(Long userId, Set<String> games);
}
