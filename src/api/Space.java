package api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import system.Computer;

/**
 *
 * @author Peter Cappello
 */
public interface Space extends Remote 
{
    public static int PORT = 1099;
    public static String SERVICE_NAME = "Space";

    void putAll ( List<Task> taskList ) throws RemoteException;

    Result take() throws RemoteException;
    
    Task takeTask() throws RemoteException, InterruptedException;
    
    void putResult(Result<?> result) throws RemoteException, InterruptedException;

    void exit() throws RemoteException;
    
    void register( Computer computer ) throws RemoteException;
}