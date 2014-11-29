package EC.GA.crowding;

import EC.GA.*;

import java.util.*;

/**
 * Handles deterministic crowding method for keeping diversity in the population.
 * <p>
 * @author Severino F. Galan
 * @since June 2011
 */
public class DeterministicReplacement extends GACrowding
{

   /**
    * Creates a deterministic replacement with null values.
    */
   public DeterministicReplacement() 
   {
      S = 0;
   }
    
   /**
    * Creates a deterministic replacement with given values.
    * <p>
    * @param SParameter Parameter S (see paper 'The crowding approach to niching in genetic algorithms')
    */
   public DeterministicReplacement(int SParameter) 
   {
      S = SParameter;
   }

   /**
    * Peforms deterministic replacement on a population of <code>S</code> (or fewer) individuals, given a permutation of their children. 
    * <p>
    * @param population the population containing the children. In general, this population will be changed after several (parent, child) tournaments.
    * @param parents the parents temporarily substituted in the population by their children.
    * @param indexPool the indices of the children in the population. This variable connects in order each pair of parents with their two children.
    */
   public void performReplacement(GAPopulation population1, GAPopulation population2, ArrayList<GAIndividual> parents, ArrayList<Integer> indexPool, int[] permutation, int setpoint, int currentNumberOfClusters, double FeedbackScalingFactor)
   {
      GAIndividual currentParent = new GAIndividual(); 
      GAIndividual currentChild = new GAIndividual(); 
      boolean isParentReplacedByChild;
      Random random = new Random();

      for(int i=0; i<parents.size(); i++)
      {
         currentParent = (GAIndividual)parents.get(i);
         currentChild = population2.individuals[((Integer)indexPool.get(permutation[i])).intValue()];
         isParentReplacedByChild = true;
         if(currentParent.isBetterThan(currentChild))
            isParentReplacedByChild = false;
         else if(currentParent.hasSameFitnessAs(currentChild))
            if(random.nextDouble()<0.5)
               isParentReplacedByChild = false;
         if(!isParentReplacedByChild)
            population2.individuals[((Integer)indexPool.get(permutation[i])).intValue()] = currentParent;
      }
   }

}



