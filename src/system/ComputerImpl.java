package system;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import api.Task;

public class ComputerImpl extends UnicastRemoteObject implements Computer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ComputerImpl() throws RemoteException {
		super();
	}

	@Override
	public <T> T execute(Task<T> t) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args){
		
	}
	
	
	
	

}
