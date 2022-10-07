package serverLocalChat;

import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server {
    public static final String BIND_CHECK_LOGIN = "server.checkLogin";
    static final int SERVER_PORT = 4343;
    private ArrayList<ClientHandler> clientsList = new ArrayList<ClientHandler>();


    public Server() {

        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            while (true) {
                // accept() будет ждать пока кто-нибудь не захочет подключиться  и когда это происходит возвращает объект типа Socket, то есть воссозданный клиентский сокет
                // установив связь и воссоздав сокет для общения с клиентом можно перейти к созданию потоков ввода/вывода.
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                Thread clientThread = new Thread(clientHandler);

                clientsList.add(clientHandler);
                clientThread.start();
                System.out.println("got connection");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendEveryone(String message) {
        for (ClientHandler ch : clientsList) {
            ch.sendToClient(message);
        }
    }

    public static void main(String[] args) throws Exception {

        final RemoteServerImpl service = new RemoteServerImpl();  //создание объекта для удаленного доступа
        //создание реестра расшареных объектов
        final Registry registry = LocateRegistry.createRegistry(4344);
        //создание "заглушки" – приемника удаленных вызовов
        Remote stub = UnicastRemoteObject.exportObject(service, 0);
        //регистрация "заглушки" в реесте
        registry.bind(BIND_CHECK_LOGIN, stub);
        //усыпляем главный поток, иначе программа завершится
        Thread.sleep(Integer.MAX_VALUE);
        Server server = new Server();
    }
}

