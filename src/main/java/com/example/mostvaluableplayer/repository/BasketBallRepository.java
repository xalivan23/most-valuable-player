package com.example.mostvaluableplayer.repository;

import com.example.mostvaluableplayer.model.BasketballPlayer;
import com.example.mostvaluableplayer.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketBallRepository extends JpaRepository<BasketballPlayer, Integer > {
    BasketballPlayer findByIdPlayer(Integer id);

    List<BasketballPlayer> findAllByNameOfGame(String basket);
}
