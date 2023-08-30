package com.fuzzy.l2launcherguard.network;

import com.fuzzy.l2launcherguard.crypt.Crypto;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by a.kiperku
 * Date: 28.08.2023
 */

public class ServerStatus extends ScheduledService {

    private static final int checkInterval = 2000;
    private boolean isConnected = false;
    private Crypto crypto;

    private static final String connect_key = "connect_key";

    private static String host = "127.0.0.1";
    private static int remoteport = 2107;
    private static int localport = 2106;



    public ServerStatus() {
        this.setDelay(Duration.millis(checkInterval));
        this.setPeriod(Duration.millis(checkInterval));
        this.start();
        this.crypto = new Crypto(Paths.get(this.getClass().getPackage().getName()));
    }

    public boolean connectToServer(){
        try (Socket socket = new Socket(host, remoteport)){
            System.out.println("Проверка состояния сервера");
                isConnected = socket.isConnected();
                return true;
        } catch (Exception e) {
            isConnected = false;
            System.out.println("Ошибка подключения");

        }
        return false;
    }

    public boolean firstConnect(String host, int port) throws GeneralSecurityException, IOException {


//        final byte[] key = crypto.encrypt(connect_key);

        final Socket socket;
        try {
            socket = new Socket(host, port);
            final InputStream inFromServer = socket.getInputStream();
            final OutputStream outToServer = socket.getOutputStream();

            if (crypto.getSecretKey() == null){
                final byte[] bytes = "getKey".getBytes();
                new Thread(() -> {
                    try {
                        outToServer.write(bytes, 0, bytes.length);
                        outToServer.flush();
                        //TODO CREATE YOUR LOGIC HERE
                    } catch (IOException ignored) {
                    }
                }).start();
            }


            AtomicInteger bytes_read = new AtomicInteger();
            final byte[] cryptRequest = new byte[1024];
            new Thread(() -> {
                try {
                    bytes_read.set(inFromServer.read(cryptRequest));
                    //TODO CREATE YOUR LOGIC HERE
                } catch (IOException ignored) {
                }
            }).start();



//            int bytes_read;
//            final byte[] cryptRequest = new byte[1024];
//            try {
//                while ((bytes_read = inFromServer.read(cryptRequest)) != -1) {
//                    if (bytes_read == 204){
//                        System.out.println("Подключение установлено");
//                        return true;
//                    }
//                }
//                outToServer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return false;
    }



    @Override
    protected Task<ServerStatus> createTask() {
        return new Task<>() {
            @Override
            protected ServerStatus call() {
                Platform.runLater(ServerStatus.this::connectToServer);
                return null;
            }
        };
    }

    public boolean isConnected() {
        return isConnected;
    }
}
