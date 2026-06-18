package gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.BestPlayersModel;

public class RankingDialog extends JDialog {

    public RankingDialog(
            java.awt.Frame owner,
            BestPlayersModel ranking) {

        super(owner,
                "Ranking",
                true);

        String[] cols = {
                "Nome",
                "Vitórias",
                "Derrotas",
                "Empates"
        };

        Object[][] data = ranking.asStream()
                .map(p -> new Object[] {
                        p.name(),
                        p.wonTimes(),
                        p.lostTimes(),
                        p.drawTimes()
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, cols);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER);

        setSize(500, 300);

        setLocationRelativeTo(owner);
    }
}