package EC.GA.survivorSelection;

import EC.GA.*;

/**
 * Selects survivor individuals by applying (mu,lambda) model.
 * <p>
 * @version 1
 * @since February 2011
 */
public class MuCommaLambdaSurvivorSelection extends GASurvivorSelection 
{
   /**
    * Given an old population of mu parent individuals and an offspring population of lambda children (lambda>mu), 
    * the best mu are selected for the next generation out of the lambda children individuals.
    * <p>
    * @param oldPopulation the old population
    * @param offspringPopulation the offspring
    */
   public void performSurvivorSelection(GAPopulation oldPopulation, GAPopulation offspringPopulation) 
   {
      int auxIndex;

      offspringPopulation.sortIndividualsByFitness();  
      for(int i=0; i<oldPopulation.individuals.length; i++) 
         oldPopulation.individuals[i] = offspringPopulation.individuals[i];
      oldPopulation.areIndividualsSortedByFitness = true;
      oldPopulation.calculateGlobalPopulationFitness();
      oldPopulation.updateBestIndividual();
   }

}