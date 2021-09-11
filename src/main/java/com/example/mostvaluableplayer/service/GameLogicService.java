package com.example.mostvaluableplayer.service;

import com.example.mostvaluableplayer.model.HandballPlayer;
import com.example.mostvaluableplayer.repository.BasketBallRepository;
import com.example.mostvaluableplayer.repository.HandballRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GameLogicService {
    private final BasketBallRepository basketBallRepository;
    private final HandballRepository handballRepository;

    public GameLogicService(BasketBallRepository basketBallRepository, HandballRepository handballRepository) {
        this.basketBallRepository = basketBallRepository;
        this.handballRepository = handballRepository;
    }

    public void getHandballGame(@RequestParam("nickname") String nickname,
                                @RequestParam("playerName") String playerName,
                                @RequestParam("teamName") String teamName,
                                @RequestParam("numberPlayer") int numberPlayer,
                                @RequestParam("goalsMade") int goalsMade,
                                @RequestParam("goalsReceived") int goalsReceived,
                                @RequestParam("numberGame") int numberGame) {
        int ratingPoints = RatingPoints(goalsMade, goalsReceived);
        int teamVictory = 0;

        HandballPlayer handballPlayer = new HandballPlayer();
        handballPlayer.setPlayerName(playerName);
        handballPlayer.setNickname(nickname);
        handballPlayer.setNumberPlayer(numberPlayer);
        handballPlayer.setTeamName(teamName);
        handballPlayer.setGoalsMade(goalsMade);
        handballPlayer.setGoalsReceived(goalsReceived);
        handballPlayer.setRatingPoints(ratingPoints);
        handballPlayer.setTeamVictory(teamVictory);
        handballPlayer.setNumberGame(numberGame);

        handballRepository.save(handballPlayer);
    }

    private int RatingPoints(int goalsMade, int goalsReceived) {
        int coefficientOfGoal = 2;
        int coefficientOfReceived = 1;
        return goalsMade * coefficientOfGoal - goalsReceived * coefficientOfReceived;
    }

//    private int goalsMade;
//    private int goalsReceived;

//    String playerName;
//    String nickname;
//    String teamName;
//
//    int ratingPoints;+
//    int teamVictory;+

//    private String nameOfGame;+
//    private int numberPlayer;
//    private int numberGame;
}
