package core;

import java.util.ArrayList;

import files.NetworkLoader;

public class Runner {
	public static void main(String[] args) {
		int[] sizes = { 28 * 28, 4, 4, 10 };
		String filePath = "src/files/test.txt";
		NeuralNetwork test = NetworkLoader.load(filePath);
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

		ArrayList<Double>[] expected = new ArrayList[4];
		expected[0] = new ArrayList<Double>();

		expected[0].add(1.0);
		expected[1] = new ArrayList<Double>();
		expected[1].add(1.0);
		expected[2] = new ArrayList<Double>();
		expected[2].add(0.0);
		expected[3] = new ArrayList<Double>();
		expected[3].add(0.0);

		// print(inputs, test);

		int k = 0;
		long startTime = System.currentTimeMillis();
		// while (!test.backPropagator.isGood && System.currentTimeMillis() <
		// startTime + 10000) {
		// // test.backPropogate(1, expected);
		// test.train(inputs, expected);
		// k++;
		// }
		System.out.println("Trials Taken: " + k);
		// NetworkSaver.save(filePath, test, k);
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
