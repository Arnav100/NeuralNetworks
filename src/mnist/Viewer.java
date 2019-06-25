package mnist;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Viewer {
	private int length = 280;
	private int width = 280;

	public void input(ArrayList<Double> values) {
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 28; j++)
				if (j + 28 * i < values.size()) {
					// while (num.length() < 3)
					// num = " " + num;
					System.out.printf("%-4.1f", values.get(j + 28 * i));
				}
			System.out.println();
		}
		System.out.println(
				"\n\n------------------------------------------------------------------------------\n\n");
	}

	public void setUp() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(length, width);
		f.setResizable(false);
		f.setLocation(200, 200);
		f.setVisible(true);
	}

	class NumberPanel extends JPanel {
		ArrayList<Double> values;

		public NumberPanel(ArrayList<Double> values) {
			this.values = values;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

		}
	}
}
