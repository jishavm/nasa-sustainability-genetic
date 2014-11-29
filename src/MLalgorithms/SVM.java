package MLalgorithms;
import weka.classifiers.functions.SMOreg;
import weka.core.Instances;
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
		
//		CSVLoader loader = new CSVLoader();
//		loader.setSource(new File("/Users/jishavm/Documents/datasets/dataset6.csv"));
//		Instances data = loader.getDataSet();
//
//		
//		System.out.println(data);
//		SMOreg smoreg = new SMOreg();
//		smoreg.buildClassifier(data);
		
		String inputFilePath = "C:\\Users\\AbyM\\Dropbox\\Statistical Discovery and Learning\\Datasubsets\\RandomFeatureSet13.csv";
				
		try {
			
			CSVLoader loader = new CSVLoader();
		    loader.setSource(new File(inputFilePath));
		    Instances data = loader.getDataSet();
		    data.setClassIndex(data.numAttributes()-1);
			
			SMOreg model = new weka.classifiers.functions.SMOreg();
			
//			String svmOptions = "-C 1.0 -N 2 -I weka.classifiers.functions.supportVector.RegSMOImproved "
//			 		+ "-L 0.0010 -W	1 -P 1.0E-12 -T 0.0010 -V "
//			 		+ "-K weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\"";
//			String saveName = "C:\\Users\\AbyM\\Dropbox\\Statistical Discovery and Learning\\Datasubsets\\output.txt";
//			svmOptions += " -d " + saveName;
			
			//model.setOptions(weka.core.Utils.splitOptions(svmOptions));
			model.buildClassifier(data);
			
			System.out.println(model.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
