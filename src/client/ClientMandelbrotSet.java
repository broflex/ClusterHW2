package client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMandelbrotSet extends Client<Integer[][]>{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9197468818871468628L;
	
	private static final double LOWER_LEFT_X = -0.7510975859375;
    private static final double LOWER_LEFT_Y = 0.1315680625;
    private static final double EDGE_LENGTH = 0.01611;
    private static final int N_PIXELS = 1024;
    private static final int ITERATION_LIMIT = 512;

    public ClientMandelbrotSet() throws RemoteException, NotBoundException, MalformedURLException
    {
        super( "Mandelbrot Set Visualizer", "localhost" );
    }

    public static void main(String[] args) throws Exception{

        MandelbrotJob mandelbrotJob = new MandelbrotJob(LOWER_LEFT_X, LOWER_LEFT_Y, EDGE_LENGTH, N_PIXELS, ITERATION_LIMIT);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            ClientMandelbrotSet client = new ClientMandelbrotSet();
            client.begin();

            System.out.println("1");
            mandelbrotJob.generateTasks(space);
            System.out.println("2");
            mandelbrotJob.getResults(space);
            System.out.println("3");

            Integer[][] counts = mandelbrotJob.getAllResults();
            System.out.println("4");
            client.add(client.getLabel(counts));
            client.end();

        } catch (RemoteException e) {
            System.err.println("RemoteException in ClientMandelbrotSet: ");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException in ClientMandelbrotSet: ");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException in ClientMandelbrotSet: ");
            e.printStackTrace();
        }
    }

    public JLabel getLabel( Integer[][] counts )
    {
        final Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
        final Graphics graphics = image.getGraphics();
        for ( int i = 0; i < counts.length; i++ )
            for ( int j = 0; j < counts.length; j++ )
            {
                graphics.setColor( getColor( counts[i][j] ) );
                graphics.fillRect( i, N_PIXELS - j, 1, 1 );
            }
        final ImageIcon imageIcon = new ImageIcon( image );
        return new JLabel( imageIcon );
    }

    private Color getColor( int iterationCount )
    {
        return iterationCount == ITERATION_LIMIT ? Color.BLACK : Color.WHITE;
    }
}