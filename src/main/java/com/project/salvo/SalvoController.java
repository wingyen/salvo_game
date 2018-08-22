package com.project.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    private GameRepository gameRepository;

    /**
     * This is called End Point
     * @return
     */
    @RequestMapping("/games")
    private List<GameVO> getIds () {
        return gameRepository
                .findAll().stream()
                .map(GameVO::new).collect(toList());
    }

    static private class GameVO {
        public final long id;
        public final Date created;
        //public final Date finished;
        public List gamePlayers;
        List<ScoreVO> scores;


        public GameVO(Game game) {
            this.id = game.getId();
            this.created = game.getCreatedTime();
            //this.finished = game.get

            this.gamePlayers = game.getGamePlayers().stream()
                                    .map(gamePlayer->new GamePlayerVO(gamePlayer))
                                    .collect(toList());

            //this.scores = game.getScores().stream().map(score -> new ScoreVO(score)).collect(Collectors.toList());

        }


    }

    /**
     * this GamePlayerVO is been used in "/games" and "/game_views"
     */

    static private class GamePlayerVO {

        public long id;
        public Player player;
        public Double score;

        public GamePlayerVO(GamePlayer gamePlayer) {
            this.id = gamePlayer.getId();
            this.player = gamePlayer.getPlayer();

            if(gamePlayer.getGameScore()!= null) /**Some players don't have scores then it returns null  */
            this.score = gamePlayer.getGameScore().getScore();
        }


    }

    /**
     * Request mapping the web file to demonstrate html
     * @param name
     * @param model
     * @return
     */

    @RequestMapping("/web")
    public String greeting(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @RequestMapping("game_view/{gamePlayerId}")

    public GameViewVO getGameView(@PathVariable long gamePlayerId) {
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerId);

        return new GameViewVO(gamePlayer);
    }

    static private class GameViewVO{
        public long id;
        public Date created;
        public List gamePlayers;
        public List ships;
        public List salvos;

        public GameViewVO(GamePlayer gamePlayer) {
            this.id = gamePlayer.getId();
            this.created = gamePlayer.getCreatedTime();
            this.gamePlayers = gamePlayer
                                .getGame()
                                .getGamePlayers().stream()
                                .map(GamePlayerVO::new).collect(toList());

            this.ships = gamePlayer.getShips().stream()
                                    .map(ShipsVO::new).collect(toList());

            this.salvos = gamePlayer.getGame()
                                     .getSalvosList().stream()
                                     .map(salvos -> salvos
                                                    .stream()
                                                    .map(SalvoVO::new).collect(toList()))
                                        .collect(toList());
        }

    }


       @RequestMapping("/leader_board")



    public List<Map<String, Object>> getScores() {
        List<Map<String, Object>> scoresMap = new ArrayList<>();

        List<GamePlayer> gamePlayers = gamePlayerRepository.findAll();


        for (GamePlayer gp: gamePlayers) {
            if(!scoresMap.contains(gp.getPlayer().getUserName())){
                Map<String, Object> scores = new LinkedHashMap<>();
                scores.put("userName",gp.getPlayer().getUserName());
                scores.put("wins", gp.getPlayer().getScores().stream()
                                                .filter(gameScore -> gameScore.getScore() == 1).count());
                scores.put("ties", gp.getGame().getScores().stream()
                                                .filter(gameScore -> gameScore.getScore() == 0.5).count());
                scores.put("looses", gp.getGame().getScores().stream()
                                                .filter(gameScore -> gameScore.getScore() == 0).count());
                scores.put("total", gp.getGame().getScores().stream()
                                                .mapToDouble(gameScore -> gameScore.getScore()).sum());
                scoresMap.add(scores);
            }
        }
        return scoresMap;
    }
//    public List<ScoresVO> getScores {
//        return gamePlayerRepository.findAll().stream().map(())
//    }


    /**
     * Some new value objects below. (VO stands for value objects)
     */

    static private class ShipsVO {
        public String type;
        public List<String> locations;

        public ShipsVO(Ship ship) {
            this.type = ship.getShipType();
            this.locations = ship.getLocation();
        }
    }



    static private class SalvoVO {
        public int turn;
        public String player;
        public List<String> locations;

        public SalvoVO(Salvo salvo) {
            this.turn = salvo.getTurn();
            this.player = salvo.getGamePlayer().getPlayer().getUserName();
            this.locations = salvo.getLocations();
        }
    }

    static private class ScoreVO{
        public Double score;
        public long playerId;
        public String userName;


        public ScoreVO(Score score) {
            this.score = score.getScore();
            playerId = score.getPlayerId();
            userName = score.player.getUserName();
        }
    }




}


