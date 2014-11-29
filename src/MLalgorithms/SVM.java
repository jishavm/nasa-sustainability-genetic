package MLalgorithms;
import weka.classifiers.functions.SMOreg;
import weka.core.Utils;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.*;

import java.io.*;

public class SVM {
	public static void main(String args[]) throws Exception{
		svm();
	}
	
	public static void svm() throws Exception{
		
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File("/Users/jishavm/Documents/datasets/dataset6.csv"));
		Instances data = loader.getDataSet();

		
		System.out.println(data);
		SMOreg smoreg = new SMOreg();
		smoreg.buildClassifier(data);
		
		
	}

}
