package org.example;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class StudyTimerApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("StudyTimer");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setTitle("Study Timer");

            StudyTimer studyTimer = new StudyTimer();

            frame.add(studyTimer);

            frame.setVisible(true);
        });
    }
}
