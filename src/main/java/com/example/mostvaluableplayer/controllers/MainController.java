package com.example.mostvaluableplayer.controllers;

import com.example.mostvaluableplayer.model.BasketballPlayer;

import com.example.mostvaluableplayer.model.Player;

import com.example.mostvaluableplayer.service.Factory;
import com.example.mostvaluableplayer.service.GameLogicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;

@Controller
public class MainController {
    private final GameLogicService gameLogicService;
    //    private final CSVMappedToJavaBean csvMappedToJavaBean;
    private final Factory factory;

    public MainController(GameLogicService gameLogicService, Factory factory) {
        this.gameLogicService = gameLogicService;
        this.factory = factory;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "main page");
        return "main-page";
    }

    @GetMapping("/handball")
    public String handball(Model model) {
        List<Player> allHandballPlayers = gameLogicService.getAllHandballPlayers();
        model.addAttribute("allHandballPlayers", allHandballPlayers);
        return "handball";
    }

    @PostMapping("/handball")
    public String handballSavePlayer(Model model,
                                     @RequestParam("playerName") String playerName,
                                     @RequestParam("nickname") String nickname,
                                     @RequestParam("numberPlayer") int numberPlayer,

                                     @RequestParam("teamName") String teamName,

                                     @RequestParam("goalsMade") int goalsMade,
                                     @RequestParam("goalsReceived") int goalsReceived,

                                     @RequestParam("numberGame") int numberGame) {
        gameLogicService.setHandballGame(playerName, nickname, numberPlayer, teamName, goalsMade, goalsReceived, numberGame);
        List<Player> allHandballPlayers = gameLogicService.getAllHandballPlayers();
        model.addAttribute("allHandballPlayers", allHandballPlayers);

        return "handball";
    }

    @GetMapping("/handball/{idPlayer}/remove")
    public String handballRemove(@PathVariable(value = "idPlayer") Integer idPlayer, Model model) {
        gameLogicService.RemoveHandballGame(idPlayer);
        return "redirect:/handball";
    }


    @GetMapping("/basketball")
    public String basketball(Model model) {
        List<BasketballPlayer> allBasketballPlayers = gameLogicService.getAllBasketballPlayers();
        model.addAttribute("allBasketballPlayers", allBasketballPlayers);
        return "basketball";
    }

    //    private int scoredPoint;
//    private int rebound;
//    private int assist;
    @PostMapping("/basketball")
    public String basketballSavePlayer(Model model,
                                       @RequestParam("playerName") String playerName,
                                       @RequestParam("nickname") String nickname,
                                       @RequestParam("numberPlayer") int numberPlayer,

                                       @RequestParam("teamName") String teamName,

                                       @RequestParam("scoredPoint") int scoredPoint,
                                       @RequestParam("rebound") int rebound,
                                       @RequestParam("assist") int assist,

                                       @RequestParam("numberGame") int numberGame) {
        gameLogicService.setBasketballGame(playerName, nickname, numberPlayer, teamName, scoredPoint, rebound, assist, numberGame);
        List<BasketballPlayer> allBasketballPlayers = gameLogicService.getAllBasketballPlayers();
        model.addAttribute("allBasketballPlayers", allBasketballPlayers);

        return "basketball";
    }

    @GetMapping("/basketball/{idPlayer}/remove")
    public String basketballRemove(@PathVariable(value = "idPlayer") Integer idPlayer, Model model) {
        gameLogicService.RemoveBasketballGame(idPlayer);
        return "redirect:/basketball";
    }


    @GetMapping("/mvp")
    public String mvp(Model model) {
        Integer idPlayer = gameLogicService.getMVP();
        Optional<Player> player = gameLogicService.getPlayer(idPlayer);
        List<Player> playerList = new ArrayList<>();
        player.ifPresent(playerList::add);

        int num = GameLogicService.num;
        model.addAttribute("playerList", playerList);
        model.addAttribute("num", num);
        model.addAttribute("title", "mvp");
        return "mvp";
    }

    @PostMapping("/load-file-system")
    @Transactional
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file) throws IOException {
        String name = "C:\\FATHER\\SKAI\\most-valuable-player\\src\\main\\resources\\files\\";
        if (!file.isEmpty()) {

            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(name + file.getOriginalFilename())));
            stream.write(bytes);
            stream.close();

            String URL = name + file.getOriginalFilename();

            System.out.println(URL);
            factory.getPlayerGame(URL); //Factory
            return "redirect:/";

        } else {
            return "redirect:/mvp";
        }
    }

}
