package EC.GA.survivorSelection;

import EC.GA.*;

/**
 * Selects survivor individuals by applying (mu+lambda) model.
 * <p>
 * @version 1
 * @since February 2011
 */
public class MuPlusLambdaSurvivorSelection extends GASurvivorSelection 
{
   /**
    * From an old population of mu parent individuals and an offspring population of lambda children, 
    * the best mu are selected for the next generation out of the mu+lambda individuals.
    * <p>
    * @param oldPopulation the old population
    * @param offspringPopulation the offspring
    */
   public void performSurvivorSelection(GAPopulation oldPopulation, GAPopulation offspringPopulation) 
   {
      int auxIndex;

      if(!oldPopulation.areIndividualsSortedByFitness)
         oldPopulation.sortIndividualsByFitness();  // Note: offspring is not necessarily sorted
      for(int i=0; i<offspringPopulation.individuals.length; i++) 
      {
         auxIndex = 0;
         while(auxIndex<oldPopulation.individuals.length && oldPopulation.individuals[auxIndex].fitness>offspringPopulation.individuals[i].fitness) 
            auxIndex++;
         if(auxIndex < oldPopulation.individuals.length) 
         {
            for(int j=oldPopulation.individuals.length-1; j>auxIndex; j--) 
               oldPopulation.individuals[j] = oldPopulation.individuals[j-1];
            oldPopulation.individuals[auxIndex] = offspringPopulation.individuals[i];
         }
      }
      oldPopulation.areIndividualsSortedByFitness = true;
      oldPopulation.calculateGlobalPopulationFitness();
      oldPopulation.updateBestIndividual();
   }

}