package EC.GA.survivorSelection;

import EC.GA.*;

import java.util.Random;

/**
 * Selects survivor individuals by applying steady state model.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class SteadyStateModelSurvivorSelection extends GASurvivorSelection 
{
   /**
    * Given a population and its offspring, selects which individuals 
    * of the population will be deleted in order to make room for the offspring.
    * <p>
    * @param oldPopulation the population whose individuals are selected for deletion
    * @param offspringPopulation the offspring
    */
   public void performSurvivorSelection(GAPopulation oldPopulation, GAPopulation offspringPopulation) 
   {
      int auxIndex;
      GAIndividual auxIndividual = new GAIndividual();

      if(!oldPopulation.areIndividualsSortedByFitness)
         oldPopulation.sortIndividualsByFitness();
      offspringPopulation.sortIndividualsByFitness();
      for(int i=0; i<offspringPopulation.individuals.length; i++)
         oldPopulation.individuals[oldPopulation.individuals.length - offspringPopulation.individuals.length + i] = offspringPopulation.individuals[i];
      for(int i=oldPopulation.individuals.length-offspringPopulation.individuals.length; i<oldPopulation.individuals.length; i++) 
      {
         auxIndex = i;
         while(auxIndex>0 && oldPopulation.individuals[auxIndex].isBetterThan(oldPopulation.individuals[auxIndex-1])) 
         {
            auxIndividual = oldPopulation.individuals[auxIndex];
            oldPopulation.individuals[auxIndex] = oldPopulation.individuals[auxIndex-1];
            oldPopulation.individuals[auxIndex-1] = auxIndividual;
            auxIndex--;
         }
      }
      oldPopulation.areIndividualsSortedByFitness = true;
      oldPopulation.updateBestIndividual();     
      oldPopulation.calculateGlobalPopulationFitness();
      oldPopulation.entropy = oldPopulation.calculateEntropy();
   }

}