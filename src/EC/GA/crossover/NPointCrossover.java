package EC.GA.crossover;

import EC.GA.*;

import java.util.*;

/**
 * Implements N-point crossover.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class NPointCrossover extends GACrossover 
{
   /** Number of crossover points */
   private int crossoverPointsNumber; 

   /**
    * Creates an N-point crossover with null parameters.
    */
   public NPointCrossover() 
   {
      crossoverRate = 0;
      recombinedParents = new ArrayList<GAIndividual>(); // Used for crowding
      childrenIndexPool = new ArrayList<Integer>(); // Used for crowding
      crossoverPointsNumber = 0;
   }
    
   /**
    * Creates an N-point crossover with given parameters.
    * <p>
    * @param rate crossover rate
    * @param crossoverPointsNo number of crossover points
    */
   public NPointCrossover(double rate, int crossoverPointsNo) 
   {
      crossoverRate = rate;
      recombinedParents = new ArrayList<GAIndividual>(); // Used for crowding
      childrenIndexPool = new ArrayList<Integer>(); // Used for crowding
      crossoverPointsNumber = crossoverPointsNo;
   }

   /**
    * Peforms N-point crossover on a population of equal-length individuals, and transforms the population accordingly.
    * <p>
    * @param population the population whose individuals are mated
    */
   public void performCrossover(GAPopulation population) 
   {
      Random random = new Random();
      ArrayList<Integer> individualsToBeMated = new ArrayList<Integer>(); // Stores indexes of individuals to be mated
      GAIndividual parent1, parent2;
      int secondParentToBeMatedPosition; // Position in individualsToBeMated of the second parent
      boolean[] genesSelectedForCrossoverPoint; // For example, if genes 0 and 2 are selected then {true, false, true...}
      int numberOfCrossoverPointsCalculated; 
      int newCrossoverPoint; 
      int numberOfCrossoverPointsVisited;
      int numberOfNonCrossoverPointsVisited;
      int auxInt;
      int[] crossoverPoints = new int[crossoverPointsNumber]; // Sorted indexes of the crossover points
      boolean takeGenesForOffspring1FromParent1; // Determines which parent offspring1 takes genes from
      StringBuffer auxStringBuffer1 = new StringBuffer();
      StringBuffer auxStringBuffer2 = new StringBuffer();
      String auxString1 = new String();
      String auxString2 = new String();
      double auxDouble;

      
      for(int i=0; i<population.individuals.length; i++) 
         individualsToBeMated.add(new Integer(i));
      recombinedParents.clear();
      childrenIndexPool.clear();
      for(int i=0; i<population.individuals[0].variables.length; i++)
      {
         auxString1 += '1';
         auxString2 += '2'; 
      }
      while(individualsToBeMated.size() > 1) // Mate two individuals as long as there is more than one individual left in the list of individuals to be mated 
      {
         parent1 = population.individuals[((Integer)individualsToBeMated.get(0)).intValue()];  // First individual in the list 
         recombinedParents.add(parent1.copy()); // Used just for crowding
         childrenIndexPool.add((Integer)individualsToBeMated.get(0)); // Used just for crowding
         individualsToBeMated.remove(0);  
         secondParentToBeMatedPosition = random.nextInt(individualsToBeMated.size());
         parent2 = population.individuals[((Integer)individualsToBeMated.get(secondParentToBeMatedPosition)).intValue()]; // Random individual of the list
         recombinedParents.add(parent2.copy()); // Used just for crowding
         childrenIndexPool.add((Integer)individualsToBeMated.get(secondParentToBeMatedPosition)); // Used just for crowding
         individualsToBeMated.remove(secondParentToBeMatedPosition);

         if(random.nextDouble() < crossoverRate)
         {
            // Combine parent1 and parent2 and update them with their offspring chromosomes 
            genesSelectedForCrossoverPoint = new boolean[parent1.variables.length];
            for(int i=0; i<genesSelectedForCrossoverPoint.length; i++) 
               genesSelectedForCrossoverPoint[i] = false;
            numberOfCrossoverPointsCalculated = 0;
            while(numberOfCrossoverPointsCalculated < crossoverPointsNumber) 
            {
               newCrossoverPoint = random.nextInt(genesSelectedForCrossoverPoint.length-1-numberOfCrossoverPointsCalculated);
               numberOfNonCrossoverPointsVisited = 0;
               numberOfCrossoverPointsVisited = 0;
               while(numberOfNonCrossoverPointsVisited < newCrossoverPoint+1)
                  if(genesSelectedForCrossoverPoint[numberOfNonCrossoverPointsVisited+numberOfCrossoverPointsVisited]) // Next gene is a crossover point
                     numberOfCrossoverPointsVisited++;
                  else
                     numberOfNonCrossoverPointsVisited++;
               genesSelectedForCrossoverPoint[numberOfNonCrossoverPointsVisited+numberOfCrossoverPointsVisited-1] = true;
               numberOfCrossoverPointsCalculated++;
            }
            auxInt = 0;
            for(int i=0; i<genesSelectedForCrossoverPoint.length; i++) 
               if(genesSelectedForCrossoverPoint[i]) 
               {
                  crossoverPoints[auxInt] = i;
                  auxInt++;
               }
            takeGenesForOffspring1FromParent1 = true;
            auxStringBuffer1.append(auxString1.substring(0, crossoverPoints[0]+1)); // First part of parent1
            auxStringBuffer2.append(auxString2.substring(0, crossoverPoints[0]+1)); // First part of parent2
            takeGenesForOffspring1FromParent1 = false;
            for(int i=0; i<crossoverPoints.length-1; i++) 
            { 
               if(takeGenesForOffspring1FromParent1) 
               {
                  auxStringBuffer1.append(auxString1.substring(crossoverPoints[i]+1, crossoverPoints[i+1]+1)); // Middle part of parent1
                  auxStringBuffer2.append(auxString2.substring(crossoverPoints[i]+1, crossoverPoints[i+1]+1)); // Middle part of parent2
                  takeGenesForOffspring1FromParent1 = false;
               } 
               else 
               {
                  auxStringBuffer1.append(auxString2.substring(crossoverPoints[i]+1, crossoverPoints[i+1]+1)); // Middle part of parent2
                  auxStringBuffer2.append(auxString1.substring(crossoverPoints[i]+1, crossoverPoints[i+1]+1)); // Middle part of parent1
                  takeGenesForOffspring1FromParent1 = true;
               }
            }
            if(takeGenesForOffspring1FromParent1) 
            {
               auxStringBuffer1.append(auxString1.substring(crossoverPoints[crossoverPoints.length-1]+1)); // Last part of parent1
               auxStringBuffer2.append(auxString2.substring(crossoverPoints[crossoverPoints.length-1]+1)); // Last part of parent2
            } 
            else 
            {
               auxStringBuffer1.append(auxString2.substring(crossoverPoints[crossoverPoints.length-1]+1)); // Last part of parent1
               auxStringBuffer2.append(auxString1.substring(crossoverPoints[crossoverPoints.length-1]+1)); // Last part of parent2
            }
            for(int i=0; i<auxStringBuffer1.length(); i++)
               if(auxStringBuffer1.charAt(i) == '2') // Allele swap between the two parents
               {
                  auxDouble = parent1.variables[i]; 
                  parent1.variables[i] = parent2.variables[i];
                  parent2.variables[i] = auxDouble;
               }
            auxStringBuffer1.setLength(0);
            auxStringBuffer2.setLength(0);
         }
      }
   } 

}