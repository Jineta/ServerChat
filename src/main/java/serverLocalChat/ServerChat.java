package serverLocalChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerChat {
    ArrayList clientOutputStreams;
    public static void main(String[] args) {
        new ServerChat().go();
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket) {
            try {
                socket = clientSocket;
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String messageForClients;
            try {
                while ((messageForClients = reader.readLine()) != null) {
                    System.out.println("read on server " + messageForClients);
                    sendEveryone(messageForClients);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }


    public void go() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(4343);

            while (true) {
                // accept() будет ждать пока
                //кто-нибудь не захочет подключиться  и когда это происходит возвращает объект типа Socket, то есть воссозданный клиентский сокет
                // установив связь и воссоздав сокет для общения с клиентом можно перейти
                // к созданию потоков ввода/вывода.
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
            clientOutputStreams.add(out);

            Thread t = new Thread(new ClientHandler(clientSocket));
            t.start();
            System.out.println("got connection");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendEveryone(String message) {
        Iterator iterator = clientOutputStreams.iterator();
        while ((iterator.hasNext())){
            try{
                PrintWriter writer = (PrintWriter) iterator.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}

