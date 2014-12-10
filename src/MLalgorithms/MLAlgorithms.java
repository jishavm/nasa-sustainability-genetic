package MLalgorithms;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.*;

import EC.GA.GATests;

public class MLAlgorithms {
	
	public  double runAlgorithm(double[] featureSet, int algorithmType) throws Exception{
		
//		CSVLoader loader = new CSVLoader();
//		loader.setSource(new File("/Users/jishavm/Documents/datasets/dataset6.csv"));
//		Instances data = loader.getDataSet();
//
//		
//		System.out.println(data);
//		SMOreg smoreg = new SMOreg();
//		smoreg.buildClassifier(data);
		
		/* Data preparation.
		 *  1. Load the csv files of both train and test data sets
		 *  2. Loop through the featureSet to set the filters of both train and test sets
		*/
		
		//String trainFilePath = "C:\\Users\\AbyM\\Dropbox\\Statistical Discovery and Learning\\Datasubsets\\RandomFeatureSet13Train.csv";
		//String testFilePath = "C:\\Users\\AbyM\\Dropbox\\Statistical Discovery and Learning\\Datasubsets\\RandomFeatureSet13Test.csv";
		
		String trainFilePath = "data/Final_Train_10000.csv";
		String testFilePath = "data/Final_Test_10000.csv";		
		
		try {
			
			CSVLoader loader = new CSVLoader();
		    loader.setSource(new File(trainFilePath));
		    Instances trainData = loader.getDataSet();
		    
		    String[] options = new String[2];
		    options[0] = "-R";
		    options[1] = "";
		    
		    for(int i = 0; i < featureSet.length; i++)
		    {
		    	if(featureSet[i] < 0.5)
		    	{
		    		options[1] += options[1].length() == 0 ? (i+1) : "," + (i+1);	// Set Column numbers to remove
		    	}
		    }
		    
		    Remove remove = new Remove();
		    remove.setOptions(options);                           
		    remove.setInputFormat(trainData);
		    Instances filteredtrainData = Filter.useFilter(trainData, remove);	//Create new filtered data Instance
		    
		    
		    filteredtrainData.setClassIndex(filteredtrainData.numAttributes()-1);	// Set label column
			
		    switch(algorithmType)
		    {
		    case GATests.SVM_SMOREG:
		    	return svm(testFilePath, loader, remove, filteredtrainData);
		    case GATests.DECISION_TREE_REPTREE:
		    	return decisionTree(testFilePath, loader, remove, filteredtrainData);
		    default:
		    	return decisionTree(testFilePath, loader, remove, filteredtrainData);
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private double svm(String testFilePath, CSVLoader loader, Remove remove,
			Instances filteredtrainData) throws Exception, IOException {
		SMOreg model = new weka.classifiers.functions.SMOreg();

		//				String svmOptions = "-C 1.0 -N 2 -I weka.classifiers.functions.supportVector.RegSMOImproved "
		//		 		+ "-L 0.0010 -W	1 -P 1.0E-12 -T 0.0010 -V "
		//		 		+ "-K weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\"";
		//		String saveName = "C:\\Users\\AbyM\\Dropbox\\Statistical Discovery and Learning\\Datasubsets\\output.txt";
		//		svmOptions += " -d " + saveName;

		//model.setOptions(weka.core.Utils.splitOptions(svmOptions));
		model.buildClassifier(filteredtrainData);	// Generate model

		//System.out.println(model.toString());

		loader.setSource(new File(testFilePath));	// Set test file data
		Instances testData = loader.getDataSet();

		remove.setInputFormat(testData);
		Instances filteredTestData = Filter.useFilter(testData, remove);	// Create filtered test data

		filteredTestData.setClassIndex(filteredTestData.numAttributes()-1);	// Set label attribute on test data

		Evaluation evaluation = new Evaluation(filteredTestData);
		evaluation.evaluateModel(model, filteredTestData);			// Evaluate model based on test data
		System.out.println(evaluation.correlationCoefficient());
		return evaluation.correlationCoefficient();
	}

	private double decisionTree(String testFilePath, CSVLoader loader,
			Remove remove, Instances filteredtrainData) throws Exception,
			IOException {
		REPTree model = new REPTree();
		model.buildClassifier(filteredtrainData);	// Generate model

		//System.out.println(model.toString());

		loader.setSource(new File(testFilePath));	// Set test file data
		Instances testData = loader.getDataSet();

		remove.setInputFormat(testData);
		Instances filteredTestData = Filter.useFilter(testData, remove);	// Create filtered test data

		filteredTestData.setClassIndex(filteredTestData.numAttributes()-1);	// Set label attribute on test data

		Evaluation evaluation = new Evaluation(filteredTestData);
		evaluation.evaluateModel(model, filteredTestData);			// Evaluate model based on test data
		System.out.println(evaluation.correlationCoefficient());
		return evaluation.correlationCoefficient();
	}

}
