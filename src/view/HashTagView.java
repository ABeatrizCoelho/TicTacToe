package view;

import java.util.Optional;

import common.Mark;
import model.HashTagModel;
import player.Player;
import util.Console;

public class HashTagView {

    private final HashTagModel model;

    public HashTagView(HashTagModel model) {
        this.model = model;
    }

    static final String aAlertFormat = "\nERRO - %s\nPreste mais atenção!\n";

    public void printError(String message) {
        Console.printf(aAlertFormat, message);
    }

    private Mark mark(int lin, int col) {
        return model.getMark(lin, col);
    }

    public void print() {
        Console.printf(hashFormat,
                mark(0, 0), mark(0, 1), mark(0, 2),
                mark(1, 0), mark(1, 1), mark(1, 2),
                mark(2, 0), mark(2, 1), mark(2, 2));
    }

    public void printGameOver(Optional<Player> winner) {
        Console.println();

        if (winner.isPresent()) {
            Player p = winner.get();
            Console.printf("'%s' Venceu.\n", p.getMark());
        } else {
            Console.println("DEU VÉIA!!!");
        }

        print();
    }

    static final String hashFormat = """
                 |     |
              %s  |  %s  |  %s
            _____|_____|_____
                 |     |
              %s  |  %s  |  %s
            _____|_____|_____
                 |     |
              %s  |  %s  |  %s
                 |     |
            """;
}
