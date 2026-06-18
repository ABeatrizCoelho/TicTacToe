package view;

import model.ScoreModel;

public class ScoreView {

    public void print(ScoreModel score) {
        System.out.println("\n=======================");
        System.out.println("        PLACAR");
        System.out.println("=======================");
        System.out.println("Jogador X: " + score.scoreX());
        System.out.println("Jogador O: " + score.scoreO());
        System.out.println("Empates  : " + score.getDraw());
        System.out.println("=======================\n");
    }
}
