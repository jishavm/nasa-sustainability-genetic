package EC.GA.parentSelection;

import EC.GA.*;

import java.util.*;

/**
 * Selects parent individuals randomly.
 * <p>
 * @author Severino F. Galan
 * @since June 2011
 */
public class RandomParentSelection extends GAParentSelection 
{
   /**
    * Selects parent individuals randomly (without replacement).
    * Random parent selection just makes sense when the number of parents is lower than the population size.
    * Otherwise, when the number of parents is equal to the population size, just a copy of the population is made.
    * <p>
    * @param currentPopulation the population whose individuals are selected
    * @param selectedIndividualsNumber the number of selected individuals
    * @return the new population of selected individuals
    */
   public GAPopulation performParentSelection(GAPopulation currentPopulation, int selectedIndividualsNumber) 
   {
      Random r = new Random();
      GAPopulation newPopulation = new GAPopulation(selectedIndividualsNumber);      

      if(currentPopulation.individuals.length == selectedIndividualsNumber) // Random parent selection just makes sense when the number of parents is lower than the population size.
      {
         for(int i=0; i<selectedIndividualsNumber; i++)
            newPopulation.individuals[i] = currentPopulation.individuals[i].copy();
      }
      else
      { 
        for(int i=0; i<selectedIndividualsNumber; i++)
           newPopulation.individuals[i] = currentPopulation.individuals[r.nextInt(currentPopulation.individuals.length)].copy();
      }
      newPopulation.variablesLowerBounds = currentPopulation.variablesLowerBounds;
      newPopulation.variablesUpperBounds = currentPopulation.variablesUpperBounds;
      
      newPopulation.feedbackCurrentScalingFactor = currentPopulation.feedbackCurrentScalingFactor;
      return newPopulation;

   }

}