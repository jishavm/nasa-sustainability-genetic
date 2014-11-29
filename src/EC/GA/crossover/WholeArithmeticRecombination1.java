package EC.GA.crossover;

import EC.GA.*;

import java.util.*;

/**
 * Implements Whole Arithmetic Recombination (Eiben&Smit, 2003, page 50) with a parameter "alpha" defining the degree of disruption.
 * THE VARIANT IMPLEMENTED IN THIS CLASS IS BIASED AND DETERMINISTIC.
 * <p>
 * @author Severino F. Galan
 * @since November 2011
 */
public class WholeArithmeticRecombination1 extends GACrossover 
{
   /** Parameter between 0 and 1 indicating the degree of disruption in the children. Maximum disruption: 0.5. */
   private double alpha; 

   /**
    * Creates a parameterized uniform crossover with null parameters.
    */
   public WholeArithmeticRecombination1() 
   {
      crossoverRate = 0;
      recombinedParents = new ArrayList<GAIndividual>(); // Used for crowding
      childrenIndexPool = new ArrayList<Integer>(); // Used for crowding
      alpha = 0;
   }
    
   /**
    * Creates a parameterized uniform crossover with given parameters.
    * <p>
    * @param rate crossover rate
    * @param alpha degree of disruption
    */
   public WholeArithmeticRecombination1(double rate, double alpha) 
   {
      crossoverRate = rate;
      recombinedParents = new ArrayList<GAIndividual>(); // Used for crowding
      childrenIndexPool = new ArrayList<Integer>(); // Used for crowding
      this.alpha = alpha;
   }

   /**
    * Peforms Whole Arithmetic Recombination on a population of equal-length individuals, and transforms the population accordingly.
    * <p>
    * @param population the population whose individuals are mated
    */
   public void performCrossover(GAPopulation population) 
   {
      Random random = new Random();
      ArrayList<Integer> individualsToBeMated = new ArrayList<Integer>(); // Stores indexes of individuals to be mated
      GAIndividual parent1, parent2;
      int secondParentToBeMatedPosition; // Position in individualsToBeMated of the second parent
      double auxDouble1, auxDouble2;
    
      for(int i=0; i<population.individuals.length; i++) 
         individualsToBeMated.add(new Integer(i));
      recombinedParents.clear();
      childrenIndexPool.clear();
      while(individualsToBeMated.size() > 1) // Mate two individuals as long as there is more than one individual left in the list of individuals to be mated 
      { 
         parent1 = population.individuals[((Integer)individualsToBeMated.get(0)).intValue()];  // First individual in the list 
         recombinedParents.add(parent1.copy()); // Used just for crowding
         childrenIndexPool.add((Integer)individualsToBeMated.get(0)); // Used just for crowding
         individualsToBeMated.remove(0);  
         secondParentToBeMatedPosition = random.nextInt(individualsToBeMated.size());
         parent2 = population.individuals[((Integer)individualsToBeMated.get(secondParentToBeMatedPosition)).intValue()]; // Random individual of the list
         recombinedParents.add(parent2.copy()); // Used just for crowding
         childrenIndexPool.add((Integer)individualsToBeMated.get(secondParentToBeMatedPosition)); // Used just for crowding
         individualsToBeMated.remove(secondParentToBeMatedPosition);

         if(random.nextDouble() < crossoverRate)
         {
            // Combine parent1 and parent2 and update them with their offspring chromosomes 
            for(int i=0; i<parent1.variables.length; i++) 
            {
               auxDouble1 = parent1.variables[i] + alpha * (parent2.variables[i]-parent1.variables[i]);
               auxDouble2 = parent1.variables[i] + (1-alpha) * (parent2.variables[i]-parent1.variables[i]);
               parent1.variables[i] = auxDouble1;
               parent2.variables[i] = auxDouble2;
            }
         }
      }
   } 

}