package EC.GA;

import java.util.*;

/**
 * Handles crossover of the individuals in the population.
 * <p>
 * @author Severino F. Galan
 * @since June 2011
 */
public abstract class GACrossover 
{
   /** Crossover rate for the genetic algorithm */
   public double crossoverRate;

   /** Recombined parents (used just for crowding). Each pair of recombined parents is contiguous here. 
    *  They would get lost if we would not use this variable. 
    */
   public ArrayList<GAIndividual> recombinedParents;

   /** Indexes of children in the new population (used just for crowding). Each pair of 'siblings' are contiguous here.
    *  There is a direct correspondence between variable 'parents' and this variable: 
    *  Each pair of parents and their pair of children are placed in the same positions of these two ArrayLists. 
    */
   public ArrayList<Integer> childrenIndexPool;

   /**
    * Peforms crossover on a population, and transforms the population accordingly.
    * <p>
    * @param population the population whose individuals are mated
    */
   public abstract void performCrossover(GAPopulation population); 

}