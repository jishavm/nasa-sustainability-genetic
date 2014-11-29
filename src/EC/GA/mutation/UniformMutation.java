package EC.GA.mutation;

import EC.GA.*;
import EC.GA.fitness.*;

import java.util.*;

/**
 * Implements uniform mutation.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class UniformMutation extends GAMutation 
{
   /**
    * Creates a uniform mutation with null values.
    */
   public UniformMutation() 
   {
      mutationRate = 0;
      scalingFactorUpperBound = 0;
   }
    
   /**
    * Creates a uniform mutation with given values.
    * <p>
    * @param rate mutation rate
    * @param sfub Upper bound for the scaling factor
    */
   public UniformMutation(double rate, double sfub) 
   {
      mutationRate = rate;
      scalingFactorUpperBound = sfub;
   }

   /**
    * Peforms uniform mutations on a population of individuals and transforms the population accordingly.
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
               population.individuals[i].variables[j] = population.variablesLowerBounds[j] + random.nextDouble() * (population.variablesUpperBounds[j] - population.variablesLowerBounds[j]);            
         auxDouble = population.individuals[i].scalingFactor + random.nextGaussian()*scalingFactorUpperBound*0.1; // <user> 0.1
         if((auxDouble>=0) && (auxDouble<=scalingFactorUpperBound))
            population.individuals[i].scalingFactor = auxDouble;
         fitness.evaluate(population.individuals[i]);
      }
      population.areIndividualsSortedByFitness = false;   
   }  

}