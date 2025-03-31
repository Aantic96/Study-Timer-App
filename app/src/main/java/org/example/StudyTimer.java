package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class StudyTimer extends JPanel{

    private int seconds = 0;
    private Timer timer;
    private JLabel timeLabel;
    private JButton startButton, pauseButton, exportButton, resetButton;
    private boolean isRunning = false;
    private int buttonSize = 50;
    private JPanel controlsPanel;
    private JPanel exportPanel;
    
    public StudyTimer() {
        setLayout(new BorderLayout());

        controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timeLabel = new JLabel("Time: 00:00:00");
        startButton = new JButton();
        pauseButton = new JButton();
        resetButton = new JButton();
        exportButton = new JButton("Export");

        styleTextTimer(timeLabel);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        styleButton(startButton, "src/main/resources/icons/play.png", buttonSize);
        styleButton(pauseButton, "src/main/resources/icons/pause.png", buttonSize);
        styleButton(resetButton, "src/main/resources/icons/reset.png", buttonSize);

        styleExportButton(exportButton);

        setBackground(Color.DARK_GRAY);
        controlsPanel.setBackground(Color.DARK_GRAY);
        exportPanel.setBackground(Color.DARK_GRAY);

        add(timeLabel, BorderLayout.NORTH);
        add(controlsPanel, BorderLayout.CENTER);
        add(exportPanel, BorderLayout.SOUTH);
        controlsPanel.add(startButton);
        controlsPanel.add(pauseButton);
        controlsPanel.add(resetButton);
        exportPanel.add(exportButton);

        startButton.addActionListener(e -> startTimer());
        pauseButton.addActionListener(e -> pauseTimer());
        resetButton.addActionListener(e -> resetTimer());
        exportButton.addActionListener(e -> exportTime());
    }

    public boolean isRunning() {
        return isRunning;
    }
    
    public String getFormattedTime() {
        return formatTime(seconds);
    }

    protected String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        return String.format("Time: %02d:%02d:%02d", hours, minutes, secs);
    }

    protected void startTimer() {
        if(!isRunning) {
            isRunning = true;
            timer = new Timer();
            timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        seconds++;
                        SwingUtilities.invokeLater(() -> timeLabel.setText(formatTime(seconds)));
                    }

                }, 1000, 1000
            );
        }
    }

    protected void pauseTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            isRunning = false;
        }
    }

    protected void resetTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        isRunning = false;

        seconds = 0;
        timeLabel.setText("Time: 00:00:00");
        timer = new Timer();
    }

    private void styleTextTimer(JLabel timeLabel) {
        timeLabel.setForeground(Color.decode("#fece09"));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 30));
    }

    private void styleButton(JButton button, String iconPath, int buttonSize) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(buttonSize - 10, buttonSize - 10, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(newImg));
    
        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
    
        button.setBackground(Color.decode("#242321"));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
    }
    
    private void styleExportButton(JButton exportButton) {
        exportButton.setForeground(Color.decode("#fece09"));
        exportButton.setBackground(Color.decode("#242321"));
    }

    private void exportTime() {
        ExcelExporter.exportSeconds(seconds);
    }
}
