package com.example.mostvaluableplayer.service;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.example.mostvaluableplayer.model.BasketballPlayer;
import com.example.mostvaluableplayer.model.HandballPlayer;
import com.example.mostvaluableplayer.model.Player;
import com.example.mostvaluableplayer.model.TypeGame;
import com.example.mostvaluableplayer.repository.BasketBallRepository;
import com.example.mostvaluableplayer.repository.HandballRepository;
import com.example.mostvaluableplayer.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


import static com.example.mostvaluableplayer.model.TypeGame.BASKETBALL;
import static com.example.mostvaluableplayer.model.TypeGame.HANDBALL;
import static java.lang.String.valueOf;

@Service
public class Factory {

    private final PlayerRepository playerRepository;
    private final GameLogicService gameLogicService;
    private final BasketBallRepository basketBallRepository;
    private final HandballRepository handballRepository;
//    private int counter = 1;


    public Factory(PlayerRepository playerRepository, GameLogicService gameLogicService, BasketBallRepository basketBallRepository, HandballRepository handballRepository) {
        this.playerRepository = playerRepository;
        this.gameLogicService = gameLogicService;
        this.basketBallRepository = basketBallRepository;
        this.handballRepository = handballRepository;

    }

    public void getPlayerGame(String path) throws IOException {
        CsvToBean csv = new CsvToBean();
        BufferedReader readerLine = new BufferedReader(new FileReader(path));
        String currentLine = readerLine.readLine();
        readerLine.close();


        if (currentLine.equals(BASKETBALL.toString())) {
            CSVReader csvReader = new CSVReader(new FileReader(path), ';', '"', 1);
            List list = csv.parse(setBasketball(), csvReader);

            for (Object object : list) {
                BasketballPlayer player = (BasketballPlayer) object;
                int ratingPoints = gameLogicService.BasketballRatingPoints(((BasketballPlayer) object).getScoredPoint(), ((BasketballPlayer) object).getRebound(), ((BasketballPlayer) object).getAssist());
                player.setRatingPoints(ratingPoints);

                player.setPlayerName(((Player) object).getPlayerName());
                player.setNickname(((Player) object).getNickname());
                player.setNumberPlayer(((Player) object).getNumberPlayer());
                player.setTeamName(((Player) object).getTeamName());
                player.setNameOfGame(TypeGame.BASKETBALL.toString());
                player.setNumberGame(1);
//                "scoredPoint", "rebound", "assist"
                player.setScoredPoint(((BasketballPlayer) object).getScoredPoint());
                player.setRebound(((BasketballPlayer) object).getRebound());
                player.setAssist(((BasketballPlayer) object).getAssist());
//                player.setNameOfGame(basket);
//                gameLogicService.getMVP();

                basketBallRepository.save(player);
                if (player.getNickname() == null) {
                    Integer idPlayer = player.getIdPlayer();
                    BasketballPlayer byIdPlayer = basketBallRepository.findByIdPlayer(idPlayer);
                    basketBallRepository.delete(byIdPlayer);
                }
                System.out.println(player);
            }
        }
        if (currentLine.equals(HANDBALL.toString())) {

            CSVReader csvReader2 = new CSVReader(new FileReader(path), ';', '"', 1);
            List list2 = csv.parse(setHandball(), csvReader2);
            for (Object object : list2) {
                HandballPlayer player = (HandballPlayer) object;
                int ratingPoints = gameLogicService.HandballRatingPoints(((HandballPlayer) object).getGoalsMade(), ((HandballPlayer) object).getGoalsReceived());
                player.setRatingPoints(ratingPoints);

                player.setPlayerName(((Player) object).getPlayerName());
                player.setNickname(((Player) object).getNickname());
                player.setNumberPlayer(((Player) object).getNumberPlayer());
                player.setTeamName(((Player) object).getTeamName());
                player.setNameOfGame(TypeGame.HANDBALL.toString());
                player.setNumberGame(2);
//                "goalsMade", "goalsReceived"
                player.setGoalsMade(((HandballPlayer) object).getGoalsMade());
                player.setGoalsReceived(((HandballPlayer) object).getGoalsReceived());

                handballRepository.save(player);
                if (player.getNickname() == null) {
                    Integer idPlayer = player.getIdPlayer();
                    HandballPlayer byIdPlayer = handballRepository.findByIdPlayer(idPlayer);
                    handballRepository.delete(byIdPlayer);
                }
                System.out.println(player);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setBasketball() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(BasketballPlayer.class);
        String[] columns = new String[]{"playerName", "nickname", "numberPlayer", "teamName", "scoredPoint", "rebound", "assist"};
        strategy.setColumnMapping(columns);
        return strategy;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setHandball() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(HandballPlayer.class);
        String[] columns = new String[]{"playerName", "nickname", "numberPlayer", "teamName", "goalsMade", "goalsReceived"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
