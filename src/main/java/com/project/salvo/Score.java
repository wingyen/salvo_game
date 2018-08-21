package com.project.salvo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public Double score;

    public Date createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    public Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    public Game game;

    public Score() { }

    public Score(Player player, Game game,Double score) {
        this.player = player;
        this.game = game;
        this.score = score;
        this.createdTime = new Date();
    }

    public long getGameId() {
        return game.getId();
    }

    public long getPlayerId() {
        return player.getId();
    }

    public long getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }
}
