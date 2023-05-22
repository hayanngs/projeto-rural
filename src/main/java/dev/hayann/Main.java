package dev.hayann;

import javax.swing.*;
import java.awt.*;

public class Main {

	private final JPanel jPanel = new JPanel();
	private final JTabbedPane tabbedPane = new JTabbedPane();

	public static void main(String[] args) {
		Main app = new Main();
		JFrame frame = new JFrame("Gerenciador Rural");
		frame.setContentPane(app.jPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
		app.jPanel.add(app.tabbedPane);
	}
}
