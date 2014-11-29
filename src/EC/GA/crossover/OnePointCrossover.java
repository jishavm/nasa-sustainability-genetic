package EC.GA.crossover;

import EC.GA.*;

import java.util.*;

/**
 * Implements one-point crossover.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class OnePointCrossover extends GACrossover 
{
   /**
    * Creates a one-point crossover with null parameters.
    */
   public OnePointCrossover() 
   {
      crossoverRate = 0;
      recombinedParents = new ArrayList<GAIndividual>(); // Used for crowding
      childrenIndexPool = new ArrayList<Integer>(); // Used for crowding
   }
    
   /**
    * Creates a one-point crossover with given parameters.
    * <p>
    * @param rate crossover rate
    */
   public OnePointCrossover(double rate) 
   {
      crossoverRate = rate;
      recombinedParents = new ArrayList<GAIndividual>(); // Used for crowding
      childrenIndexPool = new ArrayList<Integer>(); // Used for crowding
   }

   /**
    * Peforms one-point crossover on a population of equal-length individuals, and transforms the population accordingly.
    * <p>
    * @param population the population whose individuals are mated
    */
   public void performCrossover(GAPopulation population) 
   {
      Random random = new Random();
      ArrayList<Integer> individualsToBeMated = new ArrayList<Integer>(); // Stores indexes of individuals to be mated
      GAIndividual parent1, parent2;
      int secondParentToBeMatedPosition; // Position in individualsToBeMated of the second parent
      int crossoverPoint;
      StringBuffer auxStringBuffer1 = new StringBuffer();
      StringBuffer auxStringBuffer2 = new StringBuffer();
      String auxString1 = new String();
      String auxString2 = new String();
      double auxDouble;

      
      for(int i=0; i<population.individuals.length; i++) 
         individualsToBeMated.add(new Integer(i));
      recombinedParents.clear();
      childrenIndexPool.clear();
      for(int i=0; i<population.individuals[0].variables.length; i++)
      {
         auxString1 += '1';
         auxString2 += '2'; 
      }
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
            crossoverPoint = random.nextInt(parent1.variables.length-1);
            auxStringBuffer1.append(auxString1.substring(0, crossoverPoint+1)); // First part of parent1
            auxStringBuffer2.append(auxString2.substring(0, crossoverPoint+1)); // First part of parent2
            auxStringBuffer1.append(auxString2.substring(crossoverPoint+1)); // Second part of parent2
            auxStringBuffer2.append(auxString1.substring(crossoverPoint+1)); // Second part of parent1
            for(int i=0; i<auxStringBuffer1.length(); i++)
               if(auxStringBuffer1.charAt(i) == '2') // Allele swap between the two parents
               {
                  auxDouble = parent1.variables[i]; 
                  parent1.variables[i] = parent2.variables[i];
                  parent2.variables[i] = auxDouble;
               }
            auxStringBuffer1.setLength(0);
            auxStringBuffer2.setLength(0);
         }
      }
   } 

}