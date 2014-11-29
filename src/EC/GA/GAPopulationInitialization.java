package EC.GA;

/**
 * Initializes a population.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public abstract class GAPopulationInitialization 
{
   /** Number of individuals in the initialized population */   
   public int individualsNumber; 

   /** Number of variables/genes for each individual in the initialized population */
   public int variablesNumber; 

   /** Lower bounds for individual's variables values */
   public double[] variablesLowerBounds; 

   /** Upper bounds for individual's variables values */
   public double[] variablesUpperBounds; 
	
   /** Upper bound for the scaling factor (for self-adaptive crowding) */
   public double scalingFactorUpperBound;

   /**
    * Initializes a population of individuals.
    * <p>
    * @param fitness Allows fitness to be calculated for the new individuals.
    * @return the new population
    */
   public abstract GAPopulation performInitialization(GAFitness fitness); 

}