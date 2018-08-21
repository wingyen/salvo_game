package com.project.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    private String userName;
    private String password;

    public Player () {}

    public Player(String email, String passCode) {

        userName = email;
        password = passCode;
    }

    @OneToMany(mappedBy = "player",fetch = EAGER)
    Set<GamePlayer> gamePlayers;

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }

    @OneToMany(mappedBy = "player", fetch = EAGER)
    Set<Score> scores;

    public void addScore(Score score){
        score.setPlayer(this);
        scores.add(score);
    }



    public String getUserName() {

        return userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get scores
     * @param game
     * @return a set of scores
     */
    public Score getScore(Game game) {
        return scores.stream().filter(score -> score.getGame().equals(game)).findFirst().orElse(null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public Set<Score> getScores() {
        return scores;
    }
}
