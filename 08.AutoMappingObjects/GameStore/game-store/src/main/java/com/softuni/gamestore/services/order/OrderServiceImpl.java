package com.softuni.gamestore.services.order;

import com.softuni.gamestore.model.entities.Game;
import com.softuni.gamestore.model.entities.Order;
import com.softuni.gamestore.model.entities.User;
import com.softuni.gamestore.repositories.GameRepository;
import com.softuni.gamestore.repositories.OrderRepository;
import com.softuni.gamestore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void createOrder(Long userId, Set<String> titles) {
        Optional<User> user = this.userRepository.findById(userId);
        assert (user.isPresent());

        Set<Game> games = this.gameRepository.findDistinctByTitleIn(titles);

        Order order = new Order();
        order.setBuyer(user.get());
        order.setProducts(games);

        this.orderRepository.save(order);
    }
}
