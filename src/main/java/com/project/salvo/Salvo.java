package com.project.salvo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_salvo")
    public GamePlayer gamePlayer;

    public int turn;

    @ElementCollection
    @Column(name = "salvo_locations")
    public List<String> locations;

    public Salvo() {}

    public Salvo(GamePlayer gamePlayer, int turn, List<String> locations){
        this.gamePlayer = gamePlayer;
        this.turn = turn;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public int getTurn() {
        return turn;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}
