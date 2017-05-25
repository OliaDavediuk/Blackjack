package ua.com.blackjack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private Console console;
    private DataOutputStream output;
    private DataInputStream input;

    public Server() {
        Configuration configuration = new Configuration();
        port = configuration.getPort();
        console = new Console();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            console.write("Wait for connection...");

        } catch (IOException e) {
            console.write("Can't start server. " + e.getMessage());
            System.exit(-1);
        }

        try {
            socket = serverSocket.accept();
            console.write("Connection successful");
        } catch (IOException e) {
            console.write(e.getMessage());
        }

        getStreams();
    }

    private void getStreams() {
        try {
            output = new DataOutputStream(socket.getOutputStream());
            output.flush();
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            console.write(e.getMessage() + " " + e.getCause());
        }
    }

    public void sendData(String message) {
        if (socket.isConnected()) {
            try {
                output.writeUTF(message);
                output.flush();
            } catch (IOException e) {
                console.write("Can't send data to client. " + e.getMessage());
            }
        }
    }

    public String readData() {
        String data = null;
        try {
            data = input.readUTF();
        } catch (IOException e) {
            console.write("Can't read data from client. " + e.getMessage());
        }
        return data;
    }



}
