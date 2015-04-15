package system;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import api.Result;
import api.Task;

public class ComputerImpl<T> extends UnicastRemoteObject implements Computer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComputerImpl() throws RemoteException {
		super();
	}

	@Override
	public Result<T> execute(Task t) throws RemoteException {
		long begin = System.currentTimeMillis();
		T value = (T) t.call();
		long end = System.currentTimeMillis();
		long time = end - begin;
		Result<T> r = new Result<T>(value, time);
		return r;
	}
	
	public static void main(String[] args){
		String spaceHost = args[0];
		
		if (System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		
		try{
			Computer c = new ComputerImpl();
			
			ComputerSpaceInterface space = (ComputerSpaceInterface) Naming.lookup("//" + spaceHost +"/");
			space.register(c);
			
			System.out.println("Computer ready");	
		}
		catch (RemoteException e) {
			System.err.println("ComputerImpl exception : ");
			e.printStackTrace();
		}
		catch (MalformedURLException e) {
			System.err.println("ComputerImpl exception : ");
			e.printStackTrace();
		}
		catch (NotBoundException e) {
			System.err.println("ComputerImpl exception : ");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.err.println("ComputerImpl exception : ");
			e.printStackTrace();
		}
		
	}
	
}
