package EC.GA;

import EC.GA.fitness.*;


/**
 * Handles an individual 
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class GAIndividual 
{
   /** Individual's genotype (and phenotype) */
   public double[] variables;

   /** Individual's scaling factor, used for self-adaptive crowding. It belongs to [0,sf_max], where I'll take for convenience
       sf_max = InitialScalingFactor. It represents the scaling factor that will multiply the current individual, if the individual 
       happens to be the less fit individual in a crowding tournament. **/
   public double scalingFactor;

   /** Fitness for the individual */
   public double fitness; 

   /**
    * Creates an empty individual.
    */
   public GAIndividual() 
   {
      variables = null;
      scalingFactor = 0;
      fitness = 0;
   }

   /**
    * Gets a copy of the individual.
    * <p>
    * @return the copied individual
    */
   public GAIndividual copy() 
   {
      GAIndividual copy = new GAIndividual();

      copy.variables = new double[variables.length];
      for(int i=0; i<variables.length; i++)
         copy.variables[i] = variables[i];
      copy.scalingFactor = scalingFactor; 
      copy.fitness = fitness;      
      return copy;
   }

   /**
    * Determines whether the present individual has the same fitness as another one.
    * <p>
    * @param individual the individual to whom the present one is compared with 
    * @return the result of the comparison
    */
   public boolean hasSameFitnessAs(GAIndividual individual) 
   {
      boolean result = false;

      if(fitness == individual.fitness)
         result = true;
      return result;
   }

   /**
    * Determines whether the present individual is fitter than another one.
    * WARNING: WE ARE CONSIDERING MAXIMIZATION OF REAL FUNCTIONS!!!
    * <p>
    * @param individual the individual to whom the present one is compared with 
    * @return the result of the comparison
    */
   public boolean isBetterThan(GAIndividual individual) 
   {
      boolean result = false;

      if(fitness > individual.fitness)
         result = true;
      return result;
   }

   /**
    * Prints the individual information on the screen.
    */   
   public void print() 
   {
      //System.out.println("->Variables:");
 	  String bit = "";

      for(int j=0; j<variables.length; j++)
      {//System.out.println();
			bit+=variables[j]+",";

        //System.out.println(variables[j]); // Print variables values        
      }
      System.out.println(bit);
      //System.out.println("->Scaling Factor (just for self-adaptive crowding): " + scalingFactor);
      //System.out.println("->Fitness: " + fitness);
      System.out.println(fitness);
   }

   /**
    * Gets the Euclidean distance between the current individual and a new one.
    * <p>
    * @param individual the new individual
    * @return the distance between the two individuals
    */
   public double distanceTo(GAIndividual individual) 
   {
      double distance = 0.0;

      for(int i=0; i<variables.length; i++)
         distance += Math.pow(variables[i]-individual.variables[i], 2);
      return Math.sqrt(distance);
   }


//   /**
//    * Determines whether the current individual has the same genotype as a given individual.
//    * <p>
//    * @param individual the given individual which is compared with the current one
//    * @return the result of comparing the genotype of both individuals
//    */
//   public boolean hasSameGenotypeAs(GAIndividual individual) 
//   {
//      boolean result = true;
//      int index = -1;
//
//      while(result && (index<chromosome.length()-1))
//      {
//         index++;
//         if(chromosome.charAt(index) != individual.chromosome.charAt(index))
//            result = false;
//      }
//      return result;
//   }

}