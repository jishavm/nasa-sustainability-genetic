package EC.GA.crowding;

import EC.GA.*;
import EC.GA.fitness.*;

import java.util.*;

/**
 * Handles Metropolis crowding method for keeping diversity in the population.
 * The following cooling schedule is used: T_new = coolingConstant x T_old every coolingPeriod generations.
 * <p>
 * @author Severino F. Galan
 * @since June 2011
 */
public class MetropolisReplacement extends GACrowding
{
   /** Current temperature according to the cooling schedule */
   public double currentTemperature;

   /**
    * Creates a Metropolis replacement with null values.
    */
   public MetropolisReplacement() 
   {
      S = 0;
   }
    
   /**
    * Creates a Metropolis replacement with given values.
    * <p>
    * @param SParameter Parameter S (see paper 'The crowding approach to niching in genetic algorithms')
    */
   public MetropolisReplacement(int SParameter) 
   {
      S = SParameter;
   }

   /**
    * Peforms Metropolis replacement on a population of <code>S</code> (or less) individuals, given a permutation of their children. 
    * <p>
    * @param population the population containing the children. In general, this population will be changed after several (parent, child) tournaments.
    * @param parents the parents temporarily substituted in the population by their children.
    * @param indexPool the indices of the children in the population. This variable connects in order each pair of parents with their two children.
    */
   public void performReplacement(GAPopulation population1, GAPopulation population2, ArrayList<GAIndividual> parents, ArrayList<Integer> indexPool, int[] permutation, int setpoint, int currentNumberOfClusters, double FeedbackScalingFactor)
   {
      GAIndividual currentParent = new GAIndividual(); 
      GAIndividual currentChild = new GAIndividual(); 
      double f_currentParent, f_currentChild; // Fitnesses for the current parent and the current child
      Random random = new Random();

      for(int i=0; i<parents.size(); i++)
      {
         currentParent = (GAIndividual)parents.get(i);
         currentChild = population2.individuals[((Integer)indexPool.get(permutation[i])).intValue()];
         f_currentParent = currentParent.fitness;
         f_currentChild = currentChild.fitness;
         if(currentParent.isBetterThan(currentChild))
            if(random.nextDouble() >= Math.exp((f_currentChild-f_currentParent)/currentTemperature))
               population2.individuals[((Integer)indexPool.get(permutation[i])).intValue()] = currentParent;
      }
   }

}