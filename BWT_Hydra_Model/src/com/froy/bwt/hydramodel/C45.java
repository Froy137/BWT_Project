package com.froy.bwt.hydramodel;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import com.froy.bwt.utils.WekaUtils;

public class C45 {

	/**
	 * Function reads data from CBP server, stores file in Database and moves
	 * processed file to "Files_in_DB" folder.
	 * 
	 * @param TableForTrainingData
	 *            {indicates what table to grab training data from}
	 * @param classIndex
	 *            {specify what index your are classifying}
	 * @param evaluate
	 * 			{perform the statistical evaluation of algorithm when 80% of data is taken for training and 20%
	 * 				is used as test data.}
	 * @param writeARFFFile
	 *            {set true to write ARFF file from entire
	 *            "TableForTrainingData" param
	 * @return possible object is {@link FilteredClassifier }
	 * 
	 */
	public FilteredClassifier classifier(String TableForTrainingData,
			int classIndex, boolean evaluate, boolean writeARFFFile)
			throws Exception {
		System.out.println("Starting Classifier");
		WekaUtils WU = new WekaUtils();
		Instances data = WU.getInstancesFromDB(TableForTrainingData);

		if (writeARFFFile == true) {
			WU.instanceToARFF(TableForTrainingData, data);
		}

		J48 j48 = new J48();
		// filter
		// Remove rm = new Remove();
		// rm.setAttributeIndices("1"); // remove 1st attribute
		// j48.setUnpruned(true); // using an unpruned J48
		FilteredClassifier fc = new FilteredClassifier();
		// fc.setFilter(rm);
		fc.setClassifier(j48);
		// System.out.println(j48.getCapabilities());
		data.setClassIndex(classIndex);

		if (evaluate == true) {
			Instances[] trainAndTest = WU.getTrainingAndTestData(0.8, data);
			Instances train = trainAndTest[0];
			Instances test = trainAndTest[1];

			// fc.buildClassifier(data);

			fc.buildClassifier(train);
			// evaluate classifier and print some statistic
			Evaluation eval = new Evaluation(train);

			eval.evaluateModel(fc, test);
			System.out.println(eval.toSummaryString("\nResults\n======\n",
					false));
		} else {
			fc.buildClassifier(data);
		}
		System.out.println("End of classifier");
		return fc;
	}

}
