package com.softuni.gamestore.services.game;

import com.softuni.gamestore.model.dto.binding.GameAddBindingModel;
import com.softuni.gamestore.model.dto.binding.GameEditBindingModel;
import com.softuni.gamestore.model.dto.view.AllGameViewModel;
import com.softuni.gamestore.model.dto.view.DetailGameViewModel;
import com.softuni.gamestore.model.entities.Game;
import com.softuni.gamestore.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelMapper mapper) {
        this.gameRepository = gameRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean add(GameAddBindingModel model) {

        boolean isValid = model.validate();
        if(!isValid) return false;

        Game game = this.mapper.map(model, Game.class);
        game = this.gameRepository.save(game);

        return true;
    }

    @Override
    public String edit(GameEditBindingModel model) {

        if(!model.validate())
            return "invalid";

        Optional<Game> optional = this.gameRepository.findById(model.getId());

        if(!optional.isPresent())
            return null;

        Game game = optional.get();

        this.mapper.map(model.getModel(), game);
        this.gameRepository.save(game);
        return game.getTitle();
    }

    @Override
    public String delete(Long id) {
        Optional<Game> game = this.gameRepository.findById(id);

        if(!game.isPresent())
            return null;

        this.gameRepository.deleteById(id);
        return game.get().getTitle();
    }

    @Override
    public List<AllGameViewModel> getAllGames() {
        return this.gameRepository.findAll().stream().map(g -> this.mapper.map(g, AllGameViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public DetailGameViewModel getDetailGame(String title) {
        Game game = this.gameRepository.findDistinctByTitle(title);
        if(game == null)
            return null;

        return this.mapper.map(game, DetailGameViewModel.class);
    }

    @Override
    public boolean exists(String title) {
        Game game = this.gameRepository.findDistinctByTitle(title);

        return game!=null;
    }
}
