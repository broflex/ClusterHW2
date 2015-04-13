package client;

import java.rmi.RemoteException;

import api.Space;

public interface Job {
	
	void generateTasks(Space space) throws RemoteException;
	
	void getResults(Space space) throws RemoteException;

	Object getAllResults();
	
}
