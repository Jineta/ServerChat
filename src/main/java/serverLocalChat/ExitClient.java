package serverLocalChat;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ExitClient {
    public void exitClient(ArrayList<ClientHandler> clientsList) throws RemoteException;
}
