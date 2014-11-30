package EC.GA.populationInitialization;

import EC.GA.*;
import EC.GA.fitness.*;

import java.util.*;

/**
 * 
 * @author Jisha
 */
public class GetPopulationFromFile extends GAPopulationInitialization 
{
	/**
	 * Creates a random population initialization with null values.
	 */
	public GetPopulationFromFile() 
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
	//Jisha
	//individualsNo: The number of different subgroups
	//variablesNo: feature Set
	public GetPopulationFromFile(int individualsNo, int variablesNo, double vlb, double vub, double sfub) 
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
		
		
		
		GAPopulation p = new GAPopulation(5);
		
		// Initialize individuals
		//TODO:Jisha Need to read the data from file here
		for(int i=0; i<3; i++) 
		{
			GAIndividual gi = new GAIndividual();
			gi.variables = new double[10];
			for(int m=0;m<variablesNumber;m++){
				gi.variables[m] = 1;
			}
			gi.fitness = 0.5;
			gi.scalingFactor = 0.6;
			
			newPopulation.individuals[i] = gi;
			
			System.out.println(fitness.getClass());
			
			fitness.evaluate(newPopulation.individuals[i]); 
		}

		newPopulation.variablesLowerBounds = variablesLowerBounds;   
		newPopulation.variablesUpperBounds = variablesUpperBounds;
		newPopulation.areIndividualsSortedByFitness = false;
		//      newPopulation.calculateGlobalPopulationFitness();
		newPopulation.updateBestIndividual();
		//newPopulation.entropy = newPopulation.calculateEntropy();
		newPopulation.entropy = 0;
		newPopulation.feedbackCurrentScalingFactor = 0.8;
		return newPopulation;
	} 

}