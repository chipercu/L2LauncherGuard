package com.fuzzy.l2launcherguard.network;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by a.kiperku
 * Date: 28.08.2023
 */

public class ServerStatus extends ScheduledService<ServerStatus> {

    private static final int checkInterval = 2000;
    private boolean isConnected;
    private Label welcomeText;



    public ServerStatus(Label welcomeText) {
        this.welcomeText = welcomeText;
        this.setDelay(Duration.millis(checkInterval));
        this.setPeriod(Duration.millis(checkInterval));
        this.start();
    }

    public static void printInputStream(InputStream is) throws IOException {
        System.setProperty("file.encoding", "UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        try {
            for (;;) {
                String line = br.readLine();
                if (line == null) break;
                System.out.println(line);
            }
        } finally {
            br.close();
        }
    }

    public static boolean checkStatus(String host, int port){
        final Socket socket;
        try {
            socket = new Socket(host, port);
            final InputStream inFromServer = socket.getInputStream();
            final OutputStream outToServer = socket.getOutputStream();
            new Thread(() -> {
                int bytes_read = 64;
                try {
                    byte key = 68;
                    //TODO [FUZZY] шифровка пакета
                    final byte[] cryptRequest = new byte[64];
                    cryptRequest[0] = key;

                    outToServer.write(cryptRequest, 0, bytes_read);
                    outToServer.flush();
                    //TODO CREATE YOUR LOGIC HERE

                } catch (IOException ignored) {
                }
            }).start();
            int bytes_read;
            final byte[] cryptRequest = new byte[1024];
            try {
                while ((bytes_read = inFromServer.read(cryptRequest)) != -1) {
                    if (bytes_read == 204){
                        System.out.println("Подключение установлено");
                        return true;
                    }
                }
                outToServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }



    @Override
    protected Task<ServerStatus> createTask() {
        return new Task<>() {
            @Override
            protected ServerStatus call() {
                Platform.runLater(() -> {
                    ProcessBuilder processBuilder = new ProcessBuilder();
                    processBuilder.command("cmd.exe", "/c", "ping -n 1 google.com");
                    try {
                        Process process = processBuilder.start();
                        printInputStream(process.getInputStream());
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "Windows-1251"));
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            System.out.println(line);
//                        }
                        process.destroy();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                return null;
            }
        };
    }
}
