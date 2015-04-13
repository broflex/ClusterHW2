package system;

import java.rmi.Remote;
import java.rmi.RemoteException;
import api.Task;

/**
 * This interface exposes the methods that can be called by the client.
 * @author Samson Svendsen, Simen Aakhus, Eivind Kristoffersen, Anders Kristiansen
 *
 */
public interface Computer extends Remote{
	 
	public static String SERVICE_NAME = "ComputerService";
	public static int PORT = 1099;
	
	/**
	 * This remote method executes a certain task on the compute server
	 * @param t the task that should be executed
	 * @return returns a task T
	 * @throws RemoteException can throw RemoteException
	 */
	<T> T execute(Task<T> t) throws RemoteException;
	
}
