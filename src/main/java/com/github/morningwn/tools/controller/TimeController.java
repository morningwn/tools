package com.github.morningwn.tools.controller;

import com.github.morningwn.tools.utils.DateUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author morningwn
 * @create in 2022/10/20 14:27
 */
public class TimeController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeController.class);
    @FXML
    private Label timeLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label weekLabel;
    private Timer timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateTime());
            }
        }, 0, 500);
    }

    private void updateTime() {
        LocalDateTime now = LocalDateTime.now();
        String date = DateUtils.formatDate(now);
        String time = DateUtils.formatTime(now);
        String week = DateUtils.getWeekName(now);
        timeLabel.setText(time);
        dateLabel.setText(date);
        weekLabel.setText(week);
//        LOGGER.debug("updateTime, date: {}, time: {}, week: {}", date, time, week);
    }

}
