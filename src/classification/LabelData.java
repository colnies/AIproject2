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
	
	/**
	 * creates a new LabelData object. The weightMatrix and trueFeatures fields are not
	 * initialized.
	 * @param label
	 */
	public LabelData(int label){
		this.label = label;
	}
	
	/**
	 * two LabelData objects are equal iff their label fields are equal
	 */
	public boolean equals(Object o){
		if (o instanceof LabelData)
			return ((LabelData)o).label == this.label;
		else
			return false;
	}
	
	/**
	 * prints an integer matrix
	 * @param matrix a 2D array with constant length and width (there are x rows and y columns)
	 */
	public static void printMatrix(int[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	/**
	 * prints the true features matrix
	 */
	public void printTrueFeatures(){
		printMatrix(trueFeatures);
	}
	
	/**
	 * prints the weightMatrix
	 */
	public void printWeightMatrix(){
		printMatrix(weightMatrix);
	}
}
