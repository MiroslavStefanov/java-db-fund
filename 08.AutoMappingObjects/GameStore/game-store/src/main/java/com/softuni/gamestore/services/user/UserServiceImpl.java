package com.softuni.gamestore.services.user;

import com.softuni.gamestore.model.dto.binding.UserLoginBindingModel;
import com.softuni.gamestore.model.dto.binding.UserRegisterBindingModel;
import com.softuni.gamestore.model.dto.view.UserLoginViewModel;
import com.softuni.gamestore.model.entities.Game;
import com.softuni.gamestore.model.entities.User;
import com.softuni.gamestore.repositories.GameRepository;
import com.softuni.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean register(UserRegisterBindingModel registerBinding) {

        Long id = null;

        if(registerBinding.validate()) {
            User user = mapper.map(registerBinding, User.class);
            user = this.userRepository.save(user);
            id = user.getId();
        }

        return id != null;
    }

    @Override
    public UserLoginViewModel login(UserLoginBindingModel loginBinding) {
        UserLoginViewModel model = null;

        User user = this.userRepository.findOneByEmail(loginBinding.getEmail());
        if(user != null)
            if(user.getPassword().equals(loginBinding.getPassword()))
                model = mapper.map(user, UserLoginViewModel.class);

        return model;
    }

    @Override
    public String logout(Long userId) {
        if(userId == null)
            return null;

        Optional<User> user = this.userRepository.findById(userId);
        if(user.isPresent())
            return user.get().getFullName();
        else
            return null;
    }

    @Override
    public boolean isAdmin(Long userId) {
        if(userId == null)
            return false;

        Optional<User> user = this.userRepository.findById(userId);

        if(user.isPresent())
            return user.get().getAdmin();

        return false;
    }

    @Override
    public List<String> getOwnedGames(Long userId) {
        if(userId == null)
            return null;

        Optional<User> user = this.userRepository.findById(userId);
        assert (user.isPresent());

        return user.get().getGames().stream().map(Game::getTitle).collect(Collectors.toList());
    }

    @Override
    public boolean hasGame(Long userId, String title) {
        assert (userId != null);

        List<String> owned = this.getOwnedGames(userId);

        return owned.contains(title);
    }

    @Override
    public void addGames(Long userId, Set<String> titles) {
        assert (userId != null);

        Optional<User> user = this.userRepository.findById(userId);
        assert (user.isPresent());

        user.get().getGames().addAll(this.gameRepository.findDistinctByTitleIn(titles));
        this.userRepository.save(user.get());
    }
}
