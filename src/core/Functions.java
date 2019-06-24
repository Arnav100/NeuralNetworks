package core;

public class Functions {

	public static double sigmoid(double val) {
		return 1 / (1 + Math.pow(Math.E, -val));
	}

	public static double sigDerivative(double val) {
		return sigmoid(val) * (1 - sigmoid(val));
	}
}
