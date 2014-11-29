package EC.GA;

/**
 * Handles selection of individuals to survive in next generation.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public abstract class GASurvivorSelection 
{
   /**
    * Given a population and its offsprnig, selects which individuals 
    * of the population will be deleted in order to make room for the offspring.
    * <p>
    * @param currentPopulation the population whose individuals are selected for deletion
    * @param offspringPopulation the offspring
    */
   public abstract void performSurvivorSelection(GAPopulation currentPopulation, GAPopulation offspringPopulation);

}