package core;

import java.util.ArrayList;

public class NeuralNetwork {

	private ArrayList<NeuronLayer> networkLayers;
	public BackPropagator backPropagator;

	public NeuralNetwork(int[] sizes) {
		networkLayers = new ArrayList<NeuronLayer>();
		// Create each layer of the Network and connect them
		for (int i = 0; i < sizes.length; i++) {
			networkLayers.add(new NeuronLayer(sizes[i]));
			if (i == 0)
				networkLayers.get(i).setAsInputLayer();
			else
				networkLayers.get(i - 1).connectLayer(networkLayers.get(i));
		}
		backPropagator = new BackPropagator(this);
	}

	public void train(ArrayList<Double>[] inputs, ArrayList<Double>[] outputs) {
		for (int i = 0; i < inputs.length; i++) {
			setInputs(inputs[i]);
			backPropogate(inputs.length, outputs[i]);
		}
	}

	public void backPropogate(int sampleSize, ArrayList<Double> expected) {
		backPropagator.backPropagate(sampleSize, expected);
	}

	public void setInputs(ArrayList<Double> inputs) {
		networkLayers.get(0).setInputs(inputs);

		// For testing
		// networkLayers.get(0).getInputs();
	}

	// test
	public ArrayList<Double> getOutput(int index) {
		if (index > networkLayers.size())
			return null;
		return networkLayers.get(index).getValues();
	}

	public ArrayList<Double> getOutput() {
		return networkLayers.get(networkLayers.size() - 1).getValues();
	}

	public ArrayList<NeuronLayer> getLayers() {
		return networkLayers;
	}

	public NeuronLayer getLastLayer() {
		return networkLayers.get(networkLayers.size() - 1);
	}

	public void printWeights() {
		for (NeuronLayer layer : networkLayers) {
			for (Neuron n : layer.getNeurons()) {
				for (Connection c : n.getForwardConnections())
					System.out.println(c.getWeight());
				System.out.println();
			}
			System.out.println();
		}
	}

	public void test() {
		for (NeuronLayer layer : networkLayers)
			layer.printForward();
	}
}
