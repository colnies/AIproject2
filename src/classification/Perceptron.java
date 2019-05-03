package classification;

import java.util.ArrayList;

/**
 * A Perceptron object can classify images given a training set. It uses the following
 * equation to determine the most likely label for a particular image. 
 * 
 * y'= argmax score(f,y'')
 * 
 * @author Colin Nies & Eric Zimmerman
*/

public class Perceptron {
	
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
	public Perceptron(ArrayList<Image> trainingSet, ArrayList<Image> testingSet ){
		this.trainingSet = trainingSet;
		this.testingSet = testingSet;
		allLabels = new ArrayList<LabelData>();
		initialize();
	}
	
	/**
	 * creates a Perceptron object and trains it on a percentage of the training set indicated 
	 * by isFaceType and percent
	 * @param isFaceType true = working with faces. false = working with digits
	 * @param percent the percentage of training images you want the machine to "learn" from.
	 * 0 <= percent <= 1
	 */
	public Perceptron(boolean isFaceType, double percent){
		this(Image.getTrainingImages(isFaceType, percent), Image.getTestingImages(isFaceType));
	}
	
	private void initialize(){
		
	}
	
	//returns the score of the image. the higher the score the more likely
	//it is classified correctly 
	private int getScore(boolean[][] image, int[][] weightMatrix){
		
		int score = 0;
		int x;
		
		for (int i=0; i<image.length; i++){
			for (int j=0; j<image[0].length; j++){
				x= pixelConv(image,i,j);
				score += x * weightMatrix[i][j];
			}
		}
		return score;
	}

	//returns the label whose weightMatrix generated the highest score for the image
	private LabelData computeLabel(Image i){
		
		int max = 0;
		int[][] wm;
		LabelData computedLabel = null;
		
		for(LabelData ld: allLabels) {
			
			//Get each weightMatrix for each label in allLabels, then calculate score//
			wm= ld.weightMatrix;
			int score = getScore(i.pixels, wm);
			
			//If weightMatrix higher score//
			if (score > max){	
				max = score;
				computedLabel = ld;
			}
		}
		return computedLabel;
	}

	//if the computed label was incorrect, the weightMatrix is updated for that label
	private boolean updateWeightMatricies(Image curr, LabelData computedLabel){
		
		//Checking labels//
		if (computedLabel.label == curr.label)
			return false;
		
		int [][] wm = computedLabel.weightMatrix;
		boolean[][] image= curr.pixels;
		int x;
		
		//WeightMatrix is updated for 
		for (int i=0; i<image.length; i++){
			for (int j=0; j<image[0].length;j++){
				x= pixelConv(image,i,j);
				wm[i][j] += x;
			}
		}
		
		int y;
		
		//For each other weightMatrix wm//
		for (LabelData ld: allLabels) {
			
			wm= ld.weightMatrix;
			
			for (int i=0; i<image.length; i++){
				for (int j=0; j<image[0].length;j++){
					y= pixelConv(image,i,j);
					wm[i][j] -= y;
				}
			}
		}
		return true;
	}
		

	//iterates through all the training data constantly updating the weightMatrix for 
	//each label until they are no longer updated or 2 minutes has passed. returns the 
	//amount of time passed. time passed is used in the report we have to write
	public int train(ArrayList<Image> trainingSet, double percent){
		
		int toIndex= trainingSet.size() * (int)percent;
		ArrayList<Image> subSet = (ArrayList<Image>) trainingSet.subList(0, toIndex);
		boolean hasUpdated = true;
		
		long start = System.currentTimeMillis();
		long res;
		
		while (System < 120 || !hasUpdate){
			hasUpdated = false;
			for (Image i: subSet) {
				if (updateWeightMatricies(i, computeLabel(i)) == true)
					hasUpdated = true;
			}
		}
		res= (System.currentTimeMillis() - start) / 1000F;
		System.out.println("Training Time: " + ((System.currentTimeMillis() - start) / 1000F) );
		
	} 

	//returns the accuracy over all the testImages
	public double test(ArrayList<Image> testingSet){
		
		double correctCount=0;
		
		for (Image curr: testingSet) {	
			LabelData labelCurr= computeLabel(curr.pixels);
			
			if (labelCurr.label == curr.label)
				correctCount++;
		}
		return correctCount / testingSet.size() ;
	}
	
	/*
	 * Helper function to convert true to 1; false to 0 for pixel array
	 */
	private int pixelConv(boolean[][] image,int x, int y) {
		int res;
		
		if(image[x][y]==true)
			res=1;
		else
			res=0;
			
		return res;
	}
}
