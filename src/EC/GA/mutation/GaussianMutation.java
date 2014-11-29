package EC.GA.mutation;

import EC.GA.*;
import EC.GA.fitness.*;

import java.util.*;

/**
 * Implements Gaussian mutation.
 * <p>
 * @author Severino F. Galan
 * @since November 2011
 */
public class GaussianMutation extends GAMutation 
{
   /** Positive parameter indicating the standard deviation of the Gaussian distribution for mutation */
   private double sigma; 

   /**
    * Creates a Gaussian mutation with null values.
    */
   public GaussianMutation() 
   {
      mutationRate = 0;
      sigma = 0;
      scalingFactorUpperBound = 0;
   }
    
   /**
    * Creates a Gaussian mutation with given values.
    * <p>
    * @param rate mutation rate
    * @param sigma standard deviation of the Gaussian distribution
    * @param sfub Upper bound for the scaling factor
    */
   public GaussianMutation(double rate, double sigma, double sfub) 
   {
      mutationRate = rate;
      this.sigma = sigma;
      scalingFactorUpperBound = sfub;
   }

   /**
    * Peforms Gaussian mutations on a population of individuals and transforms the population accordingly.
    * <p>
    * @param fitness Allows fitness to be calculated for the new individuals in the mutated population.
    * @param population the population whose individuals are mutated
    */
   public void performMutation(GAFitness fitness, GAPopulation population) 
   {
      Random random = new Random();
      double auxDouble;

      for(int i=0; i<population.individuals.length; i++) 
      {
         for(int j=0; j<population.individuals[i].variables.length; j++) 
            if(random.nextDouble() < mutationRate) 
            {
               population.individuals[i].variables[j] = population.individuals[i].variables[j] + sigma*random.nextGaussian();
               //if(population.individuals[i].variables[j] < FunctionDefinedOnRealNumbers.x_i[j])
               //   population.individuals[i].variables[j] = FunctionDefinedOnRealNumbers.x_i[j]; 
               //else if(population.individuals[i].variables[j] > FunctionDefinedOnRealNumbers.x_f[j])
               //   population.individuals[i].variables[j] = FunctionDefinedOnRealNumbers.x_f[j]; 
if(population.individuals[i].variables[j] < FunctionDefinedOnRealNumbers.x_i)
   population.individuals[i].variables[j] = FunctionDefinedOnRealNumbers.x_i; 
else if(population.individuals[i].variables[j] > FunctionDefinedOnRealNumbers.x_f)
   population.individuals[i].variables[j] = FunctionDefinedOnRealNumbers.x_f; 
            }
         auxDouble = population.individuals[i].scalingFactor + random.nextGaussian()*scalingFactorUpperBound*0.1; // <user> 0.1
         if((auxDouble>=0) && (auxDouble<=scalingFactorUpperBound))
            population.individuals[i].scalingFactor = auxDouble;
         fitness.evaluate(population.individuals[i]);
      }
      population.areIndividualsSortedByFitness = false;   
   }  

}