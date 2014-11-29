package EC.GA;

/**
 * Handles selection of individuals to be included in the mating pool.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public abstract class GAParentSelection 
{
   /**
    * Selects individuals to be variated.
    * <p>
    * @param currentPopulation the population whose individuals are selected
    * @param selectedIndividualsNumber number of selected individuals
    * @return the new population of selected individuals
    */
   public abstract GAPopulation performParentSelection(GAPopulation currentPopulation, int selectedIndividualsNumber);

}