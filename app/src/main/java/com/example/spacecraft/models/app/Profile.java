package com.example.spacecraft.models.app;

public class Profile {
    private long id;
    private String username;
    private int highestScore;

    public Profile() {
    }

    public Profile( String username, int highestScore) {
        this.username = username;
        this.highestScore = highestScore;
    }

    public Profile(long id, String username, int highestScore) {
        this.id = id;
        this.username = username;
        this.highestScore = highestScore;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }
}
