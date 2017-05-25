package ua.com.blackjack;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Player implements Runnable {

    private int serverPort;
    private String serverName;
    private Socket socket;
    private Console console;
    private DataOutputStream output;
    private DataInputStream input;

    public Player() {
        Configuration configuration = new Configuration();
        serverPort = configuration.getPort();
        serverName = configuration.getServerName();
        console = new Console();
    }

    @Override
    public void run() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(serverName);
        } catch (UnknownHostException e) {
            console.write(e.getMessage());
        }
        try {
            socket = new Socket(inetAddress, serverPort);
        } catch (IOException e) {
            console.write(e.getMessage());
            System.exit(-1);
        }
        getStreams();
        gameRun();
    }

    private void getStreams() {
        try {
            output = new DataOutputStream(socket.getOutputStream());
            output.flush();
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            console.write(e.getMessage());
        }
    }

    private void gameRun() {
        while (true) {
            try {
                if (input.available() > 0) {
                    String data = input.readUTF();
                    if (data.equals("getData")) {
                        sendData();
                    } else if (data.equals("gameOver")) {
                        System.exit(0);
                    } else {
                        console.write(data);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendData() {
        try {
            output.writeUTF(console.read());
            output.flush();
        } catch (IOException e) {
            console.write(e.getMessage());
        }
    }
}
