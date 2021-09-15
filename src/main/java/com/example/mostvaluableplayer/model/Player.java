package com.example.mostvaluableplayer.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "player")
public class Player  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer idPlayer;

    private String playerName;
    private String nickname;
    private String teamName;

    int ratingPoints;
    int teamVictory;

    private String nameOfGame;
    private int numberPlayer;
    private int numberGame;

}
