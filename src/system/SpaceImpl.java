package system;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import api.Result;
import api.Space;
import api.Task;

public class SpaceImpl extends UnicastRemoteObject implements Space{
	
	private BlockingQueue<Task<?>> taskList;
	private BlockingQueue<Result<?>> resultList;

	protected SpaceImpl() throws RemoteException {
		super();
		taskList = new LinkedBlockingQueue<Task<?>>();
		resultList = new LinkedBlockingQueue<Result<?>>();
	}
	
	public static void main(String[] args){
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		
		try{
			Space space = new SpaceImpl();
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.rebind(SERVICE_NAME, space);
			System.out.println("SpaceImpl.main: Ready.");
			
		}
		catch(Exception e){
			System.err.println("Error in SpaceImpl.main");
			e.printStackTrace();
		}
	}

	@Override
	public void putAll(List<Task> taskList) throws RemoteException {

		taskList.addAll(taskList);
	}

	@Override
	public Result take() throws RemoteException {
		try {
			return (Result) resultList.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void exit() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(Computer computer) throws RemoteException {
		new ComputerProxy(computer, this);		
	}

	@Override
	public Task takeTask() throws RemoteException, InterruptedException {
		return taskList.take();
	}

	@Override
	public void putResult(Result result) throws RemoteException, InterruptedException {
		resultList.put(result);
	}

}
