package EC.GA;

import EC.tools.Permutations;

import java.util.*;

/**
 * Handles crowding methods for keeping diversity in the population.
 * <p>
 * @author Severino F. Galan
 * @since June 2009
 */
public abstract class GACrowding
{
	
	
/** Value of the scaling factor parameter */
public double currentScalingFactor;

   /** Parameter S for crowding: even number of parents (with S>=2) in tournament. See paper 'The crowding approach to niching in genetic algoritms'. */
   public int S;

   /**
    * Peforms crowding on a population.
    * <p>
    * @param population the population containing the children. In general, this population will be changed after several (parent, child) tournaments.
    * @param parents the parents temporarily substituted in the population by their children.
    * @param indexPool the indices of the children in the population. This variable connects in order each pair of parents with their two children.
    */
   public void performCrowding(GAPopulation population1, GAPopulation population2, ArrayList<GAIndividual> parents, ArrayList<Integer> indexPool, int setpoint, int currentNumberOfClusters, double FeedbackScalingFactor)
   {

      ArrayList<GAIndividual> parentsAux = new ArrayList<GAIndividual>();
      ArrayList<Integer> indexPoolAux = new ArrayList<Integer>();

      for(int i=0; i<parents.size(); i++)
      {
         parentsAux.add((GAIndividual)parents.get(i));
         indexPoolAux.add((Integer)indexPool.get(i));
         if((parentsAux.size()==S) || (i==parents.size()))
         {
            performCrowdingStep(population1, population2, parentsAux, indexPoolAux, setpoint, currentNumberOfClusters,FeedbackScalingFactor);
            parentsAux.clear();
            indexPoolAux.clear();
         } 
      }
      
   } 

   /**
    * Peforms crowding on a population of <code>S</code> (or fewer) individuals. 
    * <p>
    * @param population the population containing the children. In general, this population will be changed after several (parent, child) tournaments.
    * @param parents the parents temporarily substituted in the population by their children.
    * @param indexPool the indices of the children in the population. This variable connects in order each pair of parents with their two children.
    */
   public void performCrowdingStep(GAPopulation population1, GAPopulation population2, ArrayList<GAIndividual> parents, ArrayList<Integer> indexPool, int setpoint, int currentNumberOfClusters, double FeedbackScalingFactor)
   {
      double[][] distances = new double[parents.size()][indexPool.size()];
      int[] currentPermutation = new int[indexPool.size()];
      int[] optimalPermutation = new int[indexPool.size()];
      double minimumParentsChildrenDistance, parentsChildrenDistance;

      // Calculate distances between parents and children
      for(int i=0; i<parents.size(); i++)
         for(int j=0; j<indexPool.size(); j++)  // Actually, parents.size() and indexPool.size() are equal.
            distances[i][j] = ((GAIndividual)parents.get(i)).distanceTo((GAIndividual)population2.individuals[((Integer)indexPool.get(j)).intValue()]);

      // Calculate the permutation of children having minimum distance to the parents
      for(int i=0; i<indexPool.size(); i++)
      {
         currentPermutation[i] = i;
         optimalPermutation[i] = i;
      }
      minimumParentsChildrenDistance = 0;
      for(int i=0; i<indexPool.size(); i++)
         minimumParentsChildrenDistance += distances[i][i];
      for(int i=1; i<Permutations.permutationsNumber(indexPool.size()); i++)
      {
         Permutations.nextPermutation(currentPermutation);
         parentsChildrenDistance = 0;
         for(int j=0; j<indexPool.size(); j++)
            parentsChildrenDistance += distances[j][currentPermutation[j]];
         if(parentsChildrenDistance < minimumParentsChildrenDistance)
         {
            minimumParentsChildrenDistance = parentsChildrenDistance;
            for(int j=0; j<optimalPermutation.length; j++)
               optimalPermutation[j] = currentPermutation[j];
         }
      }

      // Perform replacement
      performReplacement(population1, population2, parents, indexPool, optimalPermutation, setpoint, currentNumberOfClusters, FeedbackScalingFactor); 
   }

   /**
    * Peforms replacement on a population of <code>S</code> (or less) individuals, given a permutation of their children. 
    * <p>
    * @param population the population containing the children. In general, this population will be changed after several (parent, child) tournaments.
    * @param parents the parents temporarily substituted in the population by their children.
    * @param indexPool the indices of the children in the population. This variable connects in order each pair of parents with their two children.
    */
   public abstract void performReplacement(GAPopulation population1, GAPopulation population2, ArrayList<GAIndividual> parents, ArrayList<Integer> indexPool, int[] permutation, int setpoint, int currentNumberOfClusters, double FeedbackScalingFactor);

}