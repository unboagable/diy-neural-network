package neuralNetwork;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.PerceptronPanel;

public class PerceptronSimulation {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowSimulation();
			}
		});
	}
	
	private static void createAndShowSimulation() {
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

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
}
