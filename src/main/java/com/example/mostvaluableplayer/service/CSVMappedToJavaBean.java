package com.example.mostvaluableplayer.service;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.example.mostvaluableplayer.model.BasketballPlayer;
import com.example.mostvaluableplayer.model.HandballPlayer;
import com.example.mostvaluableplayer.model.Player;
import com.example.mostvaluableplayer.repository.BasketBallRepository;
import com.example.mostvaluableplayer.repository.HandballRepository;
import com.example.mostvaluableplayer.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVMappedToJavaBean {
    private final PlayerRepository playerRepository;
    private final GameLogicService gameLogicService;
    private final BasketBallRepository basketBallRepository;
    private final HandballRepository handballRepository;
    private int counter = 1;


    public CSVMappedToJavaBean(PlayerRepository playerRepository, GameLogicService gameLogicService, BasketBallRepository basketBallRepository, HandballRepository handballRepository) {
        this.playerRepository = playerRepository;
        this.gameLogicService = gameLogicService;
        this.basketBallRepository = basketBallRepository;
        this.handballRepository = handballRepository;

    }

    public int increment() {
        return counter++;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
//    public static void main(String[] args) throws Exception {
    public void fileSender(String path) throws IOException {
        String[] nextLine;
        String basket = "BASKETBALL";
        String handball = "HANDBALL";

//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getResource());
//        File s = new File(String.valueOf(file));
//        System.out.println(Arrays.toString(file.getBytes()));
//        File csvFilename = s;
//        System.out.println(s);

//        String csvFilename = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename)

//        String csvFilename = "C:\\FATHER\\SKAI\\most-valuable-player\\src\\main\\resources\\files\\game1.csv";
//        String csvFilename = "C:\\FATHER\\SKAI\\most-valuable-player\\src\\main\\resources\\files\\game2.csv";
        CsvToBean csv = new CsvToBean();

        BufferedReader readerLine = new BufferedReader(new FileReader(path));

        String currentLine = readerLine.readLine();
        readerLine.close();

        if (currentLine.equals(basket)) {
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
                player.setNameOfGame(basket);
                player.setNumberGame(1);
//                "scoredPoint", "rebound", "assist"
                player.setScoredPoint(((BasketballPlayer) object).getScoredPoint());
                player.setRebound(((BasketballPlayer) object).getRebound());
                player.setAssist(((BasketballPlayer) object).getAssist());
//                player.setNameOfGame(basket);
//                gameLogicService.getMVP();
                basketBallRepository.save(player);
                System.out.println(player);
            }
        } else if (currentLine.equals(handball)) {
            CSVReader csvReader = new CSVReader(new FileReader(path), ';', '"', 1);
            List list = csv.parse(setHandball(), csvReader);
            for (Object object : list) {
                HandballPlayer player = (HandballPlayer) object;
                int ratingPoints = gameLogicService.HandballRatingPoints(((HandballPlayer) object).getGoalsMade(), ((HandballPlayer) object).getGoalsReceived());
                player.setRatingPoints(ratingPoints);

                player.setPlayerName(((Player) object).getPlayerName());
                player.setNickname(((Player) object).getNickname());
                player.setNumberPlayer(((Player) object).getNumberPlayer());
                player.setTeamName(((Player) object).getTeamName());
                player.setNameOfGame(handball);
                player.setNumberGame(1);
//                "goalsMade", "goalsReceived"
                player.setGoalsMade(((HandballPlayer) object).getGoalsMade());
                player.setGoalsReceived(((HandballPlayer) object).getGoalsReceived());

                handballRepository.save(player);

                System.out.println(player);
            }
        } else {
            System.out.println("ERROR");
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