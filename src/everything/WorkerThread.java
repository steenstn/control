package everything;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerThread
    implements Runnable {

    private Socket clientSocket;
    private String clientName;
    private String clientIp;
    private BufferedReader in;
    private PrintWriter out;

    public WorkerThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        clientName = clientSocket.getInetAddress().getHostName();
        clientIp = clientSocket.getInetAddress().getHostAddress();

    }

    @Override
    public void run() {
        String inputLine;

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("You are connected");
            System.out.println(clientName + " connected.");
            while (true) {
                inputLine = in.readLine();
                if (inputLine != null) {
                    System.out.println("Received command from " + clientName + ": " + inputLine);
                    out.println(inputLine);
                    String[] cmd = { "C:\\a\\test.exe", inputLine };
                    Process p = Runtime.getRuntime().exec(cmd);
                    p.waitFor();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
