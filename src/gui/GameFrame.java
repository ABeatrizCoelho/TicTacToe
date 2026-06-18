package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.Mark;
import model.HashTagModel;
import model.ScoreModel;
import player.VirtualPlayer;

import model.BestPlayersModel;

public class GameFrame extends JFrame {

    private final JButton[][] buttons = new JButton[3][3];

    private final HashTagModel model = new HashTagModel();
    private final ScoreModel scoreModel = new ScoreModel();

    private VirtualPlayer computer;

    private String player1Name;
    private String player2Name;

    private boolean vsAI;

    private Mark player1Mark;
    private Mark player2Mark;

    private Mark currentTurn;

    private JLabel lblTurn;

    private JLabel lblScore1;
    private JLabel lblScore2;
    private JLabel lblDraw;

    private BestPlayersModel ranking;
    private static final String HISTORY_FILE = "TicTacToeHistory.obj";

    public GameFrame(
            String player1Name,
            String player2Name,
            boolean vsAI,
            boolean player1IsX) {

        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.vsAI = vsAI;

        this.player1Mark = player1IsX ? Mark.X : Mark.O;
        this.player2Mark = player1IsX ? Mark.O : Mark.X;

        currentTurn = Mark.X;

        try {
            ranking = BestPlayersModel.readFromFile(
                    HISTORY_FILE);
        } catch (Exception e) {
            ranking = new BestPlayersModel(10);
        }

        if (vsAI) {
            computer = new VirtualPlayer(model);
            computer.setMark(player2Mark);
        }

        setTitle("Tic Tac Toe");
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createScreen();

        setVisible(true);
    }

    private void createScreen() {

        setLayout(new BorderLayout());

        lblTurn = new JLabel("Turno: " + currentPlayerName());
        lblTurn.setFont(new Font("Arial", Font.BOLD, 22));

        add(lblTurn, BorderLayout.NORTH);

        JPanel board = new JPanel(new GridLayout(3, 3));

        for (int lin = 0; lin < 3; lin++) {

            for (int col = 0; col < 3; col++) {

                JButton btn = new JButton("");

                btn.setFont(new Font("Arial", Font.BOLD, 50));

                int l = lin;
                int c = col;

                btn.addActionListener(e -> play(l, c));

                buttons[lin][col] = btn;

                board.add(btn);
            }
        }

        add(board, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel(new GridLayout(3, 1));

        lblScore1 = new JLabel(player1Name + ": 0");
        lblScore2 = new JLabel(player2Name + ": 0");
        lblDraw = new JLabel("Empates: 0");

        scorePanel.add(lblScore1);
        scorePanel.add(lblScore2);
        scorePanel.add(lblDraw);

        add(scorePanel, BorderLayout.EAST);

        JButton btnNewGame = new JButton("Nova Partida");

        JButton btnRanking = new JButton("Ranking");

        JPanel bottom = new JPanel();

        bottom.add(btnNewGame);
        bottom.add(btnRanking);

        btnNewGame.addActionListener(e -> newGame());

        btnRanking.addActionListener(e -> {

            RankingDialog dlg = new RankingDialog(
                    this,
                    ranking);

            dlg.setVisible(true);
        });

        add(bottom,
                BorderLayout.SOUTH);
    }

    private String currentPlayerName() {

        if (currentTurn == player1Mark)
            return player1Name;

        return player2Name;
    }

    private void play(int lin, int col) {

        if (!model.isBlank(lin, col))
            return;

        model.setMark(lin, col, currentTurn);

        refreshBoard();

        if (checkWinner(currentTurn)) {
            return;
        }

        if (!model.hasBlank()) {
            drawGame();
            return;
        }

        currentTurn = currentTurn == Mark.X
                ? Mark.O
                : Mark.X;

        lblTurn.setText(
                "Turno: " + currentPlayerName());

        if (vsAI && currentTurn == player2Mark) {

            computer.play();

            refreshBoard();

            if (checkWinner(player2Mark))
                return;

            if (!model.hasBlank()) {
                drawGame();
                return;
            }

            currentTurn = player1Mark;

            lblTurn.setText(
                    "Turno: " + currentPlayerName());
        }
    }

    private void refreshBoard() {

        for (int lin = 0; lin < 3; lin++) {

            for (int col = 0; col < 3; col++) {

                Mark mark = model.getMark(lin, col);

                buttons[lin][col].setText(
                        mark == Mark.BLANK
                                ? ""
                                : mark.toString());
            }
        }
    }

    private boolean checkWinner(Mark mark) {

        for (int i = 0; i < 3; i++) {

            if (model.getMark(i, 0) == mark &&
                    model.getMark(i, 1) == mark &&
                    model.getMark(i, 2) == mark) {

                winner(mark);
                return true;
            }

            if (model.getMark(0, i) == mark &&
                    model.getMark(1, i) == mark &&
                    model.getMark(2, i) == mark) {

                winner(mark);
                return true;
            }
        }

        if (model.getMark(0, 0) == mark &&
                model.getMark(1, 1) == mark &&
                model.getMark(2, 2) == mark) {

            winner(mark);
            return true;
        }

        if (model.getMark(0, 2) == mark &&
                model.getMark(1, 1) == mark &&
                model.getMark(2, 0) == mark) {

            winner(mark);
            return true;
        }

        return false;
    }

    private void winner(Mark mark) {

        disableBoard();

        scoreModel.incScore(mark);

        if (mark == player1Mark) {

            ranking.addBestPlayer(
                    player1Name,
                    scoreModel.scoreOf(player1Mark),
                    scoreModel.scoreOf(player2Mark),
                    scoreModel.getDraw());

        } else {

            ranking.addBestPlayer(
                    player2Name,
                    scoreModel.scoreOf(player2Mark),
                    scoreModel.scoreOf(player1Mark),
                    scoreModel.getDraw());
        }

        saveRanking();

        updateScore();

        String winnerName = mark == player1Mark
                ? player1Name
                : player2Name;

        JOptionPane.showMessageDialog(
                this,
                winnerName + " venceu!");
    }

    private void drawGame() {

        disableBoard();

        scoreModel.incScore(Mark.BLANK);

        updateScore();

        JOptionPane.showMessageDialog(
                this,
                "Deu velha!");
    }

    private void updateScore() {

        lblScore1.setText(
                player1Name + ": " +
                        scoreModel.scoreOf(player1Mark));

        lblScore2.setText(
                player2Name + ": " +
                        scoreModel.scoreOf(player2Mark));

        lblDraw.setText(
                "Empates: " +
                        scoreModel.getDraw());
    }

    private void disableBoard() {

        for (int lin = 0; lin < 3; lin++) {

            for (int col = 0; col < 3; col++) {

                buttons[lin][col]
                        .setEnabled(false);
            }
        }
    }

    private void newGame() {

        model.reset();

        currentTurn = Mark.X;

        for (int lin = 0; lin < 3; lin++) {

            for (int col = 0; col < 3; col++) {

                buttons[lin][col]
                        .setText("");

                buttons[lin][col]
                        .setEnabled(true);
            }
        }

        lblTurn.setText(
                "Turno: " + currentPlayerName());
    }

    private void saveRanking() {

        try {

            ranking.writeToFile(
                    HISTORY_FILE);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}