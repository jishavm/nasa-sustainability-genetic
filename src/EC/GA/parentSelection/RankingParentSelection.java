package EC.GA.parentSelection;

import EC.GA.*;

import java.util.Random;

/**
 * Selects parent individuals by linear ranking of the population.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class RankingParentSelection extends GAParentSelection 
{
   /** Expected offspring number for the fittest individual */
   private double expectedOffspringNumberForFittestIndividual;

   /**
    * Creates a ranking parent selection with given values.
    * <p>
    * @param expectedOffspringNoForFittestIndividual Expected offspring number for the fittest individual. 
    */
   public RankingParentSelection(double expectedOffspringNoForFittestIndividual) 
   {
      expectedOffspringNumberForFittestIndividual = expectedOffspringNoForFittestIndividual;
   }

   /**
    * Selects individuals to be variated.
    * <p>
    * @param currentPopulation the population whose individuals are selected
    * @param selectedIndividualsNumber the number of selected individuals
    * @return the new population of selected individuals
    */
   public GAPopulation performParentSelection(GAPopulation currentPopulation, int selectedIndividualsNumber) 
   {
      double[] accumulatedProbabilities = new double[currentPopulation.individuals.length]; // Array of accumulated probabilities
      Random rand = new Random();
      double randomNumber; 
      int j;
      GAPopulation newPopulation = new GAPopulation(selectedIndividualsNumber);      

      if(!currentPopulation.areIndividualsSortedByFitness)      
         currentPopulation.sortIndividualsByFitness();

      // Calculate array of accumulated probabilities
      accumulatedProbabilities[0] = expectedOffspringNumberForFittestIndividual / currentPopulation.individuals.length;
      for(int i=1; i<currentPopulation.individuals.length-1; i++) 
         accumulatedProbabilities[i] = accumulatedProbabilities[i-1] + ((2-expectedOffspringNumberForFittestIndividual)/currentPopulation.individuals.length) + (2*(currentPopulation.individuals.length-i-1)*(expectedOffspringNumberForFittestIndividual-1))/(currentPopulation.individuals.length*(currentPopulation.individuals.length-1));
      accumulatedProbabilities[currentPopulation.individuals.length-1] = 1.0;

      // Generate the population of selected individuals.
      // The global fitness of the new population will not be calculated, because we don't need it.
      for(int i=0; i<selectedIndividualsNumber; i++) 
      {
         randomNumber = rand.nextDouble();
         j=-1;
         while(randomNumber > accumulatedProbabilities[++j]);
         newPopulation.individuals[i] = currentPopulation.individuals[j].copy();
      }            

      newPopulation.variablesLowerBounds = currentPopulation.variablesLowerBounds;
      newPopulation.variablesUpperBounds = currentPopulation.variablesUpperBounds;
      return newPopulation;
   }

}