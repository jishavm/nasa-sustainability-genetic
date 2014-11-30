package EC.GA.populationInitialization;

import EC.GA.*;
import EC.GA.fitness.*;

import java.util.*;

/**
 * Implements initialization of a population in a random way.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class RandomPopulationInitialization extends GAPopulationInitialization 
{
	/**
	 * Creates a random population initialization with null values.
	 */
	public RandomPopulationInitialization() 
	{
		individualsNumber = 0;
		variablesNumber = 0;
		variablesLowerBounds = null;
		variablesUpperBounds = null;
		scalingFactorUpperBound = 0;
	}

	/** 
	 * Creates a random population initialization with given values.
	 * <p>
	 * @param individualsNo number of individuals
	 * @param variablesNo number of genes for each individual
	 * @param vlb Lower bounds for individual's variables values
	 * @param vub Upper bounds for individual's variables values
	 * @param sfub Upper bound for scaling factor
	 */
	//   public RandomPopulationInitialization(int individualsNo, int variablesNo, double[] vlb, double[] vub, double sfub) 
	public RandomPopulationInitialization(int individualsNo, int variablesNo, double vlb, double vub, double sfub) 
	{
		individualsNumber = individualsNo;
		variablesNumber = variablesNo;
		//      variablesLowerBounds = vlb;
		variablesLowerBounds = new double[FunctionDefinedOnRealNumbers.functionDimension];
		Arrays.fill(variablesLowerBounds, vlb);
		//      variablesUpperBounds = vub;
		variablesUpperBounds = new double[FunctionDefinedOnRealNumbers.functionDimension];
		Arrays.fill(variablesUpperBounds, vub);
		scalingFactorUpperBound = sfub;
	}

	/**
	 * Initializes a population of individuals in a random way
	 * <p>
	 * @param fitness Allows fitness to be calculated for the new individuals.
	 * @return the new population
	 */
	public GAPopulation performInitialization(GAFitness fitness) 
	{
		GAPopulation newPopulation = new GAPopulation(individualsNumber);
		Random rand = new Random();
		//System.out.println("Initialising population");
		double[] auxVariables;

		// Initialize individuals
		for(int i=0; i<individualsNumber; i++) 
		{
			auxVariables = new double[variablesNumber];
			for(int j=0; j<auxVariables.length; j++) 
				auxVariables[j] = variablesLowerBounds[j] + rand.nextDouble() * (variablesUpperBounds[j] - variablesLowerBounds[j]);            
			newPopulation.individuals[i].variables = auxVariables; // Initialize real-valued variables
			newPopulation.individuals[i].scalingFactor = rand.nextDouble() * scalingFactorUpperBound;
			// Assign fitness to individual just created
			fitness.evaluate(newPopulation.individuals[i]); 
		}

		newPopulation.variablesLowerBounds = variablesLowerBounds;   
		newPopulation.variablesUpperBounds = variablesUpperBounds;
		newPopulation.areIndividualsSortedByFitness = false;
		//      newPopulation.calculateGlobalPopulationFitness();
		newPopulation.updateBestIndividual();
		newPopulation.entropy = newPopulation.calculateEntropy();
		newPopulation.feedbackCurrentScalingFactor = 0.8;
		return newPopulation;
	} 

}