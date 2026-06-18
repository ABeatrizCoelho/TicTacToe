package app;

import common.Mark;
import control.HashTagControl;
import model.BestPlayersModel;
import model.HashTagModel;
import model.ScoreModel;
import player.Player;
import player.UserPlayer;
import player.VirtualPlayer;
import util.Console;
import view.AppView;
import view.BestPlayersView;
import view.HashTagView;
import view.ScoreView;

public class AppTicTacToe {

    public static enum GameType {
        HUMMAN_HUMMAN, HUMMAN_COMPUTER
    }

    private AppView appView = new AppView();
    private HashTagModel hashTagModel;
    private HashTagView hashTagView;
    private HashTagControl hashTagControl;
    private ScoreModel scoreModel;
    private ScoreView scoreView;
    private Player[] vPlayer = new Player[2];

    private static final String HISTORY_FILENAME = "TicTacToeHistory.obj";
    private BestPlayersModel bestPlayersModel;
    private BestPlayersView bestPlayersView;
    // ------------------------------------------------------------

    private AppTicTacToe() {
        createGame();
    }

    private void createGame() {
        hashTagModel = new HashTagModel();
        hashTagView = new HashTagView(hashTagModel);
        scoreModel = new ScoreModel();
        scoreView = new ScoreView();
        hashTagControl = new HashTagControl(hashTagModel, hashTagView, scoreModel, scoreView);

        readOrCreateHistory();
    }

    private void readOrCreateHistory() {
        try {
            bestPlayersModel = BestPlayersModel.readFromFile(HISTORY_FILENAME);
        } catch (Exception e) {
            bestPlayersModel = new BestPlayersModel(10); // top 10 jogadores
        } finally {
            bestPlayersView = new BestPlayersView(bestPlayersModel);
        }
    }

    private void createPlayers(GameType gameType) {

        vPlayer[0] = createHummanPlayer();
        char charMark = Console.readChar("Marca [X,O]:", 'X', 'x', 'O', 'o');
        vPlayer[0].setMark(Mark.valueOf(("" + charMark).toUpperCase()));

        vPlayer[1] = (gameType == GameType.HUMMAN_HUMMAN)
                ? createHummanPlayer()
                : new VirtualPlayer(hashTagModel);

        vPlayer[1].setMark(
                vPlayer[0].getMark() == Mark.X ? Mark.O : Mark.X);

        // Injeta no controller
        hashTagControl.setPlayerA(vPlayer[0]);
        hashTagControl.setPlayerB(vPlayer[1]);
    }

    private Player createHummanPlayer() {
        Player player = new UserPlayer(hashTagModel);
        String name = Console.readLine("Nome: ");
        player.setName(name);
        return player;
    }

    private void updateHistory() {
        Player playerA = vPlayer[0];
        Player playerB = vPlayer[1];

        int scoreA = scoreModel.scoreOf(playerA.getMark());
        int scoreB = scoreModel.scoreOf(playerB.getMark());
        int scoreDraw = scoreModel.scoreOf(Mark.BLANK);

        if (scoreA >= scoreB)
            bestPlayersModel.addBestPlayer(playerA.getName(), scoreA, scoreB, scoreDraw);

        if (scoreB >= scoreA)
            bestPlayersModel.addBestPlayer(playerB.getName(), scoreB, scoreA, scoreDraw);
    }

    private void writeHistory() {
        try {
            bestPlayersModel.writeToFile(HISTORY_FILENAME);
        } catch (Exception e) {
            hashTagView.printError(e.getMessage());
        }
    }

    private void go() {
        appView.showWelcomeMessage();

        GameType gameType = appView.askForGameType();
        createPlayers(gameType);

        do {
            hashTagControl.go();
        } while (appView.askForNewGame());

        updateHistory();

        bestPlayersView.print();

        appView.showGoodbyeMessage();
        Console.readLine("ENTER");

        writeHistory();
    }

    public static void main(String[] args) {
        AppTicTacToe app = new AppTicTacToe();
        app.go();
    }
}
