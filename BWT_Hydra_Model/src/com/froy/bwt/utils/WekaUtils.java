package com.froy.bwt.utils;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.experiment.InstanceQuery;

public class WekaUtils {

	private String user = "root";
	private String pass = "mat";

	/**
	 * Function writes Instances into ARFF file on working Directory
	 * 
	 * @param ARFFFileName
	 *            FileName to be created
	 * @param data
	 *            Instances to be written
	 */
	public void instanceToARFF(String ARFFFileName, Instances data) {
		System.out.print("Saving Instances to ARFF file...");
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		try {
			saver.setFile(new File("./" + ARFFFileName + "arff"));
			saver.writeBatch();
			System.out.println("Done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Function returns a prepared set of Instances for training and for testing
	 * 
	 * @param dataPercentUsageForTraining
	 *            percentage of shuffled data used for training algorithm
	 * @param data
	 *            Total Instances to be used for training and testing.
	 * @return Instances [] object - index 0 has training data : index 1 has
	 *         testing data.
	 */
	public Instances[] getTrainingAndTestData(
			double dataPercentUsageForTraining, Instances data) {
		System.out.print("Partitioning Training and Test data...");
		data.randomize(new java.util.Random(0));
		int trainSize = (int) Math.round(data.numInstances()
				* dataPercentUsageForTraining);
		int testSize = data.numInstances() - trainSize;
		Instances train = new Instances(data, 0, trainSize);
		Instances test = new Instances(data, trainSize, testSize);

		Instances[] dataAndTest = { train, test };
		System.out.println("Done");
		return dataAndTest;

	}

	public Instances getInstancesFromDB(String table) throws Exception {
		System.out.print("Getting Instances from DB....");
		InstanceQuery query = new InstanceQuery();
		query.setUsername(user);
		query.setPassword(pass);

		query.setQuery("select * from " + table);
		Instances data = query.retrieveInstances();
		System.out.println("Done!");
		System.out.println("Number of Training records:" + data.size());
		return data;

	}
}
