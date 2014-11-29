package EC.GA.parentSelection;

import EC.GA.*;

import java.util.*;

/**
 * Selects parent individuals by applying tournament selection without replacement.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class TournamentParentSelection extends GAParentSelection 
{
   /** At each iteration <code>tournamentSize</code> individuals are randomly selected. 
       The best one is chosen in the end. 
       This parameter belongs to [2, # of individuals] */  
   private int tournamentSize; 

   /**   
    * Creates a tournament parent selection with null values.
    */
   public TournamentParentSelection() 
   {
      tournamentSize = 0;
   }
    
   /** 
    * Creates a tournament parent selection with given values.
    * <p>
    * @param size the tournament size
    */
   public TournamentParentSelection(int size) 
   {
      tournamentSize = size;
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
      ArrayList<GAIndividual> nonTournamentIndividuals = new ArrayList<GAIndividual>(); // Individuals not selected for tournament yet
      int nextTournamentIndividualIndex; // The index of the next individual to be included in the set of tournament individuals
      GAIndividual nextTournamentIndividual; // Next individual to be included in the set of tournament individuals
      Random rand = new Random();
      GAIndividual bestIndividual; // Best individual found after an interation of tournament selection
      GAPopulation newPopulation = new GAPopulation(selectedIndividualsNumber); // The new population after tournament selection     

      // Generate the population of selected individuals.
      // The global fitness of the new population will not be calculated, because we don't need it.
      for(int i=0; i<selectedIndividualsNumber; i++) 
      { 
         nonTournamentIndividuals.clear();
         for(int j=0; j<currentPopulation.individuals.length; j++) 
            nonTournamentIndividuals.add(currentPopulation.individuals[j]);        
         bestIndividual = null;

         // Generate best tournament individual.
         for(int j=0; j<tournamentSize; j++) 
         {
            nextTournamentIndividualIndex = rand.nextInt(nonTournamentIndividuals.size());
            nextTournamentIndividual = (GAIndividual)nonTournamentIndividuals.get(nextTournamentIndividualIndex);
            if(bestIndividual == null)
               bestIndividual = nextTournamentIndividual;
            else if(nextTournamentIndividual.isBetterThan(bestIndividual))
               bestIndividual = nextTournamentIndividual;
            nonTournamentIndividuals.remove(nextTournamentIndividualIndex);
         }
  
         // Assign the best individual to the new population.
         newPopulation.individuals[i] = bestIndividual.copy();
      }        

      newPopulation.variablesLowerBounds = currentPopulation.variablesLowerBounds;
      newPopulation.variablesUpperBounds = currentPopulation.variablesUpperBounds;
      return newPopulation;
   }

}