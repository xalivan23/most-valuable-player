package com.example.mostvaluableplayer.service;

import com.example.mostvaluableplayer.model.BasketballPlayer;
import com.example.mostvaluableplayer.model.HandballPlayer;
import com.example.mostvaluableplayer.model.Player;
import com.example.mostvaluableplayer.repository.BasketBallRepository;
import com.example.mostvaluableplayer.repository.HandballRepository;
import com.example.mostvaluableplayer.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GameLogicService {
    private final BasketBallRepository basketBallRepository;
    private final HandballRepository handballRepository;
    private final PlayerRepository playerRepository;

    public GameLogicService(BasketBallRepository basketBallRepository, HandballRepository handballRepository, PlayerRepository playerRepository) {
        this.basketBallRepository = basketBallRepository;
        this.handballRepository = handballRepository;
        this.playerRepository = playerRepository;
    }

    public List<HandballPlayer> getAllHandballPlayers() {
        return handballRepository.findAll();
    }

    public List<BasketballPlayer> getAllBasketballPlayers() {
        return basketBallRepository.findAll();
    }

    public void setHandballGame(@RequestParam("playerName") String playerName,
                                @RequestParam("nickname") String nickname,
                                @RequestParam("numberPlayer") int numberPlayer,

                                @RequestParam("teamName") String teamName,

                                @RequestParam("goalsMade") int goalsMade,
                                @RequestParam("goalsReceived") int goalsReceived,

                                @RequestParam("numberGame") int numberGame) {
        int ratingPoints = HandballRatingPoints(goalsMade, goalsReceived);
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

        String handball = "Handball";
        handballPlayer.setNameOfGame(handball);

        handballRepository.save(handballPlayer);
    }

    public void RemoveHandballGame(Long id) {
        HandballPlayer byIdPlayer = handballRepository.findByIdPlayer(id);
        handballRepository.delete(byIdPlayer);
    }

    public void setBasketballGame(@RequestParam("playerName") String playerName,
                                  @RequestParam("nickname") String nickname,
                                  @RequestParam("numberPlayer") int numberPlayer,

                                  @RequestParam("teamName") String teamName,

                                  @RequestParam("scoredPoint") int scoredPoint,
                                  @RequestParam("rebound") int rebound,
                                  @RequestParam("assist") int assist,

                                  @RequestParam("numberGame") int numberGame) {
        int ratingPoints = BasketballRatingPoints(scoredPoint, rebound, assist);
        int teamVictory = 0;

        BasketballPlayer basketballPlayer = new BasketballPlayer();
        basketballPlayer.setPlayerName(playerName);
        basketballPlayer.setNickname(nickname);
        basketballPlayer.setNumberPlayer(numberPlayer);
        basketballPlayer.setTeamName(teamName);
        basketballPlayer.setRatingPoints(ratingPoints);

        basketballPlayer.setScoredPoint(scoredPoint);
        basketballPlayer.setRebound(rebound);
        basketballPlayer.setAssist(assist);

        basketballPlayer.setTeamVictory(teamVictory);
        basketballPlayer.setNumberGame(numberGame);

        String basketball = "Basketball";
        basketballPlayer.setNameOfGame(basketball);

        basketBallRepository.save(basketballPlayer);
    }

    public void RemoveBasketballGame(Long id) {
        BasketballPlayer byIdPlayer = basketBallRepository.findByIdPlayer(id);
        basketBallRepository.delete(byIdPlayer);
    }

    private int BasketballRatingPoints(int scoredPoint, int rebound, int assist) {
        int coefficientOf_ScoredPoint = 2;
        int coefficientOf_Rebound = 1;
        int coefficientOf_Assist = 1;
        return scoredPoint * coefficientOf_ScoredPoint + rebound * coefficientOf_Rebound + assist * coefficientOf_Assist;
    }

    private int HandballRatingPoints(int goalsMade, int goalsReceived) {
        int coefficientOfGoal = 2;
        int coefficientOfReceived = -1;
        return goalsMade * coefficientOfGoal + goalsReceived * coefficientOfReceived;
    }

    public Player getMVP() {
//        Optional<Player> all = playerRepository.findAllByNumberGame();
//        List<Player> playerList = new ArrayList<>();
//        all.ifPresent(playerList::add);
//        playerList.sort(Comparator.comparing(Player::getNumberGame));
//
//        playerList.size();

//        playerList.stream()
//                .sorted()
//                .map()

//        Player player = playerList.get(0);
//        int numberGame = all.get().getNumberGame();

//handballRepository.findAllByNumberGame();

        return null;
    }

}
