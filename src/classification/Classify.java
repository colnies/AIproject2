package classification;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Classify {
	
	public static ArrayList<boolean[][]> getDigitImages(boolean isTrainingSet){
		String fileName;
		int rows, columns;
		ArrayList<boolean[][]> images = new ArrayList<boolean[][]>();
		//ALL DIGIT IMAGES ARE 20 PIXELS LONG 28 PIEXELS WIDE
		rows = 20;
		columns = 28;
		if (isTrainingSet){
			fileName = "data/digitdata/trainingimages";
		}
		else {
			fileName = "data/digitdata/testimages";
		}
		
		try {
			Scanner sc = new Scanner(new File(fileName));
			String s = sc.nextLine();
			while (sc.hasNextLine()){
				//if the line has any non-whitespace chars
				if (s.trim().length() > 0){
					boolean[][] image = new boolean[rows][columns];
					//int i = 0;
					//while there are lines that are not just whitespace
					//while (sc.hasNextLine() && s.trim().length() > 0){
					for (int i = 0; i < rows; i++){
						if (i >= rows){
							System.out.println("error: i >= rows");
							sc.close();
							return null;
						}
						//iterate through each character in the line
						for(int j = 0; j < s.length(); j++){
							if (!Character.isWhitespace(s.charAt(j))){
								image[i][j] = true;
							}
						}
						//i++;
						s = sc.nextLine();
					}
					images.add(image);
				}
				else {
					s = sc.nextLine();
				}
			}
			sc.close();
			return images;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<boolean[][]> getFaceImages(boolean isTrainingSet){
		int rows = 70;
		int columns = 60;
		String fileName;
		ArrayList<boolean[][]> images = new ArrayList<boolean[][]>();
		if (isTrainingSet){
			fileName = "data/facedata/facedatatrain";
		}
		else {
			fileName = "data/facedata/facedatatest";
		}
		Scanner sc;
		try {
			sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()){
				boolean[][] image = new boolean[rows][columns];
				for (int i = 0; i < rows; i++){
					String s = sc.nextLine();
					for (int j = 0; j < columns; j++){
						if (!Character.isWhitespace(s.charAt(j))){
							image[i][j] = true;
						}
					}
				}
				images.add(image);
			}
			sc.close();
			return images;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Integer> getLabelList(boolean isFaceType, boolean isTrainingSet){
		String fileName;
		ArrayList<Integer> labels = new ArrayList<Integer>();
		if (isFaceType){
			if (isTrainingSet)
				fileName = "data/facedata/facedatatrainlabels";
			else 
				fileName = "data/facedata/facedatatestlabels";
		}
		else {
			if (isTrainingSet)
				fileName = "data/digitdata/traininglabels";
			else 
				fileName = "data/digitdata/testlabels";
		}
		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()){
				String s = sc.nextLine();
				labels.add( Integer.parseInt(s.trim()) );
			}
			return labels;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static void printImage(boolean[][] image){
		for(int i = 0; i < image.length; i++){
			for(int j = 0; j < image[i].length; j++){
				if (image[i][j])
					System.out.print("1");
				else 
					System.out.print("0");
			}
			System.out.println();
		}
		System.out.println("\n");
	}

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
		*/
		ArrayList<boolean[][]> trainingImages;
		//trainingImages = getDigitImages(false);
		ArrayList<Integer> trainingLabels = getLabelList(true, false);
		trainingImages = getFaceImages(false);
		for(int i = 0; i < trainingImages.size(); i++){
			System.out.println(trainingLabels.get(i));
			printImage(trainingImages.get(i));
			
		}
		
		/*
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
	}

}
