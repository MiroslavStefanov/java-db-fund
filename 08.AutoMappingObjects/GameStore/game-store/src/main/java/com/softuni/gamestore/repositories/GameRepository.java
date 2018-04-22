package com.softuni.gamestore.repositories;

import com.softuni.gamestore.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findDistinctByTitle(String title);

    Set<Game> findDistinctByTitleIn(Set<String> titles);
}
