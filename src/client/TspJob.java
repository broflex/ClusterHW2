package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tasks.TaskEuclideanTsp;
import api.Result;
import api.Space;
import api.Task;

public class TspJob implements Job{
	
	private double[][] cities;
	private int nrOfTasks;
	private List<Task> tasks;
	private int[] minimalPath;
	
	public TspJob(double[][] cities){
		this.cities = cities;
		this.nrOfTasks = 0;
		tasks = new ArrayList<Task>();
	}

	@Override
	public void generateTasks(Space space) throws RemoteException {
		
		
		int lastCity = 0;
		
		for(int city = lastCity + 1; city < cities.length; city++){
			int firstCity = city;

			String identifier = firstCity +","+lastCity;
			
			Task<int[]> tspTask = new TaskEuclideanTsp(cities,identifier);
			tasks.add(tspTask);
			nrOfTasks++;
//			space.put(tspTask);
		}
		space.putAll(tasks);
		
	}

	@Override
	public void getResults(Space space) throws RemoteException {
		

		long copmuterTime = 0L;
		long clientTime = 0L;
		
		double tempLength = Double.MAX_VALUE;
		
		for(int i = 0; i < tasks.size(); i++){
			Result<int[]> results = (Result<int[]>) space.take();
			long currentTime = System.currentTimeMillis();
						
			int[] path = results.getTaskReturnValue();
			double pathLength = findLength(path);
			
			if(pathLength < tempLength){
				tempLength = pathLength;
				minimalPath = path;
			}
			
		}
		
	}

	@Override
	public int[] getAllResults() {
		return minimalPath;
	}
	
	public double findLength(int[] path){
		double length = 0;
		
		
		for(int i = 0; i < cities.length - 1; i++){
			double x = cities[i][0];
			double y = cities[i][1];
			double nx = cities[i + 1][0];
			double ny = cities[i + 1][1];
			
			double dx = nx - x;
			double dy = ny - y;
			
			length += Math.sqrt(dx*dx + dy*dy);
			
		}
		return length;
	}

}
