package mnist;

import java.util.ArrayList;

import core.NeuralNetwork;
import files.NetworkLoader;

public class Runner {
	public static void main(String[] args) {

		int[] sizes = { 28 * 28, 4, 4, 10 };
		String filePath = "src/files/networkTest.txt";
		NeuralNetwork network = NetworkLoader.load(filePath);
		if (network == null)
			network = new NeuralNetwork(sizes);

		String trainPath = "src/mnist/mnist_train.csv";
		int start = NetworkLoader.lastSet;
		start = 0;
		Reader reader = new Reader(trainPath, start);
		start = NetworkLoader.lastSet;
		int i = 0;
		reader.getData();
		printOutputAndExpected(network, reader.getInput(), reader.getExpected());
		// while (i < 200) {
		// if (i % 5 == 0)
		// NetworkSaver.save(filePath, network, i + start);
		// network.train(reader.getInput(), reader.getExpected());
		// System.out.println("Trial " + (i + start));
		// i++;
		//
		// }
		// System.out.println("Total Trials: " + (i + start));
		// NetworkSaver.save(filePath, network, i + start);

	}

	public static void printOutputAndExpected(NeuralNetwork net, ArrayList<Double>[] input,
			ArrayList<Double>[] expected) {
		for (int i = 0; i < input.length; i++) {
			int expectOutput = -1;
			net.setInputs(input[i]);
			Viewer view = new Viewer();
			// view.input(input[i]);
			for (int j = 0; j < expected[i].size(); j++)
				if (expected[i].get(j) == 1.0)
					expectOutput = j;
			ArrayList<Double> output = net.getOutput();
			double greatestOutputPercent = 0;
			int outputVal = -1;
			for (int j = 0; j < output.size(); j++) {
				System.out.print(output.get(j) + " ");
				if (output.get(j) > greatestOutputPercent) {
					greatestOutputPercent = output.get(j);
					outputVal = j;
				}
			}

			System.out.println("Expected: " + expectOutput + " Given: " + outputVal);
		}
	}

	public static void print(ArrayList<Double>[] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].size(); j++)
				System.out.print(data[i].get(j) + " ");
			System.out.println("\n");
		}
	}
}
