package gui;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainMenuFrame extends JFrame {

    private JTextField txtPlayer1;
    private JTextField txtPlayer2;

    private JRadioButton rbHumanVsHuman;
    private JRadioButton rbHumanVsAI;

    private JRadioButton rbX;
    private JRadioButton rbO;

    public MainMenuFrame() {

        super("Tic Tac Toe");

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createScreen();

        setVisible(true);
    }

    private void createScreen() {

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));

        panel.add(new JLabel("Jogador 1"));
        txtPlayer1 = new JTextField("Jogador 1");
        panel.add(txtPlayer1);

        panel.add(new JLabel("Jogador 2"));
        txtPlayer2 = new JTextField("Jogador 2");
        panel.add(txtPlayer2);

        rbHumanVsHuman = new JRadioButton("Humano x Humano", true);
        rbHumanVsAI = new JRadioButton("Humano x Alienígena");

        ButtonGroup groupMode = new ButtonGroup();
        groupMode.add(rbHumanVsHuman);
        groupMode.add(rbHumanVsAI);

        panel.add(rbHumanVsHuman);
        panel.add(rbHumanVsAI);

        rbX = new JRadioButton("X", true);
        rbO = new JRadioButton("O");

        ButtonGroup groupMark = new ButtonGroup();
        groupMark.add(rbX);
        groupMark.add(rbO);

        panel.add(rbX);
        panel.add(rbO);

        JButton btnStart = new JButton("Iniciar");

        btnStart.addActionListener(e -> startGame());

        add(panel);
        add(btnStart, "South");

        rbHumanVsAI.addActionListener(e -> {
            txtPlayer2.setText("Alienígena");
            txtPlayer2.setEnabled(false);
        });

        rbHumanVsHuman.addActionListener(e -> {
            txtPlayer2.setEnabled(true);
            txtPlayer2.setText("Jogador 2");
        });
    }

    private void startGame() {

        String p1 = txtPlayer1.getText().trim();

        String p2 = txtPlayer2.getText().trim();

        boolean vsAI = rbHumanVsAI.isSelected();

        boolean player1IsX = rbX.isSelected();

        dispose();

        new GameFrame(
                p1,
                p2,
                vsAI,
                player1IsX);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                MainMenuFrame::new);
    }
}