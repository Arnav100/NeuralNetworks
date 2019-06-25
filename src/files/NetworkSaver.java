package files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import core.Connection;
import core.NeuralNetwork;
import core.Neuron;
import core.NeuronLayer;

public class NetworkSaver {
	public static void save(String fileName, NeuralNetwork network, int lastSet) {
		File file = new File(fileName);
		try {
			if (file.createNewFile())
				saveProcess(file, network, lastSet);
			else {
				file.delete();
				file.createNewFile();
				saveProcess(file, network, lastSet);
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	public static void saveProcess(File file, NeuralNetwork network, int lastSet)
			throws IOException {
		System.out.println("Saving");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		ArrayList<NeuronLayer> layers = network.getLayers();
		// First records amount of layers and then size of each
		// Then records weights following each Neuron. Layers are set off by two
		// lines, neurons in layers are set off by one line

		String sizes = layers.size() + " , ";
		for (int i = 0; i < layers.size(); i++)
			sizes += layers.get(i).getNeurons().size() + " , ";

		bw.write(sizes);
		bw.newLine();
		bw.newLine();
		for (int i = 0; i < layers.size() - 1; i++) {
			for (int j = 0; j < layers.get(i).getNeurons().size(); j++) {
				Neuron n = layers.get(i).getNeurons().get(j);
				ArrayList<Connection> connections = n.getForwardConnections();
				bw.newLine();
				String info = "";
				for (int k = 0; k < connections.size(); k++)
					info += connections.get(k).getWeight() + " , ";
				bw.newLine();
				bw.write(info);
			}
			bw.newLine();
			bw.newLine();
		}
		bw.write("" + lastSet);
		bw.close();
	}
}
