package com.example.mostvaluableplayer.repository;

import com.example.mostvaluableplayer.model.BasketballPlayer;
import com.example.mostvaluableplayer.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    List<Player> findAllByNumberGame(int numberGame);
    List<Player> findAllByNumberGameAndTeamName(int numberName, String teamName);
    Optional<Player> findByIdPlayer(int idPlayer);
    List<Player> findAllByNameOfGame(String nameOfGame);

    Optional<Player> findAllByIdPlayer(int idPlayer);
}
