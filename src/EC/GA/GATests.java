package EC.GA;

import java.io.*;
import java.util.*;

import EC.GA.clustering.*;
import EC.GA.populationInitialization.*;
import EC.GA.fitness.*;
import EC.GA.parentSelection.*;
import EC.GA.crossover.*;
import EC.GA.mutation.*;
import EC.GA.crowding.*;
import EC.GA.survivorSelection.*;
import EC.tools.*;

/**
 * Main class implementing the tests with a genetic algorithm
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class GATests
{

	// Fitness type

	/** Constant indicating the maximization of a function defined on real numbers: 1 */
	public static final int FUNCTION_DEFINED_ON_REAL_NUMBERS = 1;
	
	/** Constant indicating the maximization of a function by running machine learning algorithm, SVM, on it: 2 */
	public static final int FUNCTION_DEFINED_BY_ML_ALGORITHMS_SVM = 2;
	
	/** Constant indicating the maximization of a function by running machine learning algorithm, Decision Tree, on it: 3 */
	public static final int FUNCTION_DEFINED_BY_ML_ALGORITHMS_DECISION_TREE = 3;
	
	
	// Kinds of machine learning algorithms used in Fitness type
	/** Constant indicating using the SVM machine learning algorithm: 1 */
	public static final int SVM_SMOREG = 1;
	
	/** Constant indicating using the Decision Tree machine learning algorithm: 2 */
	public static final int DECISION_TREE_REPTREE = 2;

	// Termination condition type

	/** Constant indicating termination after a given number of generations */
	public static final int GENERATIONS_CONDITION = 1;

	/** Constant indicating termination after a given number of generations without change in the fitness for the best individual */
	public static final int BEST_INDIVIDUAL_CONDITION = 2;



	// Population initialization type

	/** Constant indicating a random population initialization type: 1 */
	public static final int RANDOM_POPULATION_INITIALIZATION = 1;


	// Parent selection type

	/** Constant indicating a random parent selection type: 1 */
	public static final int RANDOM_PARENT_SELECTION = 1;

	/** Constant indicating a ranking parent selection type: 2 */
	public static final int RANKING_PARENT_SELECTION = 2;

	/** Constant indicating a tournament parent selection type: 3 */
	public static final int TOURNAMENT_PARENT_SELECTION = 3;




	// Crossover type

	/** Constant indicating a one-point crossover type: 1 */
	public static final int ONE_POINT_CROSSOVER = 1;

	/** Constant indicating a two-point crossover type: 2 */
	public static final int TWO_POINT_CROSSOVER = 2;

	/** Constant indicating an N-point crossover type: 3 */
	public static final int N_POINT_CROSSOVER = 3;

	/** Constant indicating a parameterized uniform crossover type: 4 */
	public static final int PARAMETERIZED_UNIFORM_CROSSOVER = 4;

	/** Constant indicating an (biased and deterministic) arithmetic recombination type: 5 */
	public static final int WHOLE_ARITHMETIC_RECOMBINATION_1 = 5;

	/** Constant indicating an (unbiased and stochastic) arithmetic recombination type: 6 */
	public static final int WHOLE_ARITHMETIC_RECOMBINATION_2 = 6;



	// Mutation type

	/** Constant indicating a uniform mutation type: 1 */
	public static final int UNIFORM_MUTATION = 1;

	/** Constant indicating a Gaussian mutation type: 2 */
	public static final int GAUSSIAN_MUTATION = 2;



	// Crowding type

	/** Constant indicating a type of crowding under deterministic replacement: 1 */
	public static final int DETERMINISTIC_REPLACEMENT = 1;

	/** Constant indicating a type of crowding under probabilistic replacement: 2 */
	public static final int PROBABILISTIC_REPLACEMENT = 2;

	/** Constant indicating a type of crowding under hybrid replacement: 3 */
	public static final int HYBRID_REPLACEMENT = 3;

	/** Constant indicating a type of crowding under Boltzmann replacement: 4 */
	public static final int BOLTZMANN_REPLACEMENT = 4;

	/** Constant indicating a type of crowding under Metropolis replacement: 5 */
	public static final int METROPOLIS_REPLACEMENT = 5;

	/** Constant indicating a type of crowding under generalized probabilistic replacement: 6 */
	public static final int GENERALIZED_PROBABILISTIC_REPLACEMENT = 6;



	// Schedule (for the scaling factor) type

	/** Constant indicating a type of schedule which keeps a constant scaling factor: 1 */
	public static final int CONSTANT_SCHEDULE = 1;

	/** Constant indicating a type of schedule which uses entropy to drive scaling factor lowering: 2 */
	public static final int ENTROPY_SCHEDULE = 2;

	/** Constant indicating a type of schedule which makes the scaling factor decay exponentially: 3 */
	public static final int EXPONENTIAL_SCHEDULE = 3;

	/** Constant indicating a type of schedule which self-adapts the scaling factor: 4 */
	public static final int SELF_ADAPTIVE_SCHEDULE = 4;

	// Schedule feedback control // written by JUN
	public static final int FEEDBACK_CONTROL_SCHEDULE = 5;



	// Survivor selection type

	/** Constant indicating a generational-model survivor selection type: 1 */
	public static final int GENERATIONAL_MODEL = 1;

	/** Constant indicating a steady-state-model survivor selection type: 2 */
	public static final int STEADY_STATE_MODEL = 2;

	/** Constant identifying (mu+lambda) survivor selection: 3 */
	public static final int PLUS_SELECTION = 3;

	/** Constant identifying (mu,lambda) survivor selection: 4 */
	public static final int COMMA_SELECTION = 4;




	// Elitism

	/** Constant indicating elitism will be applied: 1 */
	public static final int ELITISM_ON = 1;

	/** Constant indicating elitism will not be applied: 2 */
	public static final int ELITISM_OFF = 2;


	// Clustering  // Written by Jun
	public static final int KMEANS_CLUSTERING = 1;
	public static final int KMEANS_CLUSTERING_CROSS_VALIDATION = 2;
	public static final int KMEANS_CLUSTERING_1 = 3;



	/**
	 * Executes the genetic algorithm 
	 * <p>
	 * @param fileName the file containing the parameters for the execution of the genetic algorithm
	 * @param numberOfExperimenets number of runs for the Bayesian network
	 * @param optimumFitness optimum fitness obtained through Bayesian network software
	 * @param areReportsMade wheter or not the following files will be generated: 
	 *    1) Fitness of best individual generation by generation (in file bestIndivReport.txt)    
	 *    1) Entropy of population generation by generation (in file EntropyOverPopulation.txt)    
	 */
	public GATests(String fileName, int numberOfExperiments, double optimumFitness, boolean areReportsMade) throws IOException
	{
		GAParametersReader pr;
		GAFitness fitnessType;
		GAPopulationInitialization populationInitializationType;
		GAParentSelection parentSelectionType;
		GACrossover crossoverType;
		GAMutation mutationType;
		GACrowding crowdingType;
		GASurvivorSelection survivorSelectionType;
		boolean elitism;
		GAClustering clusteringType;
		GAIndividual bestIndividual; // Best individual in the current population
		double initialEntropy; // Entropy of the initial population in the GA
		GAIndividual bestIndividualSoFar; // Best individual found so far by the GA
		int generation; 
		double currentTemperature; // Temperature assigned to the current generation according to the cooling schedule in Boltzmann or Metropolis replacement
		int generationsWithoutChangeInBestIndividualFitness;
		boolean isTerminationConditionSatisfied;
		GAPopulation currentPopulation, offspringPopulation;
		//      Date date;
		//      double time; // Execution time for each run
		//      double[] timeForTest = new double[numberOfExperiments]; // Stores the run time for each test
		int[] generationsForTest = new int[numberOfExperiments]; // Stores the generations each test takes
		int numberOfSuccessRuns = 0; // For determining the percentage of runs reaching the optimum fitness
		//      ArrayList<Double>[] bestIndividualFitness = new ArrayList[numberOfExperiments]; // Stores, for each run, the fitness of the best individual in the population generation by generation
		ArrayList<Double>[] Entropy = new ArrayList[numberOfExperiments]; // Stores, for each run, the normalized entropy over the current population
		ArrayList<Double>[] bestIndividualFitnessSoFar = new ArrayList[numberOfExperiments]; // Stores, for each run, the fitness of the best individual found so far by the GA generation by generation
		//      PrintWriter pw1; 
		PrintWriter pw2;
		PrintWriter pw3;
		//      double[] doubleAuxArray1 = new double[numberOfExperiments];
		double[] doubleAuxArray2 = new double[numberOfExperiments];
		double[] doubleAuxArray3 = new double[numberOfExperiments];
		//double[][] lastPopulations; // For Ole's reports and one-dimensional functions. If you had n-dimensional functions, use 'double[][][] lastPopulations;', being the last dimension the values of the real variables of each individual.
		ArrayList<Double>[] populationMeanScalingFactor = new ArrayList[numberOfExperiments]; // Stores, for each run, mean scaling factor in the population generation by generation 
		PrintWriter pw4, pw5;
		double[] doubleAuxArray4 = new double[numberOfExperiments];


		// Read parameters from file 
		pr = new GAParametersReader(); 
		if(!pr.readParameters(fileName)) // Parameters reading unsuccessful
			return;

		// Determine type of fitness 
		switch(pr.fitnessTypeReadFromFile) 
		{
		case FUNCTION_DEFINED_ON_REAL_NUMBERS:
			fitnessType = new FunctionDefinedOnRealNumbers();
			break;
		case FUNCTION_DEFINED_BY_ML_ALGORITHMS_SVM:
			fitnessType = new FunctionDefinedByMLAlgorithms(SVM_SMOREG); 
			break;
		case FUNCTION_DEFINED_BY_ML_ALGORITHMS_DECISION_TREE:
			fitnessType = new FunctionDefinedByMLAlgorithms(DECISION_TREE_REPTREE);
			break;
		default:
			fitnessType = null;
		}

		// Determine type of population initialization
		switch(pr.populationInitializationTypeReadFromFile) 
		{
		case RANDOM_POPULATION_INITIALIZATION: 
			/* populationInitializationType = new RandomPopulationInitialization(pr.individualsNumberReadFromFile, 
                                                                              FunctionDefinedOnRealNumbers.functionDimension,
                                                                              FunctionDefinedOnRealNumbers.x_i, 
                                                                              FunctionDefinedOnRealNumbers.x_f,
                                                                              pr.initialScalingFactorReadFromFile); // Maximum scaling factor for self-adaptive crowding
			 */
			populationInitializationType = new GetPopulationFromFile(pr.individualsNumberReadFromFile, 
					FunctionDefinedOnRealNumbers.functionDimension,
					FunctionDefinedOnRealNumbers.x_i, 
					FunctionDefinedOnRealNumbers.x_f,
					pr.initialScalingFactorReadFromFile);
			break;
		default:
			populationInitializationType = null;

		}

		// Determine type of parent selection
		switch(pr.parentSelectionTypeReadFromFile) 
		{
		case RANDOM_PARENT_SELECTION: 
			parentSelectionType = new RandomParentSelection();
			break;
		case RANKING_PARENT_SELECTION: 
			parentSelectionType = new RankingParentSelection(pr.expectedOffspringNumberForFittestIndividualReadFromFile);
			break;
		case TOURNAMENT_PARENT_SELECTION: 
			parentSelectionType = new TournamentParentSelection(pr.tournamentSizeReadFromFile);
			break;
		default:
			parentSelectionType = null;
		}

		// Determine type of crossover
		switch(pr.crossoverTypeReadFromFile) 
		{
		case ONE_POINT_CROSSOVER:
			crossoverType = new OnePointCrossover(pr.crossoverRateReadFromFile);
			break;
		case TWO_POINT_CROSSOVER:
			crossoverType = new TwoPointCrossover(pr.crossoverRateReadFromFile);
			break;
		case N_POINT_CROSSOVER:
			crossoverType = new NPointCrossover(pr.crossoverRateReadFromFile, pr.crossoverPointsNumberReadFromFile);
			break;
		case PARAMETERIZED_UNIFORM_CROSSOVER:
			crossoverType = new ParameterizedUniformCrossover(pr.crossoverRateReadFromFile, pr.exchangeProbabilityReadFromFile);
			break;
		case WHOLE_ARITHMETIC_RECOMBINATION_1:
			crossoverType = new WholeArithmeticRecombination1(pr.crossoverRateReadFromFile, pr.alphaDisruptionReadFromFile);
			break;
		case WHOLE_ARITHMETIC_RECOMBINATION_2:
			crossoverType = new WholeArithmeticRecombination2(pr.crossoverRateReadFromFile);
			break;
		default:
			crossoverType = null;
		}

		// Determine type of mutation
		switch(pr.mutationTypeReadFromFile) 
		{
		case UNIFORM_MUTATION: 
			mutationType = new UniformMutation(pr.mutationRateReadFromFile, pr.initialScalingFactorReadFromFile);
			break;
		case GAUSSIAN_MUTATION: 
			mutationType = new GaussianMutation(pr.mutationRateReadFromFile, pr.sigmaStepSizeReadFromFile, pr.initialScalingFactorReadFromFile);
			break;
		default:
			mutationType = null;
		}

		// Determine type of crowding
		switch(pr.crowdingTypeReadFromFile) 
		{
		case DETERMINISTIC_REPLACEMENT:
			crowdingType = new DeterministicReplacement(pr.SReadFromFile);
			break;
		case PROBABILISTIC_REPLACEMENT:
			crowdingType = new ProbabilisticReplacement(pr.SReadFromFile);
			break;
		case HYBRID_REPLACEMENT:
			crowdingType = new HybridReplacement(pr.SReadFromFile);
			break;
		case BOLTZMANN_REPLACEMENT:
			crowdingType = new BoltzmannReplacement(pr.SReadFromFile);
			break;
		case METROPOLIS_REPLACEMENT:
			crowdingType = new MetropolisReplacement(pr.SReadFromFile);
			break;
		case GENERALIZED_PROBABILISTIC_REPLACEMENT:
			crowdingType = new GeneralizedProbabilisticReplacement(pr.SReadFromFile);
			break;
		default:
			crowdingType = null;
		}

		// Determine type of survivor selection
		switch(pr.survivorSelectionTypeReadFromFile) 
		{
		case STEADY_STATE_MODEL: 
			survivorSelectionType = new SteadyStateModelSurvivorSelection();
			break;
		case PLUS_SELECTION: // (mu+lambda) selection
			survivorSelectionType = new MuPlusLambdaSurvivorSelection();
			break;
		case COMMA_SELECTION: // (mu,lambda) selection
			survivorSelectionType = new MuCommaLambdaSurvivorSelection();
			break;
		default:
			survivorSelectionType = null;
		}

		// Determine elitism (for generational genetic algorithms)
		switch(pr.elitismReadFromFile) 
		{
		case ELITISM_ON:
			elitism = true;
			break;
		case ELITISM_OFF:
			elitism = false;
			break;
		default:
			elitism = false;
		}

		//
		switch(pr.clusteringTypeReadFromFile) 
		{
		case KMEANS_CLUSTERING:
			clusteringType = new KMeansClustering();
			break;
		case KMEANS_CLUSTERING_CROSS_VALIDATION:
			clusteringType = new KMeansClustering1();
			break;
		case KMEANS_CLUSTERING_1:
			clusteringType = new KMeansClustering1();
			break;
		default:
			clusteringType = new KMeansClustering();
		}

		//lastPopulations = new double[numberOfExperiments][pr.individualsNumberReadFromFile]; // For Ole's reports

		//      System.out.println("Press a key to start.");
		//      try 
		//      {
		//         System.in.read();
		//      } 
		//      catch(IOException ioe) 
		//      {}

		int totalGenerations = pr.terminationConditionReadFromFile == GENERATIONS_CONDITION ? pr.generationsNumberReadFromFile + 1 : 501;

		int[][] NumberOfClusters = new int[numberOfExperiments][totalGenerations];
		
		double[][][] generationClusterFitness = new double[numberOfExperiments][pr.generationsNumberReadFromFile][10];

		// *** Beginning of the algorithm ***
		for(int i=0; i<numberOfExperiments; i++)
		{


			// Written by JUN 
			// need to transfer scaling factor from last generation to the current one
			double FeedbackScalingFactor = pr.initialScalingFactorReadFromFile;
			int setpoint = pr.clusterNumberReadFromFile;
			int reachSetpoint = 0;
			//System.out.println("Number of Experiments" + i);
			int currentNumberOfClusters = pr.individualsNumberReadFromFile;
			//int currentNumberOfClusters = 5;


			generation = 1; 
			generationsWithoutChangeInBestIndividualFitness = 0;
			isTerminationConditionSatisfied = false;
			//         date = new Date();
			//         time = (double)date.getTime();

			currentPopulation = populationInitializationType.performInitialization(fitnessType);
			bestIndividual = currentPopulation.bestIndividual;
			initialEntropy = currentPopulation.entropy;
			bestIndividualSoFar = currentPopulation.bestIndividual;
			if(areReportsMade)
			{ 
				// Report 1: Best Fitness 
				//            bestIndividualFitness[i] = new ArrayList<Double>();
				//            bestIndividualFitness[i].add(new Double(currentPopulation.bestIndividual.fitness));

				// Report 2: Entropy over the current population
				Entropy[i] = new ArrayList<Double>();            
				Entropy[i].add(new Double(currentPopulation.entropy));

				// Report 3: Best Fitness So Far
				bestIndividualFitnessSoFar[i] = new ArrayList<Double>();
				bestIndividualFitnessSoFar[i].add(new Double(bestIndividualSoFar.fitness));

				// Report 4: Scaling Factor
				populationMeanScalingFactor[i] = new ArrayList<Double>();
				populationMeanScalingFactor[i].add(new Double(currentPopulation.meanScalingFactor()));

			}
			offspringPopulation = null; 
			double[][] clusterFitness = new double[10][10];

			// written by JUN
			// we need clustering methods here to cluster the population to several clusters
			// consider using k-means cluster first
			// hope we could use an algorithm to identify clusters of solutions in multimodal optimization written by Ballester and Carter later
			// currentNumberOfClusters = clusteringType.Clustering(currentPopulation, pr.clusterNumberReadFromFile);

			//System.out.println("reachSetpoint"+reachSetpoint);
			NumberOfClusters[i][1]=clusteringType.Clustering(currentPopulation, pr.clusterNumberReadFromFile, generation, clusterFitness);
			if(currentNumberOfClusters == pr.clusterNumberReadFromFile)
				reachSetpoint++;

			while(!isTerminationConditionSatisfied) // Main loop
			{
				//System.out.println("Number of generations = " + generation);
				//System.out.println("current scaling factor = " + currentPopulation.feedbackCurrentScalingFactor);
				//System.out.println("current number of clusters = "+currentNumberOfClusters);
				//System.out.println();

				//NumberOfClusters[i][generation]=currentNumberOfClusters;

				generation++;

				// Parent selection (in order to construct the mating pool of individuals)
				switch(pr.survivorSelectionTypeReadFromFile) 
				{
				case GENERATIONAL_MODEL: 
					offspringPopulation = parentSelectionType.performParentSelection(currentPopulation, currentPopulation.individuals.length);
					break;
				case STEADY_STATE_MODEL: 
					offspringPopulation = parentSelectionType.performParentSelection(currentPopulation, pr.generationalGapReadFromFile);
					break;
				case PLUS_SELECTION: 
					offspringPopulation = parentSelectionType.performParentSelection(currentPopulation, pr.lambdaReadFromFile);
					break;
				case COMMA_SELECTION: 
					offspringPopulation = parentSelectionType.performParentSelection(currentPopulation, pr.lambdaReadFromFile);
					break;
				}

				// Crossover
				crossoverType.performCrossover(offspringPopulation); // Fitnesses of individuals are not calculated yet    

				// Mutation
				mutationType.performMutation(fitnessType, offspringPopulation);

				// Crowding
				if(crowdingType.getClass() == BoltzmannReplacement.class)
					((BoltzmannReplacement)crowdingType).currentTemperature = pr.initialTemperatureReadFromFile * Math.pow(pr.coolingConstantReadFromFile, (generation-2)/pr.coolingPeriodReadFromFile); // Cooling schedule
				else if(crowdingType.getClass() == MetropolisReplacement.class)
					((MetropolisReplacement)crowdingType).currentTemperature = pr.initialTemperatureReadFromFile * Math.pow(pr.coolingConstantReadFromFile, (generation-2)/pr.coolingPeriodReadFromFile); // Cooling schedule
				else if(crowdingType.getClass() == GeneralizedProbabilisticReplacement.class)
				{
					if(pr.scheduleTypeReadFromFile == CONSTANT_SCHEDULE)   
						((GeneralizedProbabilisticReplacement)crowdingType).currentScalingFactor = pr.initialScalingFactorReadFromFile;
					else if(pr.scheduleTypeReadFromFile == ENTROPY_SCHEDULE)
						((GeneralizedProbabilisticReplacement)crowdingType).currentScalingFactor = (pr.initialScalingFactorReadFromFile * currentPopulation.entropy) / initialEntropy;
					else if(pr.scheduleTypeReadFromFile == EXPONENTIAL_SCHEDULE)
						((GeneralizedProbabilisticReplacement)crowdingType).currentScalingFactor = pr.initialScalingFactorReadFromFile * Math.pow(pr.decayConstantReadFromFile, generation-1);
					else if(pr.scheduleTypeReadFromFile == SELF_ADAPTIVE_SCHEDULE)
						((GeneralizedProbabilisticReplacement)crowdingType).currentScalingFactor = -1; // I know it sounds weird, but -1 here means self-adaptive scaling factor
						else if(pr.scheduleTypeReadFromFile == FEEDBACK_CONTROL_SCHEDULE)
							((GeneralizedProbabilisticReplacement)crowdingType).currentScalingFactor = -2;

				}

				/*
            if(generation<=500)
            {
            	setpoint = 3;
            	pr.clusterNumberReadFromFile = 3;
            }
            else if(generation<=500)
            {
            	setpoint = 3;
            	pr.clusterNumberReadFromFile = 3;
            }
            else
            {
            	setpoint = 5;
            	pr.clusterNumberReadFromFile = 5;
            }
				 */

				if(setpoint<currentNumberOfClusters & generation%pr.controlFrequencyReadFromFile==0)
				{
					FeedbackScalingFactor = currentPopulation.feedbackCurrentScalingFactor+pr.controlParameterReadFromFile*(setpoint-currentNumberOfClusters);            		
					if(FeedbackScalingFactor<0)
						FeedbackScalingFactor = 0;
				}

				else if(setpoint>currentNumberOfClusters & generation%pr.controlFrequencyReadFromFile==0)
					FeedbackScalingFactor = currentPopulation.feedbackCurrentScalingFactor+pr.controlParameterReadFromFile*(setpoint-currentNumberOfClusters);            		
				else
					FeedbackScalingFactor = currentPopulation.feedbackCurrentScalingFactor;


				crowdingType.performCrowding(currentPopulation, offspringPopulation, crossoverType.recombinedParents, crossoverType.childrenIndexPool, setpoint, currentNumberOfClusters, FeedbackScalingFactor);


				currentPopulation.feedbackCurrentScalingFactor=FeedbackScalingFactor;
				offspringPopulation.feedbackCurrentScalingFactor=FeedbackScalingFactor;

				// Survivor selection
				switch(pr.survivorSelectionTypeReadFromFile) 
				{
				case GENERATIONAL_MODEL: 
					if(elitism) 
					{
						if(!offspringPopulation.updateWithElitistIndividual(currentPopulation.bestIndividual)) 
						{
							offspringPopulation.areIndividualsSortedByFitness = false;
							offspringPopulation.updateBestIndividual();
						}   
						//                     offspringPopulation.calculateGlobalPopulationFitness();
					} 
					else 
					{
						offspringPopulation.areIndividualsSortedByFitness = false;
						offspringPopulation.updateBestIndividual();
						//                     offspringPopulation.calculateGlobalPopulationFitness();
					}
					currentPopulation = offspringPopulation;
					currentPopulation.entropy = currentPopulation.calculateEntropy();
					break;
				case STEADY_STATE_MODEL: 
					survivorSelectionType.performSurvivorSelection(currentPopulation, offspringPopulation);
					break;
				case PLUS_SELECTION: 
					survivorSelectionType.performSurvivorSelection(currentPopulation, offspringPopulation);
					break;
				case COMMA_SELECTION: 
					survivorSelectionType.performSurvivorSelection(currentPopulation, offspringPopulation);
					break;
				}

				// written by JUN
				// we need clustering methods here to cluster the population to several clusters
				// consider using k-means cluster first
				// hope we could use an algorithm to identify clusters of solutions in multimodal optimization written by Ballester and Carter later
				currentNumberOfClusters = clusteringType.Clustering(currentPopulation, pr.clusterNumberReadFromFile, generation, clusterFitness);
				if(currentNumberOfClusters == pr.clusterNumberReadFromFile)
					reachSetpoint++;
				
				boolean condition = pr.terminationConditionReadFromFile == GENERATIONS_CONDITION ? 
						(currentPopulation.bestIndividual.hasSameFitnessAs(bestIndividual)) : 
							checkChangeInGenerationFitness(pr, bestIndividual,
									currentPopulation, currentNumberOfClusters, clusterFitness[currentNumberOfClusters-1], optimumFitness);
				
				if(condition)
				{
					generationClusterFitness[i][generationsWithoutChangeInBestIndividualFitness] = (clusterFitness[currentNumberOfClusters-1]).clone();
					generationsWithoutChangeInBestIndividualFitness++;
				}
				else
				{  
					for(int m = 0; m < generationsWithoutChangeInBestIndividualFitness; m++)
					{
						generationClusterFitness[i][m] = new double[10];
					}
					generationsWithoutChangeInBestIndividualFitness = 0;
					bestIndividual = currentPopulation.bestIndividual;
				}

				NumberOfClusters[i][generation]=currentNumberOfClusters;
				//System.out.println("reachSetpoint"+reachSetpoint);
				//System.out.println(generation);


				switch(pr.terminationConditionReadFromFile)
				{
				case GENERATIONS_CONDITION:
					if(generation == pr.generationsNumberReadFromFile)
						isTerminationConditionSatisfied = true;
					break;
				case BEST_INDIVIDUAL_CONDITION:
					if(generationsWithoutChangeInBestIndividualFitness == pr.generationsNumberReadFromFile || generation == 500 )
					//&&  currentNumberOfClusters == pr.clusterNumberReadFromFile)
						isTerminationConditionSatisfied = true;
					if(generationsWithoutChangeInBestIndividualFitness == pr.generationsNumberReadFromFile)
					{
						NumberOfClusters[i][0] = 1;
					}
					break;
				}            

				if(areReportsMade) 
				{
					//               bestIndividualFitness[i].add(new Double(bestIndividual.fitness));
					Entropy[i].add(new Double(currentPopulation.entropy));
					if(bestIndividual.fitness > bestIndividualFitnessSoFar[i].get(bestIndividualFitnessSoFar[i].size()-1)) // We are maximizing...
					{
						bestIndividualFitnessSoFar[i].add(new Double(bestIndividual.fitness));
						bestIndividualSoFar = bestIndividual;
					}
					else
						bestIndividualFitnessSoFar[i].add(new Double(bestIndividualFitnessSoFar[i].get(bestIndividualFitnessSoFar[i].size()-1)));
					populationMeanScalingFactor[i].add(new Double(currentPopulation.meanScalingFactor()));
				}

				//System.out.println("current scaling factor = " + currentPopulation.feedbackCurrentScalingFactor);
				//System.out.println(currentNumberOfClusters);
			} // end of while

			// written by JUN
			//System.out.println();
			//System.out.println();


			//System.out.println();
			//System.out.println("reachSetpoint = "+reachSetpoint);

			//         date = new Date();
			//         time = (double)date.getTime()-time;
			//         timeForTest[i] = time;
			generationsForTest[i] = generation;
			if(pr.terminationConditionReadFromFile == GENERATIONS_CONDITION)
			{
				if(bestIndividual.fitness>=optimumFitness)
					numberOfSuccessRuns++;
			}
			else
			{
				if(NumberOfClusters[i][0] == 1)
				{
					numberOfSuccessRuns++;
				}
			}

			// For Ole's reports
			//if(areReportsMade)
			//{   
			//	for(int j=0; j<currentPopulation.individuals.length; j++)

			//      lastPopulations[i][j] = currentPopulation.individuals[j].variables[0]; // This is for one-dimensional functions. For n-dimensional functions, we should store the set of real values for the individual
			//}

			//System.out.println("Experiment: " + (i+1));
			//System.out.println("Best Individual: ");
			//currentPopulation.bestIndividual.print();
			//System.out.println("Best Individual Fitness So Far:");
			//bestIndividualSoFar.print();
			//System.out.println();

		} // End of for
		
		if(pr.terminationConditionReadFromFile == GENERATIONS_CONDITION){
			double overallClusters = 0;
			
			for (int i=0; i<numberOfExperiments;i++)
			{
				double overallClusterPerGeneration = 0;
				for(int n=0; n<pr.generationsNumberReadFromFile;n++)
				{
					System.out.print(NumberOfClusters[i][n+1]);
					if(n > (pr.generationsNumberReadFromFile*0.75)){
						overallClusterPerGeneration += NumberOfClusters[i][n+1];
					}
				}
				System.out.println();
				System.out.println((overallClusterPerGeneration/(pr.generationsNumberReadFromFile*.25)));
				overallClusters += (overallClusterPerGeneration/(pr.generationsNumberReadFromFile*.25));
			}
			
			System.out.println((overallClusters/numberOfExperiments));
		}
		else{
			double totalRunsAll = 0;
			double totalGenerationsInRunsCompleted = 0;
			int runsCompletedCount = 0;

			for (int i=0; i<numberOfExperiments;i++)
			{
				int numberOfGenerations = 0;//NumberOfClusters[i].length +1;
				for(int n = 0; n < (NumberOfClusters[i].length - 1); n++)
				{
					if(NumberOfClusters[i][n+1] != 0)
					{
						numberOfGenerations++;
					}
					System.out.print(NumberOfClusters[i][n+1]);
				}
				System.out.println();
				System.out.println(numberOfGenerations);
								
				totalRunsAll += numberOfGenerations;
				if(NumberOfClusters[i][0] != 0)
				{
					totalGenerationsInRunsCompleted += numberOfGenerations;
					runsCompletedCount++;
					for(int n = 0; n < generationClusterFitness[i].length; n++)
					{
						System.out.println("Generation " + (n + 1) + ":");
						for(int m = 0; m < generationClusterFitness[i][n].length; m++)
						{
							if(generationClusterFitness[i][n][m] != -1)
							{
								System.out.println("Cluster " + (m + 1) + ": " + generationClusterFitness[i][n][m]);
							}
						}
					}
				}
			}
			
			System.out.println("Average number of generations considering all runs: " + (totalRunsAll/numberOfExperiments));
			System.out.println("Average number of generations considering only the "+ runsCompletedCount +" completed runs: " + (totalGenerationsInRunsCompleted/(runsCompletedCount == 0 ? numberOfExperiments : runsCompletedCount)));
		}

		// *** End of the algorithm ***

		// Reports
		if(areReportsMade)
		{
			File file2 = new File("C:/Users/Jun/Dropbox/EC/EC/reports/EntropyOverPopulation.txt");
			File file3 = new File("C:/Users/Jun/Dropbox/EC/EC/reports/bestIndivSOFARMeansReport.txt");
			//       pw1 = new PrintWriter("EC/reports/bestIndivMeansReport.txt");
			pw2 = new PrintWriter(file2);
			pw3 = new PrintWriter(file3);
			pw4 = new PrintWriter(file2);
			pw5 = new PrintWriter(file3);
			//pw4 = new PrintWriter("EC/reports/populationMeanScalingFactorMeansReport.txt");
			//pw5 = new PrintWriter("EC/reports/populationMeanScalingFactorStandardDeviationsReport.txt");

			// 'Reports' only make sense when each experiment has the same number of generations.
			// Therefore, we just take the first experiment to determine the number of generations for each experiment.
			for(int i=0; i<generationsForTest[0]; i++)
			{
				for(int j=0; j<numberOfExperiments; j++) 
				{       
					//               doubleAuxArray1[j] = bestIndividualFitness[j].get(i).doubleValue();
					doubleAuxArray2[j] = Entropy[j].get(i).doubleValue();
					doubleAuxArray3[j] = bestIndividualFitnessSoFar[j].get(i).doubleValue();
					doubleAuxArray4[j] = populationMeanScalingFactor[j].get(i).doubleValue();
				}
				//            pw1.println(Statistics.mean(doubleAuxArray1));
				pw2.println(Statistics.mean(doubleAuxArray2));
				pw3.println(Statistics.mean(doubleAuxArray3));
				pw4.println(Statistics.mean(doubleAuxArray4));
				pw5.println(Statistics.standardDeviation(doubleAuxArray4));
			}

			//         pw1.close();
			pw2.close();
			pw3.close();
			pw4.close();
			pw5.close();
		}

		// For Ole's reports
		//OleReports.doOleReportPopulationDistribution(lastPopulations);

		// Experiments statistics to the standard output
		// System.out.println("Number of tests: " + numberOfExperiments);
		//System.out.println();
		//System.out.println("Number of individuals: " + pr.individualsNumberReadFromFile);
		//System.out.println();
		//      System.out.println("Mean run time: " + 0.001*Statistics.mean(timeForTest) + " secs.");
		//      System.out.println("Run time standard deviation: " + 0.001*Statistics.standardDeviation(timeForTest) + " secs.");
		//      System.out.println();
		//System.out.println("Mean number of generations for each run: " + Statistics.mean(generationsForTest));
		//System.out.println("Standard deviation of the number of generations for each run: " + Statistics.standardDeviation(generationsForTest));
		//System.out.println();
		//      System.out.println("Mean best individual's partial fitness for each run: " + Statistics.mean(bestPartialFitnessForTest));
		//      System.out.println("Standard deviation of the best individual's partial fitness for each run: " + Statistics.standardDeviation(bestPartialFitnessForTest));
		//System.out.println();
		System.out.println("Percentage of runs reaching the optimum fitness: " + 100*numberOfSuccessRuns/numberOfExperiments);
	}



	private boolean checkChangeInGenerationFitness(GAParametersReader pr,
			GAIndividual bestIndividual, GAPopulation currentPopulation,
			int currentNumberOfClusters, double[] clusterFitness, double optimumFitness) {
		
		boolean everyClusterFitnessCheck = false;
		
		if(currentNumberOfClusters == pr.clusterNumberReadFromFile)
		{
			for(double fitness: clusterFitness)
			{
				if(fitness != -1)
				{
					if(fitness >= optimumFitness)
					{
						everyClusterFitnessCheck = true;
					}
					else
					{
						everyClusterFitnessCheck = false;
						break;
					}
				}
			}
		}
		return everyClusterFitnessCheck;
	}

}	