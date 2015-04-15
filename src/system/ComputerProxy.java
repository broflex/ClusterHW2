package system;

import java.rmi.RemoteException;
import java.util.Random;

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
		t = new Thread(this, "ComputerProxy_" + getRandomNumber());
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
				e.printStackTrace();
			}
			catch(InterruptedException e){
				System.err.println("Interrupted Exception in thread: " + this.t.getName());
				e.printStackTrace();
			}
		}
	}
	
	public String getRandomNumber(){
		Random rand = new Random();
		int a = rand.nextInt(9);
		String code = Integer.toString(a);
		a = rand.nextInt(9);
		code = code + Integer.toString(a);
		a = rand.nextInt(9);
		code = code + Integer.toString(a);
		return code;
	}

	
	
}
