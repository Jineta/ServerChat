package serverLocalChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RemoteServerImpl  extends UnicastRemoteObject implements CheckLogin, ExitClient  {
    public RemoteServerImpl() throws  RemoteException {}
    public boolean checkLogin(String login, ArrayList<ClientHandler> clientsList) throws RemoteException {
        for (ClientHandler ch : clientsList) {
            if (ch.getLogin().equals(login)){
                return false;
            }
        }
        return true;
    }
public void  exitClient(ArrayList<ClientHandler> clientsList) throws RemoteException {
//TODO
    }
}
