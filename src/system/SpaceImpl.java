package system;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import api.Result;
import api.Space;
import api.Task;

public class SpaceImpl extends UnicastRemoteObject implements Space{

	protected SpaceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putAll(List<Task> taskList) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Result take() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exit() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(Computer computer) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}