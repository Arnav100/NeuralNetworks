package core;

import java.util.ArrayList;

public class NeuronLayer {
	private ArrayList<Neuron> neurons;
	private boolean isInputLayer;

	public NeuronLayer(int length) {
		neurons = new ArrayList<Neuron>();
		for (int i = 0; i < length; i++)
			neurons.add(new Neuron());
	}

	public void setAsInputLayer() {
		isInputLayer = true;
		for (Neuron n : neurons)
			n.setAsInputLayer();
	}

	/**
	 * Sets the inputs for each Neuron
	 * 
	 * @param inputs
	 *            holds each value for each input Neuron
	 */
	public void setInputs(ArrayList<Double> inputs) {
		// Maybe make this throw an error?
		if (inputs.size() != neurons.size())
			return;
		for (int i = 0; i < inputs.size(); i++)
			neurons.get(i).setInput(inputs.get(i));
	}

	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}

	/**
	 * Returns the values of each Neuron
	 * 
	 * @return the values of each Neuron
	 */
	public ArrayList<Double> getValues() {
		ArrayList<Double> vals = new ArrayList<Double>();
		for (Neuron n : neurons)
			vals.add(n.getValue());

		return vals;
	}

	/**
	 * Goes through each Neuron of this layer and connects to each Neuron of the
	 * layer ahead of it
	 * 
	 * @param forward
	 *            the NeuronLayer for this one to transmit values to.
	 */
	public void connectLayer(NeuronLayer forward) {
		for (Neuron n : neurons)
			for (Neuron f : forward.getNeurons())
				n.connectForwardNeuron(f);
	}

	public void printForward() {
		for (Neuron n : neurons)
			for (Connection c : n.getForwardConnections())
				System.out.println(
						"Neuron " + n.getId() + " F Connection: " + c.getForward().getId());
		System.out.println("\n");
	}
}
