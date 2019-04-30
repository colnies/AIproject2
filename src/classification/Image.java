package classification;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Image {
	
	boolean[][] pixels;
	int label;
	
	public Image(boolean[][] pixels, int label){
		this.pixels = pixels;
		this.label = label;
	}
	
	/**
	 * @param isTrainingSet if true returns training set, else returns testing set
	 * @return arrayList of digit Images 
	 */
	private static ArrayList<Image> getDigitImages(boolean isTrainingSet){
		String fileName;
		int rows, columns;
		ArrayList<boolean[][]> rawImages = new ArrayList<boolean[][]>();
		ArrayList<Image> images = new ArrayList<Image>();
		ArrayList<Integer> labels;
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
					rawImages.add(image);
				}
				else {
					s = sc.nextLine();
				}
			}
			sc.close();
			
			labels = getLabelList(false, isTrainingSet);
			
			for (int i = 0; i < rawImages.size(); i++){
				images.add( new Image(rawImages.get(i), labels.get(i)) );
			}
			return images;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param isTrainingSet if true returns training set, else returns testing set
	 * @return arrayList of face Images
	 */
	private static ArrayList<Image> getFaceImages(boolean isTrainingSet){
		int rows = 70;
		int columns = 60;
		String fileName;
		ArrayList<boolean[][]> rawImages = new ArrayList<boolean[][]>();
		ArrayList<Image> images = new ArrayList<Image>();
		ArrayList<Integer> labels;
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
				rawImages.add(image);
			}
			sc.close();
			labels = getLabelList(true, isTrainingSet);
			for (int i = 0; i < rawImages.size(); i++){
				images.add( new Image(rawImages.get(i), labels.get(i)) );
			}
			return images;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param imageSetSize the size of the imageSet we want to extract a 
	 * percent of images from
	 * @param percent of the image set we want to extract. must be between 
	 * 0 and 1
	 * @return a list of indices corresponding to a subset of the imageset
	 */
	private static ArrayList<Integer> chooseIndices(int imageSetSize,
			double percent){
		int newSize = (int)( (double)imageSetSize * percent);
		System.out.println("new size: " + newSize);
		int index = 0;
		boolean[] visited = new boolean[imageSetSize];
		ArrayList<Integer> chosenIndices = new ArrayList<Integer>();
		Random rand = new Random();
		
		if (percent > 1  || percent < 0){
			System.out.println("percent must be between 0 and 1");
			return null;
		}
			
		for (int i = 0; i < newSize; i++){
			index = rand.nextInt(imageSetSize);
			while (visited[index] == true){
				index = rand.nextInt(imageSetSize);
			}
			visited[index] = true;
			chosenIndices.add(index);
		}
		
		return chosenIndices;
	}
	

	
	/**
	 * @param isFaceType is true if we are working with faces. False for digits
	 * @param isTrainingSet is true if we want the training labels false if we want
	 * the testing labels
	 * @return an arraylist of integers representing the labels for a set of images
	 */
	private static ArrayList<Integer> getLabelList(boolean isFaceType, boolean isTrainingSet){
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
	
	/**
	 * 
	 * @param isFaceType is true if you want the training set for faces. False
	 * if you want the training set for digits
	 * @param percent is the percent of images in training set you want to use
	 * @return a arrayList of Images whose size is determined by percent and whose
	 * images are chosen at random
	 */
	public static ArrayList<Image> getTrainingImages(boolean isFaceType, double percent){
		ArrayList<Image> images;
		ArrayList<Image> imagesSubset = new ArrayList<Image>();
		ArrayList<Integer> chosenIndices;
		if (isFaceType){
			images = getFaceImages(true);
		}
		else
			images = getDigitImages(true);
		System.out.println("original size: " + images.size());
		chosenIndices = chooseIndices(images.size(), percent);
		for (int index: chosenIndices){
			imagesSubset.add(images.get(index));
		}
		return imagesSubset;
	}
	
	/**
	 * 
	 * @param isFaceType is true if you want the testing set for faces. false 
	 * if you want the testing set for digits
	 * @return an arrayList of Images corresponding to the testing set for
	 * faces or digits
	 */
	public static ArrayList<Image> getTestingImages(boolean isFaceType){
		if (isFaceType)
			return getFaceImages(false);
		else
			return getDigitImages(false);
	}
	
	/**
	 * prints out the image as 1s and 0s. 1 is true, 0 is false.
	 * @param image boolean matirx where each element indicates
	 * whether that pixel is on or off
	 */
	public static void printImage(Image image){
		for(int i = 0; i < image.pixels.length; i++){
			for(int j = 0; j < image.pixels[i].length; j++){
				if (image.pixels[i][j])
					System.out.print("1");
				else 
					System.out.print("0");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
}
