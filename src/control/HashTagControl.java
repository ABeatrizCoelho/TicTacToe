package control;

import java.util.Optional;

import common.Mark;
import model.HashTagModel;
import model.HashTagModelException;
import model.ScoreModel;
import player.Player;
import view.HashTagView;
import view.ScoreView;

public class HashTagControl {

    private HashTagModel model;
    private HashTagView view;

    private Player playerA;
    private Player playerB;

    private Player startPlayer;

    private Optional<Player> winner = Optional.empty();
    
    private ScoreModel scoreModel;
    private ScoreView scoreView;


    public HashTagControl(HashTagModel model, HashTagView view,
            ScoreModel scoreModel, ScoreView scoreView) {
    	this.model = model;
    	this.view = view;
    	this.scoreModel = scoreModel;
    	this.scoreView = scoreView;
    }


    public void setPlayerA(Player player) {
        this.playerA = player;
    }

    public void setPlayerB(Player player) {
        this.playerB = player;
    }

    public void setStartPlayer(Player player) {
        this.startPlayer = player;
    }


    // ======================================================
    //        REFATORAÇÃO PEDIDA PELO SLIDE
    // ======================================================
    private void doPlay(Player player) {
        while (true) {
            try {
                player.play();
                return;  // jogada válida → sai do loop
            }
            catch (HashTagModelException e) {
                view.printError(e.getMessage());  // imprime mensagem formatada
            }
        }
    }
    // ======================================================


    public void go() {

        gameStart();

        Player currentPlayer = startPlayer;

        while (model.hasBlank()) {

            // Substitui: currentPlayer.play();
            doPlay(currentPlayer);

            view.print();
            checkForWinner();

            if (winner.isPresent())
                break;

            // troca turno
            currentPlayer = (currentPlayer == playerA ? playerB : playerA);
        }

        gameOver();
    }


    private Player getPlayer(Mark mark) {

        if (playerA.getMark() == mark)
            return playerA;

        if (playerB.getMark() == mark)
            return playerB;

        throw new RuntimeException("Nenhum jogador possui a marca: " + mark);
    }

    private void defineStartPlayer() {

        if (winner.isPresent()) {
            startPlayer = winner.get();
            return;
        }

        if (startPlayer != null) {
            startPlayer = (startPlayer == playerA ? playerB : playerA);
            return;
        }

        startPlayer = (Math.random() < 0.5 ? playerA : playerB);
    }

    private void checkForWinner(Mark[] vMark) {

        if (winner.isPresent())
            return;

        for (Mark m : vMark) {
            if (m == Mark.BLANK)
                return;
        }

        if (vMark[0] == vMark[1] && vMark[1] == vMark[2]) {
            winner = Optional.of(getPlayer(vMark[0]));
        }
    }


    private void checkForWinner() {

        for (int lin = 0; lin < 3; lin++) {
            checkForWinner(new Mark[]{
                    model.getMark(lin, 0),
                    model.getMark(lin, 1),
                    model.getMark(lin, 2)
            });
        }

        for (int col = 0; col < 3; col++) {
            checkForWinner(new Mark[]{
                    model.getMark(0, col),
                    model.getMark(1, col),
                    model.getMark(2, col)
            });
        }

        checkForWinner(new Mark[]{
                model.getMark(0, 0),
                model.getMark(1, 1),
                model.getMark(2, 2)
        });

        checkForWinner(new Mark[]{
                model.getMark(0, 2),
                model.getMark(1, 1),
                model.getMark(2, 0)
        });
    }


    private void gameStart() {

        defineStartPlayer();

        model.reset();

        winner = Optional.empty();

        view.print();
    }


    private void gameOver() {

        // Marca do vencedor — se ninguém venceu, é BLANK (DEU VÉIA)
        Mark winnerMark = winner.isPresent()
                ? winner.get().getMark()
                : Mark.BLANK;

        // Apresentar resultado
        view.printGameOver(winner);

        // Incrementar score
        scoreModel.incScore(winnerMark);

        // Mostrar o placar atualizado
        scoreView.print(scoreModel);
    }


}
