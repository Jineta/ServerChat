package serverLocalChat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CheckLogin extends Remote {
    public boolean checkLogin(String login, ArrayList<ClientHandler> clientsList) throws RemoteException;

}
