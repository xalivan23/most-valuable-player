package com.example.mostvaluableplayer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "player")
public class Player extends Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long idPlayer;

    String playerName;
    String nickname;
    String teamName;

    int ratingPoints;
    int teamVictory;
//    private String gameNameOfGame;
//    private int numberPlayer;

}
