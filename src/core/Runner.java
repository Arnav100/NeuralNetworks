package core;

import java.util.ArrayList;

import files.NetworkLoader;
import files.NetworkSaver;

public class Runner {
	public static void main(String[] args) {
		int[] sizes = { 2, 4, 4, 1 };
		NeuralNetwork test = NetworkLoader.load("src/files/test.txt");
		if (test == null)
			test = new NeuralNetwork(sizes);
		ArrayList<Double>[] inputs = new ArrayList[4];
		inputs[0] = new ArrayList<Double>();
		inputs[0].add(0.0);
		inputs[0].add(1.0);
		inputs[1] = new ArrayList<Double>();
		inputs[1].add(1.0);
		inputs[1].add(0.0);
		inputs[2] = new ArrayList<Double>();
		inputs[2].add(0.0);
		inputs[2].add(0.0);
		inputs[3] = new ArrayList<Double>();
		inputs[3].add(1.0);
		inputs[3].add(1.0);
		// inputs[0].add(0.75);
		// inputs[0].add(0.62);
		// inputs[0].add(0.98);
		// inputs[1] = new ArrayList<Double>();
		// inputs[1].add(0.7);
		// inputs[1].add(0.6);
		// inputs[1].add(0.8);
		// inputs[1].add(0.2);
		// inputs[1].add(0.1);

		// test.setInputs(inputs);

		ArrayList<Double>[] expected = new ArrayList[4];
		expected[0] = new ArrayList<Double>();
		expected[0].add(1.0);
		expected[1] = new ArrayList<Double>();
		expected[1].add(1.0);
		expected[2] = new ArrayList<Double>();
		expected[2].add(0.0);
		expected[3] = new ArrayList<Double>();
		expected[3].add(0.0);
		// expected[0].add(0.2);
		// expected[0].add(0.7);

		// expected[1] = new ArrayList<Double>();
		// expected[1].add(0.6);
		// expected[1].add(0.4);
		// expected[1].add(0.1);

		print(inputs, test);

		int k = 0;
		long startTime = System.currentTimeMillis();
		while (!test.backPropagator.isGood && System.currentTimeMillis() < startTime + 20000) {
			// test.backPropogate(1, expected);
			test.train(inputs, expected);
			k++;
		}
		System.out.println("Trials Taken: " + k);
		NetworkSaver.save("src/files/test.txt", test);
		System.out.println("\n");
		print(inputs, test);
	}

	private static void print(ArrayList<Double>[] inputs, NeuralNetwork test) {
		for (int i = 0; i < inputs.length; i++) {
			test.setInputs(inputs[i]);
			ArrayList<Double> vals = test.getOutput();
			for (Double val : vals)
				System.out.println(val);
			System.out.println("\n");
		}
	}
}
