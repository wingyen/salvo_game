package com.project.salvo;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

   private long id;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "player_id")
   private Player player;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "game_id")
   private Game game;

   private Date createdTime;

   public GamePlayer () {}

   @OneToMany(mappedBy ="gamePlayer",fetch = FetchType.EAGER)
   Set<Ship> ships = new HashSet<>();

    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        ships.add(ship);
    }

   @OneToMany(mappedBy ="gamePlayer", fetch = FetchType.EAGER)
   Set<Salvo> salvos = new HashSet<>();

   public void addSalvo(Salvo salvo) {
       salvo.setGamePlayer(this);
       salvos.add(salvo);
   }

   public GamePlayer(Player player, Game game) {
       this.player = player;
       this.game = game;
       this.createdTime = new Date();

   }


   public Player getPlayer() {
       return player;
   }

   public Game getGame() {
       return game;
   }

    public void setPlayer(Player player) {
       this.player = player;
    }

    public void setGame(Game game) {
       this.game = game;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public long getId() {
        return id;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public Score getGameScore() {
       return player.getScore(game);
    }

}
