package classification;

import java.util.ArrayList;

public class Perceptron {
	ArrayList<boolean[][]> trainingSet;
	ArrayList<boolean[][]> testingSet;
	double percent;
	public Perceptron(ArrayList<boolean[][]> trainingSet, ArrayList<boolean[][]> testingSet, 
			double percent){
		this.trainingSet = trainingSet;
		this.testingSet = testingSet;
		this.percent = percent;
	}
	
	
}
