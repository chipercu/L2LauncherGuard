package com.fuzzy.l2launcherguard;

import com.fuzzy.l2launcherguard.model.L2Client;
import com.fuzzy.l2launcherguard.network.ServerStatus;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class HelloController {


    private static List<L2Client> l2Clients = new ArrayList<>();
    private static ServerStatus serverStatus;
    private static ScheduledService<Object> connectedScheduled;


    @FXML
    public Label welcomeText;

    @FXML
    private Button startGameButton;

    @FXML
    void onStartGameButtonClick(ActionEvent event) {

    }

    @FXML
    protected void onHelloButtonClick() {

        if (serverStatus == null){
            serverStatus = new ServerStatus();
            connectedScheduled = new ScheduledService<>() {
                @Override
                protected Task<Object> createTask() {
                    return new Task<>() {
                        @Override
                        protected Object call() {
                            Platform.runLater(()-> {
                                if (serverStatus != null && serverStatus.isConnected()){
                                    welcomeText.setText("Server is online");
                                    startGameButton.setDisable(false);
                                }else {
                                    welcomeText.setText("Server is offline");
                                    startGameButton.setDisable(true);
                                }
                            });
                            return null;
                        }
                    };
                }
            };
            connectedScheduled.setDelay(Duration.millis(1000));
            connectedScheduled.setPeriod(Duration.millis(1000));
            connectedScheduled.start();
        }
//        Process process = new ProcessBuilder("C:\\Program Files (x86)\\HF\\system\\l2.exe").start();
//        l2Clients.add(new L2Client(process));
//        welcomeText.setText("started new client..." + process.pid());
//
//        service = new ScheduledService<>() {
//            @Override
//            protected Task createTask() {
//                return new Task() {
//                    @Override
//                    protected String call() {
//                        Platform.runLater(() -> {
//                            int count = 0;
//                            final List<L2Client> temp = new ArrayList<>(l2Clients);
//                            for (L2Client client: temp){
//                                if (client.isAlive()){
//                                    count++;
//                                }else {
//                                    l2Clients.remove(client);
//                                }
//                            }
//                            welcomeText.setText("Current client:" + count);
//                        });
//                        return "Starting...";
//                    }
//                };
//            }
//        };
//        service.setDelay(Duration.seconds(2));
//        service.setPeriod(Duration.seconds(1));
//        service.start();

    }

    @FXML
    void initialize() {


    }









}