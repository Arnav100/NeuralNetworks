package core;

import java.util.ArrayList;

public class Neuron {

	private ArrayList<Connection> backConnections, forwardConnections;
	private static int amount;
	private boolean isInput;
	private double inputVal;
	private double changeToCost;
	private int id;

	public Neuron() {
		backConnections = new ArrayList<Connection>();
		forwardConnections = new ArrayList<Connection>();
		id = ++amount;
		inputVal = 0;
	}

	public ArrayList<Connection> getForwardConnections() {
		return forwardConnections;
	}

	public ArrayList<Connection> getBackConnections() {
		return backConnections;
	}

	// These four methods connect one Neuron to another via a connection object

	public void connectForwardNeuron(Neuron forward) {
		Connection connection = new Connection(this, forward);
		forwardConnections.add(connection);
		forward.connectBackNeuron(connection);
	}

	public void connectForwardNeuron(Connection forwardConnection) {
		if (forwardConnections.contains(forwardConnection))
			return;
		forwardConnections.add(forwardConnection);
	}

	public void connectBackNeuron(Neuron back) {
		Connection connection = new Connection(back, this);
		backConnections.add(connection);
		back.connectForwardNeuron(connection);
	}

	public void connectBackNeuron(Connection backConnection) {
		if (backConnections.contains(backConnection))
			return;
		backConnections.add(backConnection);
	}

	// int bias = backConnections.size()/75?

	/**
	 * Determines the output value of this neuron based off the values from the
	 * back connections or the inputted value if it is an input neuron
	 * 
	 * @return the non sigmoid value of this neuron
	 */
	public double getNonSigmoidValue() {
		if (isInput)
			return inputVal;
		double value = 0;
		for (Connection c : backConnections)
			value += c.getValue();
		return value;
	}

	/**
	 * Returns the sigmoid value of this neuron, from 0-1
	 * 
	 * @return the sigmoid value of this neuron, from 0-1
	 */
	public double getValue() {
		if (isInput)
			return inputVal;
		return Functions.sigmoid(getNonSigmoidValue());
	}

	public void setAsInputLayer() {
		isInput = true;
	}

	public void setInput(double input) {
		this.inputVal = input;
	}

	public void setChangeToCost(double changeToCost) {
		this.changeToCost = changeToCost;
	}

	/**
	 * This returns this the derivative: Change to cost with respect to this
	 * Neuron
	 * 
	 * @return Change to cost with respect to this Neuron
	 */
	public double getChangeToCost() {
		return changeToCost;
	}

	public boolean isInput() {
		return isInput;
	}

	public int getId() {
		return id;
	}

}
