package utb.fai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) {
        int port = 12345;
        int max_threads = 0;
       
        port = Integer.parseInt(args[0]);
        max_threads = Integer.parseInt(args[1]);

        ExecutorService pool = null;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            if (max_threads > 0) {
                pool = Executors.newFixedThreadPool(max_threads);
            }
            while (true) {
                Socket client = serverSocket.accept();
                if (pool != null) {
                    final Socket s = client;
                    pool.execute(() -> new ClientThread(s).run());
                } else {
                    new ClientThread(client).start();
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            if (pool != null) {
                pool.shutdown();
            }
        }
    }
}
