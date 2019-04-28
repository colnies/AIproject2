package classification;

public class Label {
	/** 
	 * identifier. if we are using faces 1=face 0=noFace. Digit labels correspond to
	 * their exact digit
	 */
	int id;
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
}
