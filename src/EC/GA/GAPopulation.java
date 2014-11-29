package EC.GA;

import EC.GA.fitness.*;

import java.util.Random;

/**
 * Handles general aspects of a population.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class GAPopulation 
{
   /** Individuals making up the population */
   public GAIndividual[] individuals; 

   /** Lower bounds for the variables of all the individuals in the population. Defined in <code>Fitness</code> class. */
   public double[] variablesLowerBounds; 

   /** Upper bounds for the variables of all the individuals in the population. Defined in <code>Fitness</code> class. */
   public double[] variablesUpperBounds; 

   /** Indicates whether individuals are sorted by fitness in their array. (First the fittest ones.) */
   public boolean areIndividualsSortedByFitness; 

   /** Total sum of the fitnesses of all the individuals in the population */
   public double fitness; 

   /** Fittest individual */   
   public GAIndividual bestIndividual; 

   /** Entropy of the population */
   public double entropy;
   
   /** scaling factor **/
   public double feedbackCurrentScalingFactor;


   /**
    * Creates an empty population of <code>individualsNumber</code> individuals.
    * <p>
    * @param individualsNumber the number of individuals of the new empty population
    */
   public GAPopulation(int individualsNumber) 
   {
      individuals = new GAIndividual[individualsNumber];
      for(int i=0; i<individualsNumber; i++)
         individuals[i] = new GAIndividual();
      variablesLowerBounds = null;
      variablesUpperBounds = null;
      areIndividualsSortedByFitness = false;
      fitness = 0;
      bestIndividual = null;
      entropy = 0;
   }

   /**
    * Sorts the individuals of the population by fitness.
    * Fittest (maximization is assumed) individuals are placed first.
    */
   public void sortIndividualsByFitness() 
   {
      int indexOfithBestIndividual;
      GAIndividual auxIndividual = new GAIndividual();

      for(int i=0; i<individuals.length-1; i++) 
      {
         indexOfithBestIndividual = i;
         for(int j=i+1; j<individuals.length; j++)
            if(individuals[j].isBetterThan(individuals[indexOfithBestIndividual]))
               indexOfithBestIndividual = j;
         auxIndividual = individuals[indexOfithBestIndividual];
         individuals[indexOfithBestIndividual] = individuals[i];
         individuals[i] = auxIndividual;
      }
      areIndividualsSortedByFitness = true; 
   }

   /**
    * Calculates the sum of the fitnesses of all individuals in the population, and updates the corresponding field.
    */
   public void calculateGlobalPopulationFitness() 
   {
      fitness = 0;

      for(int i=0; i<individuals.length; i++)
         fitness += individuals[i].fitness;
   }


   /**
    * Updates the fittest individual in the population.
    */
   public void updateBestIndividual() 
   {
      if(individuals.length>0) 
      {
         bestIndividual = individuals[0];
         for(int i=1; i<individuals.length; i++)
            if(individuals[i].isBetterThan(bestIndividual))
               bestIndividual = individuals[i];
      } 
   }


   /**
    * Includes an elitist individual in a population if such an individual improves the ones in the population.
    * The individual to be deleted is chosen randomly with uniform distribution.
    * <p>
    * @param elitistIndividual the elitist individual
    * @return <code>true</code> if the elitist individual has been included, <code>false</code> otherwise. 
    */
   public boolean updateWithElitistIndividual(GAIndividual elitistIndividual) 
   {
      boolean result = false;
      int auxInt = 0;
      Random random = new Random();

      while(auxInt<individuals.length && elitistIndividual.isBetterThan(individuals[auxInt]))
         auxInt++;
      if(auxInt == individuals.length) 
      {
         int individualToBeDeletedIndex = random.nextInt(individuals.length);
         fitness -= individuals[individualToBeDeletedIndex].fitness;
         individuals[individualToBeDeletedIndex] = elitistIndividual;
         areIndividualsSortedByFitness = false;
         fitness += elitistIndividual.fitness;
         bestIndividual = elitistIndividual;
         result = true;
      }                 
      return result;    
   }


   /**
    * Prints the population on the screen.
    */   
   public void print() 
   {
      for(int i=0; i<individuals.length; i++) 
      {
         //System.out.println();
         //System.out.println("Individual " + (i+1) + ": ");
         individuals[i].print();
      }
      //System.out.println();
      //System.out.println("Population global fitness: " + fitness);   
      //System.out.println();
      //System.out.println("Population fittest individual: ");
      //bestIndividual.print();
   }


   /**
    * Calculates the normalized entropy of the population: (sum of the entropies for each gene) / (number of genes)
    * Since the entropy for each gene varies between 0 and 1, the normalized entropy of the population varies between 0 an 1 too
    * <p>
    * @return the normalized entropy of the population 
    */
   public double calculateEntropy()
   {
      double currentEntropy, accEntropy;
      int genesNumber;
      int allelesNumber;
      int accValues;
      double probability;

      accEntropy = 0.0;
      genesNumber = individuals[0].variables.length;
      for(int i=0; i<genesNumber; i++)
      {
         currentEntropy = 0.0;
//         allelesNumber = FunctionDefinedOnRealNumbers.intervalsNumber[i];
allelesNumber = FunctionDefinedOnRealNumbers.intervalsNumber;
        for(int j=0; j<allelesNumber; j++)  
         {
            accValues = 0;
            for(int k=0; k<individuals.length; k++)
//               if(j == (int)(((individuals[k].variables[i]-FunctionDefinedOnRealNumbers.x_i[i])*FunctionDefinedOnRealNumbers.intervalsNumber[i])/(FunctionDefinedOnRealNumbers.x_f[i]-FunctionDefinedOnRealNumbers.x_i[i])))
//                  accValues++; 
 if(j == (int)(((individuals[k].variables[i]-FunctionDefinedOnRealNumbers.x_i)*FunctionDefinedOnRealNumbers.intervalsNumber)/(FunctionDefinedOnRealNumbers.x_f-FunctionDefinedOnRealNumbers.x_i)))
   accValues++; 
            if(accValues > 0)
            {
               probability = (1.0 * accValues) / individuals.length; 
               currentEntropy += probability * Math.log(probability) / Math.log(allelesNumber);           
            }
         }    
         accEntropy += currentEntropy;
      }
      return (-1.0) * accEntropy / genesNumber;
   }


   /**
    * Calculates the mean scaling factor in the population.
    */   
   public double meanScalingFactor() 
   {
      double auxDouble = 0;

      for(int i=0; i<this.individuals.length; i++)
      {
         auxDouble += individuals[i].scalingFactor;
      }
      return auxDouble / this.individuals.length;
   }


}