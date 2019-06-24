package core;

public class Connection {
	private Neuron backNeuron, forwardNeuron;
	private double weight;
	private static int amount;
	private int id;

	public Connection(Neuron b, Neuron f) {
		backNeuron = b;
		forwardNeuron = f;
		// Generates a random weight to apply, and randomly makes it postive or
		// negative
		weight = Math.random() * (double) (Math.random() < 0.5 ? -1.0 : 1.0);
		id = ++amount;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getValue() {
		return backNeuron.getValue() * weight;
	}

	public Neuron getBack() {
		return backNeuron;
	}

	public Neuron getForward() {
		return forwardNeuron;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Connection))
			return false;
		return this.id == ((Connection) o).getId();
	}

	public int getId() {
		return id;
	}

}
