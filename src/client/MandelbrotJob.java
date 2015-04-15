package client;

import java.awt.geom.Point2D;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tasks.TaskEuclideanTsp;
import tasks.TaskMandelbrotSet;
import api.Result;
import api.Space;
import api.Task;

public class MandelbrotJob implements Job {

	private int TASK_STEP = 32;
	private double LOWER_LEFT_X;
	private double LOWER_LEFT_Y;
	private double EDGE_LENGTH;
	private int N_PIXELS;
	private int ITERATION_LIMIT;
	private List<Task> tasks;
	private Integer[][] RESULT;
	private Map<String, Point2D> identifierMap;

	public MandelbrotJob(double LOWER_LEFT_X, double LOWER_LEFT_Y,
			double EDGE_LENGTH, int N_PIXELS, int ITERATION_LIMIT) {
		this.LOWER_LEFT_X = LOWER_LEFT_X;
		this.LOWER_LEFT_Y = LOWER_LEFT_Y;
		this.EDGE_LENGTH = EDGE_LENGTH;
		this.N_PIXELS = N_PIXELS;
		this.ITERATION_LIMIT = ITERATION_LIMIT;
		this.RESULT = new Integer[N_PIXELS][N_PIXELS];
		this.identifierMap = new HashMap<String, Point2D>();
		

	}

	@Override
	public void generateTasks(Space space) throws RemoteException {

	
		int i = 0;
		int j = 0;
		double step = EDGE_LENGTH / N_PIXELS;
		for (double x = LOWER_LEFT_X; i < N_PIXELS; x += step * TASK_STEP, i += TASK_STEP) {
			j = 0;
			for (double y = LOWER_LEFT_Y; j < N_PIXELS; y += step * TASK_STEP, j += TASK_STEP) {
				String identifier = i+","+j;
				Task<Integer[][]> mandelbrotSetTask = new TaskMandelbrotSet(x,
						y, step, TASK_STEP, ITERATION_LIMIT, identifier);

				
				identifierMap.put(identifier, new Point2D.Double(i,j));

				tasks.add(mandelbrotSetTask);
			}
		}
		space.putAll(tasks);
		System.out.println("der");

	}

	@Override
	public void getResults(Space space) throws RemoteException {
	
		for(int i = 0; i < identifierMap.size(); i++){
			Result<Integer[][]> results = space.take();
			
			Integer[][] values = results.getTaskReturnValue();
			String identifier = results.getTaskIdentifier();
			Point2D point = identifierMap.get(identifier);
			double start_x = new Double(point.getX());
			double start_y = new Double(point.getY());
			
			for (int r = 0; r < values.length; r++) {
				for (int c = 0; c < values[0].length; c++) {
					int x = new Double(r + start_x).intValue();
					int y = new Double(N_PIXELS - 1 - (c + start_y)).intValue();
					RESULT[x][y] = values [r][c];			
				}
			}

			
			
		}
	}

	@Override
	public Integer[][] getAllResults() {
		System.out.println(RESULT);
		return RESULT;
	}

}
