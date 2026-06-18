package player;

import model.HashTagModel;
import common.Mark;

public class VirtualPlayer extends AbstractPlayer {

    public VirtualPlayer(HashTagModel hashTag) {
        super(hashTag);
        this.setName("Alienígena");
    }

    @Override
    public void play() {

        if (playToWin())
            return;

        if (playToNotLose())
            return;

        if (playStrategy())
            return;

        playRandom();
    }


    private void playRandom() {

        int lin = ((int)(Math.random() * 10)) % 3;

        for (int i = 0; i < 3; i++, lin = (++lin % 3)) {

            int col = ((int)(Math.random() * 10)) % 3;

            for (int j = 0; j < 3; j++, col = (++col % 3)) {

                if (hashTag.isBlank(lin, col)) {
                    hashTag.setMark(lin, col, myMark);
                    return;
                }

            }
        }
    }


    private boolean playToWin() {

        for (int lin = 0; lin < 3; lin++) {
            for (int col = 0; col < 3; col++) {

                if (iCanWin(lin, col)) {
                    hashTag.setMark(lin, col, myMark);
                    return true;
                }

            }
        }
        return false;
    }

    private boolean playToNotLose() {

        Mark enemy = (myMark == Mark.X ? Mark.O : Mark.X);

        for (int lin = 0; lin < 3; lin++) {
            for (int col = 0; col < 3; col++) {

                if (iCanWin(enemy, lin, col)) {
                    hashTag.setMark(lin, col, myMark);
                    return true;
                }

            }
        }
        return false;
    }


    private boolean playStrategy() {
        return false;
    }


    private boolean iCanWin(int lin, int col) {
        return canWin(myMark, lin, col);
    }


    private boolean iCanWin(Mark mark, int lin, int col) {
        return canWin(mark, lin, col);
    }

    private boolean canWin(Mark mark, int lin, int col) {


        if (!hashTag.isBlank(lin, col))
            return false;


        boolean ok = true;
        for (int c = 0; c < 3; c++) {
            if (c == col)
                continue;
            if (hashTag.getMark(lin, c) != mark)
                ok = false;
        }
        if (ok) return true;


        ok = true;
        for (int l = 0; l < 3; l++) {
            if (l == lin)
                continue;
            if (hashTag.getMark(l, col) != mark)
                ok = false;
        }
        if (ok) return true;


        if (lin == col) {
            ok = true;
            for (int i = 0; i < 3; i++) {
                if (i == lin)
                    continue;
                if (hashTag.getMark(i, i) != mark)
                    ok = false;
            }
            if (ok) return true;
        }


        if (lin + col == 2) {
            ok = true;
            for (int i = 0; i < 3; i++) {
                int j = 2 - i;
                if (i == lin)
                    continue;
                if (hashTag.getMark(i, j) != mark)
                    ok = false;
            }
            if (ok) return true;
        }

        return false;
    }

}
