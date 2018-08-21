package com.project.salvo;


import javax.persistence.*;
import java.util.*;

@Entity
public class Game {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   private Date createdTime;

   public Game () {
      setCreatedTime(new Date());
   }



    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    public void addGamePlayer(GamePlayer gamePlayer) {
      gamePlayer.setGame(this);
      gamePlayers.add(gamePlayer);
    }

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Score> scores;

    public void addScore(Score score){
        score.setGame(this);
        scores.add(score);
    }

    public long getId() {
        return id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public List<Set<Salvo>> getSalvosList(){
          List salvosList = new ArrayList();
          gamePlayers.stream().forEach(gamePlayer -> salvosList.add(gamePlayer.getSalvos()));

          return salvosList;
    }



    public void setId(long id) {
        this.id = id;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }
}
