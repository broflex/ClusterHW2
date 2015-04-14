package system;

import java.rmi.RemoteException;

import api.Result;
import api.Space;
import api.Task;

public class ComputerProxy implements Runnable{

	private Computer computer;
	private Space space;
	private Thread t;
	
	public ComputerProxy(Computer computer, SpaceImpl space){
		this.computer = computer;
		this.space = space;
		t = new Thread(this, "ComputerProxy");
		t.start();
	}
	
	@Override
	public void run() {

		Task<?> task = null;
		while(true){
			try{
				task = space.takeTask();
				Result<?> result = (Result<?>) computer.execute(task);
				space.putResult(result);
				
			}catch(RemoteException e){
				System.err.println("Remote Exception in thread: " + this.t.getName());
			}
			catch(InterruptedException e){
				System.err.println("Interrupted Exception in thread: " + this.t.getName());
			}
		}
	}

	
	
}
