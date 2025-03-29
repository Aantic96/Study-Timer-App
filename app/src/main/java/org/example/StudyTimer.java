package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StudyTimer extends JPanel{

    private int seconds = 0;
    private Timer timer;
    private JLabel timeLabel;
    private JButton startButton, pauseButton, exportButton;
    private boolean isRunning = false;
    private int buttonSize = 50;
    
    public StudyTimer() {
        setBackground(Color.DARK_GRAY);

        timeLabel = new JLabel("Time: 00:00:00");
        startButton = new JButton();
        pauseButton = new JButton();
        exportButton = new JButton("Export");

        styleTextTimer(timeLabel);

        styleButton(startButton, "src/main/resources/icons/play.png", buttonSize);
        styleButton(pauseButton, "src/main/resources/icons/pause.png", buttonSize);

        add(timeLabel);
        add(startButton);
        add(pauseButton);
        add(exportButton);

        startButton.addActionListener(e -> startTimer());
        pauseButton.addActionListener(e -> pauseTimer());
        exportButton.addActionListener(e -> exportTime());
    }

    private void styleTextTimer(JLabel timeLabel) {
        timeLabel.setForeground(Color.YELLOW);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
    }

    private void styleButton(JButton button, String iconPath, int buttonSize) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(buttonSize - 10, buttonSize - 10, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(newImg));
    
        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
    
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }
    

    private String formatTime() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        return String.format("Time: %02d:%02d:%02d", hours, minutes, secs);
    }

    private void startTimer() {
        if(!isRunning) {
            isRunning = true;
            timer = new Timer();
            timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        seconds++;
                        SwingUtilities.invokeLater(() -> timeLabel.setText(formatTime()));
                    }

                }, 1000, 1000
            );
        }
    }

    private void pauseTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            isRunning = false;
        }

    }

    private void exportTime() {

    }
}
