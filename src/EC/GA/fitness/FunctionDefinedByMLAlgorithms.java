/**
 * 
 */
package EC.GA.fitness;

import java.util.HashMap;

import EC.GA.GAFitness;
import EC.GA.GAIndividual;

/**
 * @author AbyM
 *
 */
public class FunctionDefinedByMLAlgorithms extends GAFitness {
	
	int algorithmType;
	
	// HashMap to hold cached values of fitness for featureSets already encountered
	HashMap<String, Double> precomputedFitness;
	   
	public FunctionDefinedByMLAlgorithms(int algorithmType, HashMap<String, Double> precomputedFitness){
		this.algorithmType = algorithmType;
		this.precomputedFitness = precomputedFitness;
	}

	/* (non-Javadoc)
	 * @see EC.GA.GAFitness#evaluate(EC.GA.GAIndividual)
	 */
	@Override
	public void evaluate(GAIndividual individual) {
		individual.fitness = fitnessFunction(individual.variables);
	}

	private double fitnessFunction(double[] variables) {
		
		double result = 0;

		try {
			/*for(int j=0;j<x.length;j++){
				System.out.println(x[j]);

			}*/
			String key = "";
			for (int i = 0; i < variables.length; i++)
			{
				key += variables[i];
			}

			if(precomputedFitness != null && precomputedFitness.containsKey(key)){
				result = precomputedFitness.get(key);
			}
			else{
				if(precomputedFitness == null)
				{
					precomputedFitness = new HashMap<String, Double>();
				}
				result = new MLalgorithms.MLAlgorithms().runAlgorithm(variables, algorithmType);
				precomputedFitness.put(key, result);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
