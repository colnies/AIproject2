package classification;

public class LabelData {
	
	/** 
	 * identifier. if we are using faces 1=face 0=noFace. Digit labels correspond to
	 * their exact digit
	 */
	int label;
	/**
	 * used in perceptron. Gives a weight to each feature
	 */
	int[][] weightMatrix;
	/**
	 * used in naive bayes. Indicates how many training images were truly this label
	 */
	int count;
	/**
	 * used in naive bayes. Indicates how many times each feature is true in the training
	 * data for this particular label
	 */
	int[][] trueFeatures;
	
	public LabelData(int label){
		this.label = label;
	}
	
	public boolean equals(Object o){
		if (o instanceof LabelData)
			return ((LabelData)o).label == this.label;
		else
			return false;
	}
	
	public static void printMatrix(int[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	public void printTrueFeatures(){
		printMatrix(trueFeatures);
	}
	
	public void printWeightMatrix(){
		printMatrix(weightMatrix);
	}
}
