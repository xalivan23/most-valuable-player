package com.example.mostvaluableplayer.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "handball")
public class HandballPlayer extends Player{
    private int goalsMade;
    private int goalsReceived;

    public HandballPlayer(Long idUser, String playerName, String nickname, String teamName, int ratingPoints, int teamVictory, int goalsMade, int goalsReceived) {
        super(idUser, playerName, nickname, teamName, ratingPoints, teamVictory);
        this.goalsMade = goalsMade;
        this.goalsReceived = goalsReceived;
    }

    public HandballPlayer(int goalsMade, int goalsReceived) {
        this.goalsMade = goalsMade;
        this.goalsReceived = goalsReceived;
    }

    //    String playerName;
//    String nickname;
//    String teamName;
//
//    int ratingPoints;
//    int teamVictory;

//    private String nameOfGame;
//    private int numberPlayer;

}
