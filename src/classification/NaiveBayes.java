package classification;

import java.util.ArrayList;

public class NaiveBayes {
	ArrayList<Image> trainingSet;
	ArrayList<Image> testingSet;
	ArrayList<LabelData> allLabels;
	
	public NaiveBayes(ArrayList<Image> trainingSet, ArrayList<Image> testingSet ){
		this.trainingSet = trainingSet;
		this.testingSet = testingSet;
		allLabels = new ArrayList<LabelData>();
		initialize();
	}
	
	public NaiveBayes(boolean isFaceType, double percent){
		this(Image.getTrainingImages(isFaceType, percent), Image.getTestingImages(isFaceType));
	}
	
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
	
	private LabelData getLabelData(int label){
		return allLabels.get(allLabels.indexOf( new LabelData(label) ));
	}
	
	public double priorDistribution(LabelData labelData){
		return (double)labelData.count / (double)trainingSet.size();
	}
	
	public int getFeatureCount(int i, int j, boolean value, LabelData ld){
		if (value == true){
			return ld.trueFeatures[i][j];
		}
		return ld.count - ld.trueFeatures[i][j];
	}
	
	public double probOfFeature(int i, int j, boolean value, LabelData ld, int k){
		return ((double)getFeatureCount(i, j, value, ld) + k) 
				/ (ld.count + (2 * k));
	}
	
	public double probOfLabel(LabelData ld, Image image, int k){
		double pd = priorDistribution(ld);
		double result = Math.log(pd);
		for (int i = 0; i < image.pixels.length; i++){
			for(int j = 0; j < image.pixels[0].length; j++){
				result += Math.log(probOfFeature(i, j, image.pixels[i][j], ld, k));
			}
		}
		return result;
	}
	
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
	
	public void demonstrate(int k, int numOfTestImages){
		if (numOfTestImages > testingSet.size()){
			numOfTestImages = testingSet.size() - 1;
		}
		
		for (int i = 0; i <= numOfTestImages; i++){
			Image image = testingSet.get(i);
			System.out.println("testImage " + i + " TL: " + image.label);
			image.printImage();
			System.out.println("classified as " + classify(image, 1) +
					"\n---------------------------------\n");
		}
	}
	
	
	

}
