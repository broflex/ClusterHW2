package client;

import tasks.TaskMandelbrotSet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMandelbrotSet extends Client<Integer[][]>{

    private static final double LOWER_LEFT_X = -0.7510975859375;
    private static final double LOWER_LEFT_Y = 0.1315680625;
    private static final double EDGE_LENGTH = 0.01611;
    private static final int N_PIXELS = 1024;
    private static final int ITERATION_LIMIT = 512;


    public ClientMandelbrotSet() throws RemoteException, NotBoundException, MalformedURLException
    {
        super( "Mandelbrot Set Visualizer", "128.111.43.28",
                new TaskMandelbrotSet( LOWER_LEFT_X, LOWER_LEFT_Y, EDGE_LENGTH, N_PIXELS,
                        ITERATION_LIMIT) );
    }

    private static void main(String[] args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
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