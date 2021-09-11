package com.example.mostvaluableplayer.repository;

import com.example.mostvaluableplayer.model.BasketballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketBallRepository extends JpaRepository<BasketballPlayer, Long > {
}
