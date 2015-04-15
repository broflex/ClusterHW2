package tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import api.Task;

/**
 * This is an implementation of the task interface that solves the traveling salesman problem
 * @author Samson Svendsen, Simen Aakhus
 *
 */
public class TaskEuclideanTsp implements Task<int[]> {

	double[][] cities;
	
	/**
	 * Constructor that initiates cities
	 * @param cities A 2D double array of cities, where cities[i][0] gives x-coordinate, and cities[i][1] is the y-coordinate.
	 */
	public TaskEuclideanTsp(double[][] cities) {
		this.cities = cities;
	}

	/**
	 * Calculates an adjacency matrix
	 * @param cities A 2D double array of cities, where cities[i][0] gives x-coordinate, and cities[i][1] is the y-coordinate.
	 * @return returns a 2D adjacency matrix
	 */
	public double[][] calcAdjacencyMatrix(double[][] cities) {

		int numberOfCities = cities.length;
		double[][] matrix = new double[numberOfCities][numberOfCities];

		for (int i = 0; i < numberOfCities; i++) {
			double x1 = cities[i][0];
			double y1 = cities[i][1];

			for (int j = 0; j < numberOfCities; j++) {
				double x2 = cities[j][0];
				double y2 = cities[j][1];

				double distance = Math.sqrt(((x2 - x1) * (x2 - x1))
						+ ((y2 - y1) * (y2 - y1)));

				matrix[i][j] = distance;
			}
		}

		return matrix;

	}

	/**
	 * Calculates the answer to TSP.
	 * @param adjacencyMatrix 2D adjacency matrix.
	 * @return returns a solution to TSP in the form of an array of cities
	 */
	public static int[] tsp(double adjacencyMatrix[][]) {

		int[] path = new int[adjacencyMatrix[0].length];

		path[0] = 0;
		int currentCity = 0;

		int i = 1;
		while (i < path.length) {
			// find next city
			int nextCity = findMin(adjacencyMatrix[currentCity], path);
			// if the city is not -1 (meaning if there is a city to be visited
			if (nextCity != -1) {
				// add the city to the path
				path[i] = nextCity;
				// update currentCity and i
				currentCity = nextCity;
				i++;
			}
		}

		return path;
	}

	/**
	 * Finds the minimum neighbor of a node that is not currently in the path.
	 * @param row a row of an adjacency matrix.
	 * @param path the current path of the tps method.
	 * @return returns the next city.
	 */
	private static int findMin(double[] row, int[] path) {

		int nextCity = -1;
		int i = 0;
		double min = Integer.MAX_VALUE;

		while (i < row.length) {
			if (isCityInPath(path, i) == false && row[i] < min) {
				min = row[i];
				nextCity = i;
			}
			i++;
		}
		return nextCity;
	}

	/**
	 * Checks wether a city is in the current path
	 * @param path current path of cities.
	 * @param city a city
	 * @return true if city is in the current path, false if it is not.
	 */
	public static boolean isCityInPath(int[] path, int city) {
		for (int i = 0; i < path.length; i++) {
			if (path[i] == city) {
				return true;
			}
		}
		return false;
	}

	@Override
	/**
	 * Method to execute calculations
	 * @return returns the TSP path as an integer array of cities.
	 */
	public int[] call() {
		double[][] adj = calcAdjacencyMatrix(cities);
		int[] path = tsp(adj);
	
		int[] pathList = new int[cities.length];
		for (int index = 0; index < path.length; index++) {
			pathList[index] = path[index];
		}

		return pathList;
	}
}
