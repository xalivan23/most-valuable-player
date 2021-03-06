package com.example.mostvaluableplayer.repository;

import com.example.mostvaluableplayer.model.HandballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandballRepository extends JpaRepository<HandballPlayer, Integer> {

    HandballPlayer findByIdPlayer(Integer id);

   List<HandballPlayer> findAllByNumberGame(int numberGame);
}
