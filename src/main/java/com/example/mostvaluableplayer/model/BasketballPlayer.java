package com.example.mostvaluableplayer.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "basketball")
public class BasketballPlayer extends Player {
    private int scoredPoint;
    private int rebound;
    private int assist;
    //    String nickname;
//    String teamName;
//    int ratingPoints;
//    int teamVictory;
//    private String gameName;
//    private int numberPlayer;

}
