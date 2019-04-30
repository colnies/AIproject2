package classification;

import java.util.ArrayList;

/**
 * A naiveBayes object can classify images given a training set. It uses the following
 * equation to determine the most likely label for a particular image. 
 * 
 * argmax[y](log(P(y| f1, ..., fm))) = argmax[y]( log(P(y) + summation[i=1->m](log(P(fi|y)))
 * 
 * where y can be any label
 * and argmax[y] is the max value over every y value
 * and f1,...,fm is the feature vector (I use a feature matrix but its the same idea)
 * @author Eric Zimerman
 *
 */
public class NaiveBayes {
	/**
	 * the set of training images. From these images the machine can have an idea what
	 * an image of each label looks like
	 */
	ArrayList<Image> trainingSet;
	/**
	 * the set of testing images. The machine will attempt to classify these images
	 */
	ArrayList<Image> testingSet;
	/**
	 * the small set of labelData objects corresponding to each label. If we are 
	 * testing faces it will have data for labels 1 and 0. if we are testing
	 * digits it will have data for labels 0 -> 9
	 */
	ArrayList<LabelData> allLabels;
	
	/**
	 * creates a NaiveBayes object and trains it on the given trainingSet
	 * @param trainingSet
	 * @param testingSet
	 */
	public NaiveBayes(ArrayList<Image> trainingSet, ArrayList<Image> testingSet ){
		this.trainingSet = trainingSet;
		this.testingSet = testingSet;
		allLabels = new ArrayList<LabelData>();
		initialize();
	}
	
	/**
	 * creates a NaiveBayes object and trains it on a percentage of the training set indicated 
	 * by isFaceType and percent
	 * @param isFaceType true = working with faces. false = working with digits
	 * @param percent the percentage of training images you want the machine to "learn" from.
	 * 0 <= percent <= 1
	 */
	public NaiveBayes(boolean isFaceType, double percent){
		this(Image.getTrainingImages(isFaceType, percent), Image.getTestingImages(isFaceType));
	}
	
	/**
	 * this pretty much "trains" the machine on the training set. It populates the
	 * true features matrix for every label. Every time a pixel[i][j] is true
	 * trueFeatures[i][j] is incremented by 1. It will process every picture
	 * in the training set
	 */
	private void initialize(){
		for (Image image: trainingSet){
			LabelData currentLabel;
			if (allLabels.contains(new LabelData(image.label) )){
				currentLabel = getLabelData(image.label);
			}
			else {
				currentLabel = new LabelData(image.label);
				currentLabel.trueFeatures = new int[image.pixels.length][image.pixels[0].length];
				allLabels.add(currentLabel);
			}
			currentLabel.count++;
			//increment the count for each true feature/pixel in the trueFeatures matrix
			for (int i = 0; i < image.pixels.length; i++){
				for (int j = 0; j < image.pixels[0].length; j++){
					if (image.pixels[i][j] == true){
						currentLabel.trueFeatures[i][j]++;
					}
				}
			}
		}
	}
	
	/**
	 * easy way to get particular LabelData with just the int label.
	 * @param label whose data you want
	 * @return the data corresponding to the label
	 */
	public LabelData getLabelData(int label){
		return allLabels.get(allLabels.indexOf( new LabelData(label) ));
	}
	
	/**
	 * returns the percentage of images labeled with labelData.label from the
	 * training set. This is P(y) in the equation
	 * @param labelData
	 * @return
	 */
	private double priorDistribution(LabelData labelData){
		return (double)labelData.count / (double)trainingSet.size();
	}
	
	/**
	 * 
	 * @param i row corresponding to pixel matrix
	 * @param j column corresponding to pixel matrix
	 * @param value of pixel at i,j
	 * @param ld labelData of the label we are interested in
	 * @return how many times the pixel at i,j equals value for label ld.label
	 */
	private int getFeatureCount(int i, int j, boolean value, LabelData ld){
		if (value == true){
			return ld.trueFeatures[i][j];
		}
		return ld.count - ld.trueFeatures[i][j];
	}
	
	/**
	 * 
	 * @param i row corresponding to pixel matrix
	 * @param j column corresponding to pixel matrix
	 * @param value of pixel at i,j
	 * @param ld labelData of the label we are interested in
	 * @param k a constant to fine tune the probability. setting to 1 is a safe bet
	 * @return the probability that the pixel at i,j equals value for label ld.label
	 */
	private double probOfFeature(int i, int j, boolean value, LabelData ld, int k){
		return ((double)getFeatureCount(i, j, value, ld) + k) 
				/ (ld.count + (2 * k));
	}
	
	/**
	 * this is log(P(y) + summation[i=1->m](log(P(fi|y)). The probability that
	 * ld.label corresponds to this image
	 * @param ld labelData of the label we are interested in
	 * @param image the image we are trying to classify
	 * @param k a constant to fine tune the probability of each feature. setting to 1 is a safe 
	 * bet
	 * @return the natural log of the probability that this image can be classified as ld.label
	 */
	private double probOfLabel(LabelData ld, Image image, int k){
		double pd = priorDistribution(ld);
		double result = Math.log(pd);
		for (int i = 0; i < image.pixels.length; i++){
			for(int j = 0; j < image.pixels[0].length; j++){
				result += Math.log(probOfFeature(i, j, image.pixels[i][j], ld, k));
			}
		}
		return result;
	}
	
	/**
	 * Determines the probability of each label for this image and chooses the label
	 * which has the highest probability
	 * @param image the image we want to classify
	 * @param k a constant to fine tune the probability of each feature. setting to 1 is a safe 
	 * bet
	 * @return the most likely label of this image represented as an int
	 */
	public int classify(Image image, int k){
		double maxProb = Double.NEGATIVE_INFINITY;
		LabelData mostLikelyLabel = allLabels.get(0);
		for (LabelData ld: allLabels){
			double prob = probOfLabel(ld, image, k);
			if (prob > maxProb){
				maxProb = prob;
				mostLikelyLabel = ld;
			}
		}
		return mostLikelyLabel.label;
	}
	
	/**
	 * Counts how many times the naive bayes classification fails after testing every image in 
	 * the testing set then determines the percent error
	 * @param k a constant to fine tune the probability of each feature. setting to 1 is a safe 
	 * bet
	 * @return the percent error of this classification over all test images
	 */
	public double determineError(int k){
		double failCount = 0;
		for (Image image: testingSet){
			int computedLabel = classify(image, k);
			if (computedLabel != image.label){
				failCount += 1;
			}
		}
		return failCount / (double)testingSet.size();
	}
	
	/**
	 * Prints out test images, their true labels and the classified labels
	 * @param k a constant to fine tune the probability of each feature. setting to 1 is a safe 
	 * bet 
	 * @param numOfTestImages number of images from the testing set to print out
	 */
	public void demonstrate(int k, int numOfTestImages){
		if (numOfTestImages > testingSet.size()){
			numOfTestImages = testingSet.size() - 1;
		}
		
		for (int i = 0; i <= numOfTestImages; i++){
			Image image = testingSet.get(i);
			System.out.println("testImage " + i + " TL: " + image.label);
			image.printImage();
			System.out.println("classified as " + classify(image, 1) 
				+ "\n---------------------------------\n");
		}
	}
	
	
	

}
