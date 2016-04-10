package singlePerceptron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PerceptronSimulation {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowSimulation(true);
			}
		});
	}
	
	private static void createAndShowSimulation(boolean timed) {
		// Create and set-up the window.
		final JFrame frame = new JFrame("Perceptron Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		final PerceptronPanel simulationPanel = new PerceptronPanel();
		simulationPanel.setup();
		simulationPanel.setOpaque(true);
		frame.setContentPane(simulationPanel);
		
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				simulationPanel.oneMoreTraining();
				frame.repaint();
			}
	    });
		
		Timer timer = new Timer(1000, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
				simulationPanel.oneMoreTraining();
				frame.repaint();
		    }    
		});
		
		timer.start();
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
}
