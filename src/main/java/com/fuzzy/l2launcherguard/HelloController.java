package com.fuzzy.l2launcherguard;

import com.fuzzy.l2launcherguard.model.L2Client;
import com.fuzzy.l2launcherguard.network.ServerStatus;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloController {


    private static List<L2Client> l2Clients = new ArrayList<>();

    private static ScheduledService<?> service;
    private static ScheduledService<?> serverStatus;





    @FXML
    public Label welcomeText;


    @FXML
    protected void onHelloButtonClick() throws IOException {




        Process process = new ProcessBuilder("C:\\Program Files (x86)\\HF\\system\\l2.exe").start();
        l2Clients.add(new L2Client(process));
        welcomeText.setText("started new client..." + process.pid());

        service = new ScheduledService<>() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected String call() {
                        Platform.runLater(() -> {
                            int count = 0;
                            final List<L2Client> temp = new ArrayList<>(l2Clients);
                            for (L2Client client: temp){
                                if (client.isAlive()){
                                    count++;
                                }else {
                                    l2Clients.remove(client);
                                }
                            }
                            welcomeText.setText("Current client:" + count);
                        });
                        return "Starting...";
                    }
                };
            }
        };
        service.setDelay(Duration.seconds(2));
        service.setPeriod(Duration.seconds(1));
        service.start();

    }

    @FXML
    void initialize() {
        serverStatus = new ServerStatus(welcomeText);
    }



}