package com.example.mostvaluableplayer.service;

import com.example.mostvaluableplayer.model.BasketballPlayer;
import com.example.mostvaluableplayer.model.HandballPlayer;
import com.example.mostvaluableplayer.model.Player;
import com.example.mostvaluableplayer.repository.BasketBallRepository;
import com.example.mostvaluableplayer.repository.HandballRepository;
import com.example.mostvaluableplayer.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameLogicService {
    public static int num = 0;
    private final BasketBallRepository basketBallRepository;
    private final HandballRepository handballRepository;
    private final PlayerRepository playerRepository;
    String basket = "BASKETBALL";
    String handball = "HANDBALL";
    public GameLogicService(BasketBallRepository basketBallRepository, HandballRepository handballRepository, PlayerRepository playerRepository) {
        this.basketBallRepository = basketBallRepository;
        this.handballRepository = handballRepository;
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllHandballPlayers() {
        return playerRepository.findAllByNameOfGame(handball);
    }

    public List<BasketballPlayer> getAllBasketballPlayers() {
        return basketBallRepository.findAllByNameOfGame(basket);
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
//        String basket = "BASKETBALL";
//        String handball = "HANDBALL";
        String handball = "HANDBALL";
        handballPlayer.setNameOfGame(handball);

        handballRepository.save(handballPlayer);
    }

    public void RemoveHandballGame(Integer id) {
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

        String basketball = "BASKETBALL";
        basketballPlayer.setNameOfGame(basketball);

        basketBallRepository.save(basketballPlayer);
    }

    public void RemoveBasketballGame(Integer id) {
        BasketballPlayer byIdPlayer = basketBallRepository.findByIdPlayer(id);
        basketBallRepository.delete(byIdPlayer);
    }

    public int BasketballRatingPoints(int scoredPoint, int rebound, int assist) {
        int coefficientOf_ScoredPoint = 2;
        int coefficientOf_Rebound = 1;
        int coefficientOf_Assist = 1;
        return scoredPoint * coefficientOf_ScoredPoint + rebound * coefficientOf_Rebound + assist * coefficientOf_Assist;
    }

    public int HandballRatingPoints(int goalsMade, int goalsReceived) {
        int coefficientOfGoal = 2;
        int coefficientOfReceived = -1;
        return goalsMade * coefficientOfGoal + goalsReceived * coefficientOfReceived;
    }

    public Integer getMVP() {
        List<Player> number = playerRepository.findAll();
        number.sort(Comparator.comparing(Player::getNumberGame).reversed());
        int numberGame = number.get(0).getNumberGame();

        for (int i = 1; i < numberGame + 1; i++) {
            List<Player> a = playerRepository.findAllByNumberGameAndTeamName(i, "Team A");
            List<Player> b = playerRepository.findAllByNumberGameAndTeamName(i, "Team B");
            int sumA = a.stream().mapToInt(Player::getRatingPoints).sum();
            int sumB = b.stream().mapToInt(Player::getRatingPoints).sum();
            if (sumA > sumB) {
                for (int j = 0; j < 3; j++) {
                    Integer idPlayer = a.get(j).getIdPlayer();
                    Player player = playerRepository.findByIdPlayer(idPlayer).orElseThrow(null);
                    player.setTeamVictory(10);
                    playerRepository.save(player);
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    Integer idPlayer = b.get(j).getIdPlayer();
                    Player player = playerRepository.findByIdPlayer(idPlayer).orElseThrow(null);
                    player.setTeamVictory(10);
                    playerRepository.save(player);
                }
            }
        }
        return best();
    }

    private Integer best() {
        List<Player> all = playerRepository.findAll();
        Map<Integer, Integer> map = new TreeMap<>();

        for (int i = 0; i < all.size(); i++) {
            int kay = all.get(i).getIdPlayer();
            int value = all.get(i).getRatingPoints() + all.get(i).getTeamVictory();
            map.put(kay, value);
        }
        int key = Math.toIntExact(map.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey());

        int value = map.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();

        num = value;
        return key;
    }


    public Optional<Player> getPlayer(Integer id) {
        Optional<Player> byId = playerRepository.findById(id);
        return byId;
    }

}
