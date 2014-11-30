package EC.GA;

import java.util.HashMap;

/**
 * Calculates the fitness values assigned to individuals.
 * <p>
 * @since October 2011
 */
public abstract class GAFitness 
{
   /**
    * Updates the fitness of an individual.
    * <p>
    * @param individual the individual to be updated
    * @return the fitness
    */
   public abstract void evaluate(GAIndividual individual);

}