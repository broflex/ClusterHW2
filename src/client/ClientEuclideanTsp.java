package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import system.SpaceImpl;
import api.Space;

public class ClientEuclideanTsp{
	
	private static final double[][] CITIES = { { 1, 1 }, { 8, 1 }, { 8, 8 }, { 1, 8 }, { 2, 2 },
			{ 7, 2 }, { 7, 7 }, { 2, 7 }, { 3, 3 }, { 6, 3 }, { 6, 6 },
			{ 3, 6 } };
    private static final int NUM_PIXALS = 600;

	
	public static void main(String[] args){
		
		String tempName = "TspClient";
		
		TspJob tspJob = new TspJob(CITIES);
		
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		try{
			
			long startOfJob = System.currentTimeMillis();
			
			Space space = (Space) Naming.lookup("rmi://" + tempName + "/" + Space.SERVICE_NAME);
			tspJob.generateTasks(space);
			tspJob.getResults(space);
			int[] minimalPath = (int[]) tspJob.getAllResults();
			
			//Displaying resultsS
			JLabel euclideanTspLabel = getLabel( minimalPath );
            JFrame frame = new JFrame( "Result Visualizations" );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            Container container = frame.getContentPane();
            container.setLayout( new BorderLayout() );
            container.add( new JScrollPane( euclideanTspLabel ), BorderLayout.EAST );
            frame.pack();
            frame.setVisible( true );
            space.exit();
            
            long endOfJob = System.currentTimeMillis();
            System.out.println("Client Job Time: " + (endOfJob - startOfJob) + " ms");
			
		}catch(Exception e){
			System.err.println("Error in TspClient");
			e.printStackTrace();
		}
		
	}
	
	public static JLabel getLabel( final int[] tour )
    {
        Logger.getLogger( ClientEuclideanTsp.class.getCanonicalName() ).log(Level.INFO, tourToString( tour ) );

        // display the graph graphically, as it were
        // get minX, maxX, minY, maxY, assuming they 0.0 <= mins
        double minX = CITIES[0][0], maxX = CITIES[0][0];
        double minY = CITIES[0][1], maxY = CITIES[0][1];
        for ( double[] cities : CITIES ) 
        {
            if ( cities[0] < minX ) 
                minX = cities[0];
            if ( cities[0] > maxX ) 
                maxX = cities[0];
            if ( cities[1] < minY ) 
                minY = cities[1];
            if ( cities[1] > maxY ) 
                maxY = cities[1];
        }

        // scale points to fit in unit square
        final double side = Math.max( maxX - minX, maxY - minY );
        double[][] scaledCities = new double[CITIES.length][2];
        for ( int i = 0; i < CITIES.length; i++ )
        {
            scaledCities[i][0] = ( CITIES[i][0] - minX ) / side;
            scaledCities[i][1] = ( CITIES[i][1] - minY ) / side;
        }

        final Image image = new BufferedImage( NUM_PIXALS, NUM_PIXALS, BufferedImage.TYPE_INT_ARGB );
        final Graphics graphics = image.getGraphics();

        final int margin = 10;
        final int field = NUM_PIXALS - 2*margin;
        // draw edges
        graphics.setColor( Color.BLUE );
        int x1, y1, x2, y2;
        int city1 = tour[0], city2;
        x1 = margin + (int) ( scaledCities[city1][0]*field );
        y1 = margin + (int) ( scaledCities[city1][1]*field );
        for ( int i = 1; i < CITIES.length; i++ )
        {
            city2 = tour[i];
            x2 = margin + (int) ( scaledCities[city2][0]*field );
            y2 = margin + (int) ( scaledCities[city2][1]*field );
            graphics.drawLine( x1, y1, x2, y2 );
            x1 = x2;
            y1 = y2;
        }
        city2 = tour[0];
        x2 = margin + (int) ( scaledCities[city2][0]*field );
        y2 = margin + (int) ( scaledCities[city2][1]*field );
        graphics.drawLine( x1, y1, x2, y2 );

        // draw vertices
        final int VERTEX_DIAMETER = 6;
        graphics.setColor( Color.RED );
        for ( int i = 0; i < CITIES.length; i++ )
        {
            int x = margin + (int) ( scaledCities[i][0]*field );
            int y = margin + (int) ( scaledCities[i][1]*field );
            graphics.fillOval( x - VERTEX_DIAMETER/2,
                               y - VERTEX_DIAMETER/2,
                              VERTEX_DIAMETER, VERTEX_DIAMETER);
        }
        final ImageIcon imageIcon = new ImageIcon( image );
        return new JLabel( imageIcon );
    }
    
    private static String tourToString( int[] tour )
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "Tour: " );
        for ( Integer city : tour )
        {
            stringBuilder.append( city ).append( ' ' );
        }
        return stringBuilder.toString();
    }
	

}