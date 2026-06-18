package model;

import common.Mark;

public class ScoreModel {

    private int scoreX;
    private int scoreO;
    private int draw;

    public ScoreModel() {
        reset();
    }

    public int scoreOf(Mark mark) {
        return switch (mark) {
            case O -> this.scoreO;
            case X -> this.scoreX;
            case BLANK -> this.draw;
        };
    }

    public void reset() {
        this.scoreX = 0;
        this.scoreO = 0;
        this.draw = 0;
    }

    public void incScore(Mark mark) {
        if (mark == Mark.X) {
            scoreX++;
        } else if (mark == Mark.O) {
            scoreO++;
        } else {
            draw++;
        }
    }

    public int scoreX() {
        return scoreX;
    }

    public int scoreO() {
        return scoreO;
    }

    public int getDraw() {
        return draw;
    }
}
