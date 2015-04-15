package client;

import java.rmi.RemoteException;

import api.Space;

public class MandelbrotJob implements Job{

    private double LOWER_LEFT_X;
    private double LOWER_LEFT_Y;
    private  double EDGE_LENGTH;
    private int N_PIXELS;
    private int ITERATION_LIMIT;

    public MandelbrotJob(double LOWER_LEFT_X, double LOWER_LEFT_Y, double EDGE_LENGTH, int N_PIXELS, int ITERATION_LIMIT) {
        this.LOWER_LEFT_X = LOWER_LEFT_X;
        this.LOWER_LEFT_Y = LOWER_LEFT_Y;
        this.EDGE_LENGTH = EDGE_LENGTH;
        this.N_PIXELS = N_PIXELS;
        this.ITERATION_LIMIT = ITERATION_LIMIT;
    }

    @Override
	public void generateTasks(Space space) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public void getResults(Space space) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer[][] getAllResults() {
		// TODO Auto-generated method stub
		return null;
	}

}
