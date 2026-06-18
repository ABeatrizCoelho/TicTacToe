package view;

import model.BestPlayersModel;
import util.Console;

public class BestPlayersView {

    private final BestPlayersModel model;

    public BestPlayersView(BestPlayersModel model) {
        super();
        this.model = model;
    }

    public void print() {

        Console.println("""
                ************************************************
                |          G R A N D E S  -  V E N C E D O R E S         |
                |----------------------------------------------|
                | Nome                 Venceu Perdeu Empate    |
                |----------------------------------------------|
                """);

        String format = "| %-20s %6d %6d %6d |\n";

        model.asStream().forEach(bp -> {
            String name = bp.name();

            if (name.length() > 20)
                name = name.substring(0, 20);

            Console.printf(format,
                    name,
                    bp.wonTimes(),
                    bp.lostTimes(),
                    bp.drawTimes());
        });

        Console.println("************************************************");
    }

}
