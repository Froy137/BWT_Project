package com.froy.bwt.hydramodel;

import weka.classifiers.meta.FilteredClassifier;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		C45 c45 = new C45();
		FilteredClassifier FC = c45.classifier("PortRecord", 21, true, false);

		// double result = FC.classifyInstance(args);

		// return args.classAttribute().value((int) result);

	}

}
