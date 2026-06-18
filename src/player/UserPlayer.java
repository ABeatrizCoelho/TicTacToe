package player;

import model.HashTagModel;
import util.Console;

public class UserPlayer extends AbstractPlayer {

    public UserPlayer(HashTagModel hashTag) {
        super(hashTag);
    }

    @Override
    public void play() {
        Console.printf("\nSua Vez '%s'\n", myMark);

        int lin = Console.readInt("L: ");
        int col = Console.readInt("C: ");

        hashTag.setMark(lin, col, myMark);
    }
}
