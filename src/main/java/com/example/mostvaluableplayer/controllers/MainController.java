package com.example.mostvaluableplayer.controllers;

import com.example.mostvaluableplayer.model.BasketballPlayer;
import com.example.mostvaluableplayer.model.HandballPlayer;
import com.example.mostvaluableplayer.model.Player;
import com.example.mostvaluableplayer.repository.BasketBallRepository;
import com.example.mostvaluableplayer.repository.HandballRepository;
import com.example.mostvaluableplayer.service.GameLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {
    private final GameLogicService gameLogicService;

    public MainController(GameLogicService gameLogicService) {
        this.gameLogicService = gameLogicService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "main page");
        return "main-page";
    }

    @GetMapping("/handball")
    public String handball(Model model) {
        List<HandballPlayer> allHandballPlayers = gameLogicService.getAllHandballPlayers();
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
        List<HandballPlayer> allHandballPlayers = gameLogicService.getAllHandballPlayers();
        model.addAttribute("allHandballPlayers", allHandballPlayers);

        return "handball";
    }

    @GetMapping("/handball/{idPlayer}/remove")
    public String handballRemove(@PathVariable(value = "idPlayer") Long idPlayer, Model model) {
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
    public String basketballRemove(@PathVariable(value = "idPlayer") Long idPlayer, Model model) {
        gameLogicService.RemoveBasketballGame(idPlayer);
        return "redirect:/basketball";
    }


    @GetMapping("/mvp")
    public String mvp(Model model) {
        Long idPlayer = gameLogicService.getMVP();
        Optional<Player> player = gameLogicService.getPlayer(idPlayer);
        List<Player>playerList=new ArrayList<>();
        player.ifPresent(playerList::add);

        int num = gameLogicService.num;
        model.addAttribute("playerList",playerList);
        model.addAttribute("num",num);
        model.addAttribute("title", "mvp");
        return "mvp";
    }

}
