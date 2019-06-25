package mnist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

	private Scanner in;
	ArrayList<Double>[] input, expected;
	boolean inputGiven, expectedGiven;

	public Reader(String sourcePath, int start) {
		input = new ArrayList[10];
		expected = new ArrayList[10];
		try {
			in = new Scanner(new File(sourcePath));
			in.useDelimiter(",");
		} catch (IOException e) {
			System.out.println(e);
		}
		startSet(start);
	}

	public ArrayList<Double>[] getExpected() {
		return expected;
	}

	public ArrayList<Double>[] getInput() {
		return input;
	}

	public boolean getData() {
		if (!in.hasNextLine())
			return false;

		for (int i = 0; i < 10; i++) {
			expected[i] = new ArrayList<Double>();
			int label = in.nextInt();
			for (int j = 0; j < 10; j++)
				if (j == label)
					expected[i].add(1.0);
				else
					expected[i].add(0.0);
			input[i] = new ArrayList<Double>();
			while (in.hasNextDouble()) {
				input[i].add(in.nextDouble() / 254);
			}
			input[i].add(0.0); // The dataset does not have a comma at the end
								// of the line, so this is to account for the
								// last value
			in.nextLine();

		}
		return true;
	}

	private void startSet(int start) {
		for (int i = 0; i < start; i++)
			getData();
	}
}
