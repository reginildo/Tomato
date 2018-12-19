package io.github.reginildo.tomato.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.InputStream;

public class Sounds extends Thread {
    private String[] audioList = new String[2];
    public Player player;

    public Sounds() {
        audioList[0] = "clock-ringing.mp3";
        audioList[1] = "ticking-noise.mp3";
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(
                    "/io/github.reginildo/tomato/audio/" + audioList[1]);
            player = new Player(inputStream);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
