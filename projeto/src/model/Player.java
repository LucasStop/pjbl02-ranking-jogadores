package model;

public class Player {
    private String nickname;
    private int ranking;

    public Player(String nickname, int ranking) {
        this.nickname = nickname;
        this.ranking = ranking;
    }

    public String getNickname() {
        return nickname;
    }

    public int getRanking() {
        return ranking;
    }

    @Override
    public String toString() {
        return nickname + " (" + ranking + ")";
    }
}
