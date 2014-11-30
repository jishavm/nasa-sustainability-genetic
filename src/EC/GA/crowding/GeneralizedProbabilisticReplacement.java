package EC.GA.crowding;

import EC.GA.*;
import EC.GA.fitness.*;

import java.util.*;

/**
 * Handles generalized probabilistic crowding method for keeping diversity in the population.
 * <p>
 * @author Severino F. Galan
 * @since September 2011
 */
public class GeneralizedProbabilisticReplacement extends GACrowding
{
   /** Value of the scaling factor parameter */
   public double currentScalingFactor;

   /**
    * Creates a generalized probabilistic replacement with null values.
    */
   public GeneralizedProbabilisticReplacement() 
   {
      S = 0;
   }
    
   /**
    * Creates a generalized probabilistic replacement with given values.
    * <p>
    * @param SParameter Parameter S (see paper 'The crowding approach to niching in genetic algorithms')
    */
   public GeneralizedProbabilisticReplacement(int SParameter) 
   {
      S = SParameter;
   }

   /**
    * Peforms generalized probabilistic replacement on a population of <code>S</code> (or less) individuals, given a permutation of their children. 
    * <p>
    * @param population the population containing the children. In general, this population will be changed after several (parent, child) tournaments.
    * @param parents the parents temporarily substituted in the population by their children.
    * @param indexPool the indices of the children in the population. This variable connects in order each pair of parents with their two children.
    */
   public void performReplacement(GAPopulation population1, GAPopulation population2, ArrayList<GAIndividual> parents, ArrayList<Integer> indexPool, int[] permutation, int setpoint, int currentNumberOfClusters, double FeedbackScalingFactor)
   {
	   System.out.println("crowding");

	  GAIndividual currentParent = new GAIndividual(); 
      GAIndividual currentChild = new GAIndividual(); 
      double f_currentParent, f_currentChild; // Fitnesses for the current parent and the current child
      double probabilityThatParentIsNotReplaced;
      Random random = new Random();
      
      
      //System.out.println("parent size = "+parents.size());
      for(int i=0; i<parents.size(); i++)
      {
         currentParent = (GAIndividual)parents.get(i);
         currentChild = population2.individuals[((Integer)indexPool.get(permutation[i])).intValue()];
         f_currentParent = currentParent.fitness;
         f_currentChild = currentChild.fitness;
         if(currentParent.hasSameFitnessAs(currentChild))
            probabilityThatParentIsNotReplaced = 0.5;
         else if(currentChild.isBetterThan(currentParent))
         {
            if(currentScalingFactor == -1) // Self-adaptive crowding
               probabilityThatParentIsNotReplaced = (currentParent.scalingFactor*f_currentParent) / ((currentParent.scalingFactor*f_currentParent) + f_currentChild);
            else if(currentScalingFactor == -2) // Feedback control // written by JUN
            {
            	/*//System.out.println("111CurrentFeedbackScalingFactor"+population1.feedbackCurrentScalingFactor);
            	//System.out.println("currentNumberOfClusters"+currentNumberOfClusters);
            	if(setpoint<currentNumberOfClusters)
            	{
            		FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+10*(setpoint-currentNumberOfClusters);            		
            		//FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+0.1;
            		if(FeedbackScalingFactor<0)
            			FeedbackScalingFactor = 0;
            	}
            	
            	else if(setpoint>currentNumberOfClusters)
            		FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+10*(setpoint-currentNumberOfClusters);            		
            		//FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+0.1;
            	else
            		//reachSetpoint++;
            		FeedbackScalingFactor = population1.feedbackCurrentScalingFactor;*/
            	
            	probabilityThatParentIsNotReplaced = (FeedbackScalingFactor*f_currentParent) / ((FeedbackScalingFactor*f_currentParent) + f_currentChild); 
            	//population.feedbackCurrentScalingFactor = FeedbackScalingFactor;
            	//System.out.println("11CurrentFeedbackScalingFactor"+population1.feedbackCurrentScalingFactor);
            	//System.out.println("11FeedbackScalingFactor"+FeedbackScalingFactor);
            	//System.out.println();
            }
            else
            	//probabilityThatParentIsNotReplaced = 0;
               probabilityThatParentIsNotReplaced = (currentScalingFactor*f_currentParent) / ((currentScalingFactor*f_currentParent) + f_currentChild);
         }
         else
         {
            if(currentScalingFactor == -1) // Self-adaptive crowding
               probabilityThatParentIsNotReplaced = f_currentParent / (f_currentParent + (currentChild.scalingFactor*f_currentChild));

            
            else if(currentScalingFactor == -2) // Feedback control // written by JUN
            {
            	/*//System.out.println("CurrentFeedbackScalingFactor"+population1.feedbackCurrentScalingFactor);
            	//System.out.println("currentNumberOfClusters"+currentNumberOfClusters);
            	if(setpoint<currentNumberOfClusters)
            	{
            		FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+10*(setpoint-currentNumberOfClusters);            		
            		//FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+0.1;
            		if(FeedbackScalingFactor<0)
            			FeedbackScalingFactor = 0;
            	}
            	
            	else if(setpoint>currentNumberOfClusters)
            		FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+10*(setpoint-currentNumberOfClusters);
            		//FeedbackScalingFactor = population1.feedbackCurrentScalingFactor+0.1;
            	else
            		//reachSetpoint++;
            		FeedbackScalingFactor = population1.feedbackCurrentScalingFactor;*/
            	
            	probabilityThatParentIsNotReplaced = f_currentParent / (f_currentParent + (FeedbackScalingFactor*f_currentChild));
            	//System.out.println("22CurrentFeedbackScalingFactor"+population1.feedbackCurrentScalingFactor);
            	//System.out.println("22FeedbackScalingFactor"+FeedbackScalingFactor);
            	//System.out.println();
            }
            
            
            else
            	//probabilityThatParentIsNotReplaced = 1;
               probabilityThatParentIsNotReplaced = f_currentParent / (f_currentParent + (currentScalingFactor*f_currentChild));
         }
         if(random.nextDouble() < probabilityThatParentIsNotReplaced)
            population2.individuals[((Integer)indexPool.get(permutation[i])).intValue()] = currentParent;
      }
      
      //population1.feedbackCurrentScalingFactor = FeedbackScalingFactor;
      //population2.feedbackCurrentScalingFactor = FeedbackScalingFactor;
   }

}