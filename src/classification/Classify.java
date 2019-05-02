package classification;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Classify {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		String errorMessage = "Incorrect input, follow directions above.";
		Scanner in = new Scanner(System.in);
		String[] splitInputString;
		System.out.println("Please specify image set: [f]aces or [d]igits\n"
				+ "Please specify classification algorithm: [p]erceptron, [n]aive-bayes or [o]ther\n"
				+ "Please specify percent of training data to use: 0 -> 100\n"
				+ "Enter parameters in the order they appear above. Here is an example:\n"
				+ "f p 50\n"
				+ "this will use the faces image set, classify using perceptron and use 50% of\n"
				+ "the training data");
		splitInputString = in.nextLine().split(" ");
		if (splitInputString.length >= 3){
			if (!(splitInputString[0].equalsIgnoreCase("f") || splitInputString[0].equalsIgnoreCase("d"))){
				System.out.println("[0] " + errorMessage);
				return;
			}
			if (!(splitInputString[1].equalsIgnoreCase("p") || splitInputString[1].equalsIgnoreCase("n") 
					|| splitInputString[1].equalsIgnoreCase("o"))){
				System.out.println("[1] " + errorMessage);
				return;
			}
			if (Integer.parseInt(splitInputString[2]) < 0 || Integer.parseInt(splitInputString[2]) > 100){
				System.out.println("[2] " + errorMessage);
				return;
			}
		}
		else {
			System.out.println(errorMessage);
			return;
		}
		
		if (splitInputString[1].equalsIgnoreCase("p")){
			//perceptron(splitInputString[0], Double.parseDouble(splitInputString[2])/100)
		}
		else if (splitInputString[1].equalsIgnoreCase("n")){
			//naiveBayes(splitInputString[0], Double.parseDouble(splitInputString[2])/100)
		}
		else{
			//use other classification algorithm here
		}
		*/
		int k = 1;
		NaiveBayes nb = new NaiveBayes(false, 1);
		System.out.println("error: " + nb.determineError(k));
		//nb.demonstrate(k, 10);
		
	}

}
