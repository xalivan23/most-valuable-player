package com.example.mostvaluableplayer.repository;

import com.example.mostvaluableplayer.model.HandballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandballRepository extends JpaRepository<HandballPlayer, Long> {
}
