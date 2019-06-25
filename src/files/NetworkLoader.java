package files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import core.NeuralNetwork;
import core.NeuronLayer;

public class NetworkLoader {
	public static int lastSet = 0;

	public static NeuralNetwork load(String fileName) {
		File file = new File(fileName);
		if (!file.exists())
			return null;
		NeuralNetwork network = null;
		try {
			Scanner in = new Scanner(file);
			in.useDelimiter("\\s,\\s");
			System.out.println("Printing Load info");

			// Reads through the file, creates new network off info and then
			// sets the weights to written weights
			int[] sizes = new int[in.nextInt()];
			for (int i = 0; i < sizes.length; i++)
				sizes[i] = in.nextInt();
			network = new NeuralNetwork(sizes);
			ArrayList<NeuronLayer> layers = network.getLayers();

			for (int i = 0; i < sizes.length - 1; i++) {
				for (int j = 0; j < sizes[i]; j++) {
					while (!in.hasNextDouble())
						in.nextLine();
					for (int k = 0; k < sizes[i + 1]; k++) {
						layers.get(i).getNeurons().get(j).getForwardConnections().get(k)
								.setWeight(in.nextDouble());
					}
				}
			}

			while (!in.hasNextInt())
				in.nextLine();

			lastSet = in.nextInt();

		} catch (IOException e) {
			System.out.println(e);
		}
		return network;
	}
}
