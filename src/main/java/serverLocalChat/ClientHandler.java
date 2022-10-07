package serverLocalChat;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    private Server server;
    private BufferedReader readerFromInputStream; //сообщение, которое сервер считывает с клиента
    private PrintWriter writerToOutputStream; //сообщение, которое сервер отправляет клиенту
    private String login;
    public ClientHandler(Socket clientSocket, Server server) {
        try {
            this.server = server;
            this.clientSocket = clientSocket;
            this.readerFromInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //The getInputStream() returns an input stream for reading bytes from this socket.
            this.writerToOutputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 public String getLogin(){
        return login;
 }
    public void run() {
        String messageToSend;
        try {
            while ((messageToSend = readerFromInputStream.readLine()) != null) {
                System.out.println("read on server " + messageToSend);
                server.sendEveryone(messageToSend);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendToClient(String message) {
        try {
            writerToOutputStream.println(message);
            writerToOutputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}