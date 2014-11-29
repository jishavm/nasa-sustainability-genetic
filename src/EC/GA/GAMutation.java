package EC.GA;

/**
 * Handles mutation of the individuals in the population.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public abstract class GAMutation 
{
   /** Mutation rate for the genetic algorithm */
   public double mutationRate;

   /** Upper bound for the scaling factor (for self-adaptive crowding) */
   public double scalingFactorUpperBound;
	
   /**
    * Peforms mutations on a population, and transforms the population accordingly.
    * <p>
    * @param fitness Allows fitness to be calculated for the new individuals in the mutated population.
    * @param population the population whose individuals are mutated
    */
   public abstract void performMutation(GAFitness fitness, GAPopulation population); 

}