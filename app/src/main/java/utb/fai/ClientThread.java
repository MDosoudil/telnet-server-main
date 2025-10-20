package utb.fai;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

    private Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                String cmd = line.trim().toLowerCase();
                if ("exit".equals(cmd) || "quit".equals(cmd)) {
                    out.println("Bye.");
                    break;
                }
                out.println(line);
            }
        } catch (IOException e) {}
    }
}
