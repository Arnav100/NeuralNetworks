package core;

import java.util.ArrayList;

public class BackPropagator {

	private NeuralNetwork network;
	private ArrayList<Double> lowestCostNet;
	private ArrayList<Double> output;
	private ArrayList<Double> weightAdjustments;
	private int weightLength;
	private int sampleSize, samplesChecked;
	private double cost;
	public boolean isGood;

	public BackPropagator(NeuralNetwork network) {
		this.network = network;
		weightAdjustments = new ArrayList<Double>();
		isGood = false;
	}

	public void backPropagate(int sampleSize, ArrayList<Double> sampleExpected) {
		this.sampleSize = sampleSize;
		calculateCost(sampleExpected);
		if (cost < 0.00001)
			isGood = true;

		ArrayList<Neuron> lastLayer = network.getLastLayer().getNeurons();
		weightLength = 0;
		for (int i = 0; i < lastLayer.size(); i++) {
			double initalChange = 2 * (lastLayer.get(i).getValue() - sampleExpected.get(i));
			lastLayer.get(i).setChangeToCost(initalChange);
		}
		for (int i = 0; i < lastLayer.size(); i++)
			findChanges(lastLayer.get(i));

		weightLength = 0;
		for (int i = 0; i < lastLayer.size(); i++)
			applyChanges(lastLayer.get(i));

		reset();
	}

	private void reset() {
		if (samplesChecked == sampleSize) {
			cost = 0;
			samplesChecked = 0;
			weightAdjustments = new ArrayList<Double>();
		}
	}

	public void printLowest() {
		for (Double val : lowestCostNet)
			System.out.println(val);
	}

	public void averageChanges() {
		for (int i = 0; i < weightAdjustments.size(); i++)
			weightAdjustments.set(i, weightAdjustments.get(i) / sampleSize);
	}

	private void applyChanges(Neuron n) {
		if (n.isInput())
			return;
		if (samplesChecked != sampleSize) // Apply changes only if they can be
											// averaged
			return;
		averageChanges();
		ArrayList<Connection> backConnections = n.getBackConnections();
		for (Connection c : backConnections) {
			n = c.getBack();
			double change = weightAdjustments.get(weightLength++);
			c.setWeight(c.getWeight() - change * 1.5); // Apply the change to
														// the current, 1.5 to
														// make it a bit faster
			applyChanges(n);
		}
	}

	private void findChanges(Neuron n) {
		if (n.isInput())
			return;
		ArrayList<Connection> backConnections = n.getBackConnections();
		for (Connection c : backConnections) {
			Neuron back = c.getBack();
			double backVal = back.getValue();
			// Derivative formula: Change in cost with respect to weight
			double change = backVal * Functions.sigDerivative(n.getNonSigmoidValue())
					* n.getChangeToCost();
			addChange(change);

			// The affect this back neuron has on the cost (Change in cost with
			// respect to this back Neuron) is calculated by using all its
			// forward connections
			double changeSum = 0;
			for (Connection f : back.getForwardConnections()) {
				Neuron forward = f.getForward();
				changeSum += f.getWeight() * Functions.sigDerivative(forward.getNonSigmoidValue())
						* forward.getChangeToCost();
			}
			back.setChangeToCost(changeSum);

			findChanges(back);
		}
	}

	public void printChanges() {
		for (int i = 0; i < weightAdjustments.size(); i++)
			System.out.println(weightAdjustments.get(i));
	}

	private void addChange(double val) {
		// Either adds value if new sample has started, otherwise adds it to the
		// existing spot to be averaged later
		if (samplesChecked - 1 == 0)
			weightAdjustments.add(val);
		else {
			weightAdjustments.set(weightLength, weightAdjustments.get(weightLength++) + val);
		}

	}

	// This gives you how sensitive the Cost is with respect to a neuron
	// weight * derivative sig (val) * 2 (output - expected)
	// weight * derivative sig (val) <--- This part repeats I think

	// This give you how sensitive the Cost is with respect to a weight
	// prev Neuron * derivative sig (val) * 2 (output - expected)
	//
	public void getNetworkOutput() {
		output = network.getOutput();
	}

	int test = 0;

	private void calculateCost(ArrayList<Double> expected) {
		getNetworkOutput();
		for (int i = 0; i < output.size(); i++) {
			cost += (output.get(i) - expected.get(i)) * (output.get(i) - expected.get(i));
			if (test == 0)
				System.out.println("Expected " + i + ":" + expected.get(i) + "\n");
		}

		if (++samplesChecked == sampleSize) {
			cost /= sampleSize;
			test++;
			if (test % 25 == 0)
				System.out.println(cost);
		}

	}
}
