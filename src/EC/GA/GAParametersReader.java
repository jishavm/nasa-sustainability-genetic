package EC.GA;

import java.util.*;
import java.io.*;

/**
 * Reads from a file the parameters needed for the execution of the genetic algorithm.
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class GAParametersReader 
{
   /** Type of fitness to be applied */
   int fitnessTypeReadFromFile; 

   /** Number of individuals in the population */
   int individualsNumberReadFromFile; 

   /** Number of generations */
   int generationsNumberReadFromFile; 

   /** Termination condition for the evolutionary algorithm */
   int terminationConditionReadFromFile;

   /** Type of initialization method for the population */
   int populationInitializationTypeReadFromFile; 

   /** Type of selection method for constructing the mating pool */
   int parentSelectionTypeReadFromFile; 

   /** Expected offspring number for the fittest individual in case of linear ranking selection */
   double expectedOffspringNumberForFittestIndividualReadFromFile; 

   /** Tournament size in case of tournament selection */
   int tournamentSizeReadFromFile; 

   /** Type of crossover */
   int crossoverTypeReadFromFile; 

   /** Crossover rate */
   double crossoverRateReadFromFile; 

   /** Allele exchange probability in case of parameterized uniform crossover */
   double exchangeProbabilityReadFromFile; 

   /** Number of crossover points in case of N-point crossover */
   int crossoverPointsNumberReadFromFile; 

   /** Alphe disruption in case of arithmetic recombination */
   double alphaDisruptionReadFromFile;

   /** Type of mutation */
   int mutationTypeReadFromFile; 

   /** Mutation rate */
   double mutationRateReadFromFile; 

   /** Sigma Step Size (standar deviation for the Gaussian mutation) */
   double sigmaStepSizeReadFromFile;

   /** Type of crowding */
   int crowdingTypeReadFromFile; 

   /** Parameter S for crowding */
   int SReadFromFile; 

   /** Parameters related to the cooling schedule applied, just in the case of Boltzmann or Metropolis replacements */
   double initialTemperatureReadFromFile;
   int coolingPeriodReadFromFile;
   double coolingConstantReadFromFile;

   /** Parameters related to the schedule applied to the scaling factor, just in case of generalized crowding */
   double initialScalingFactorReadFromFile;
   int scheduleTypeReadFromFile;
   double decayConstantReadFromFile;

   /** Type of selection method for creating the new population from the old one and its offspring */
   int survivorSelectionTypeReadFromFile; 

   /** Number of generated offspring in the case of (mu+lambda) or (mu,lambda) survivor selection */
   int lambdaReadFromFile;

   /** Elitism */
   int elitismReadFromFile; 

   /** Generational gap in case of steady state model for survivor selection */
   int generationalGapReadFromFile;
   
   // by JUN
   int clusteringTypeReadFromFile; 
   int clusterNumberReadFromFile;
   int controlFrequencyReadFromFile;
   double controlParameterReadFromFile;

   /**
    * Reads the parameters, stored as a property list, needed for the execution of the genetic algorithm on a specific problem.
    * <p>
    * @param fileName the name of the file where the parameters are stored
    * @return <code>true</code> if the parameters are read successfully, <code>false</code> otherwise
    */
   public boolean readParameters(String fileName) 
   {
      boolean status = true; // Status of the reading process
      boolean individualsNumberStatus = true; // Status of the reading process of individuals number

      try 
      {
         File file = new File(fileName);
         FileInputStream input;
         Properties properties = new Properties();

         properties.load(input = new FileInputStream(file));
         input.close();

         // Read FITNESS TYPE
         try 
         {
            fitnessTypeReadFromFile = Integer.parseInt(properties.getProperty("FitnessType"));
            
            System.out.println(fitnessTypeReadFromFile);
            if(fitnessTypeReadFromFile<1 || fitnessTypeReadFromFile>1) 
            {
               System.out.println("Fitness type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Fitness type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read INDIVIDUALS NUMBER
         try 
         {
            individualsNumberReadFromFile = Integer.parseInt(properties.getProperty("IndividualsNumber"));
            if(individualsNumberReadFromFile<1) 
            {
               System.out.println("Individuals number in file " + fileName + " is out of range.");
               individualsNumberStatus = false;
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Individuals number in file " + fileName + " must be an integer.");
            individualsNumberStatus = false;
            status = false;
         }

         // Read GENERATIONS NUMBER
         try 
         {
            generationsNumberReadFromFile = Integer.parseInt(properties.getProperty("GenerationsNumber"));
            if(generationsNumberReadFromFile<1) 
            {
               System.out.println("Generations number in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Generations number in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read TERMINATION CONDITION
         try
         {
            terminationConditionReadFromFile = Integer.parseInt(properties.getProperty("TerminationConditionType"));
            if(terminationConditionReadFromFile<1 || terminationConditionReadFromFile>2)
            {
               System.out.println("Termination condition type in file " + fileName + " is out of range.");
               status = false;
            }
         }
         catch(NumberFormatException nfe)
         {
            System.out.println("Termination condition type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read POPULATION INITIALIZATION TYPE
         try 
         {
            populationInitializationTypeReadFromFile = Integer.parseInt(properties.getProperty("PopulationInitializationType"));
            if(populationInitializationTypeReadFromFile<1 || populationInitializationTypeReadFromFile>1) 
            {
               System.out.println("Population initialization type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Population initialization type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read PARENT SELECTION TYPE
         try 
         {
            parentSelectionTypeReadFromFile = Integer.parseInt(properties.getProperty("ParentSelectionType"));
            if(parentSelectionTypeReadFromFile<1 || parentSelectionTypeReadFromFile>3) 
            {
               System.out.println("Parent selection type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Parent selection type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read EXPECTED OFFSPRING NUMBER FOR FITTEST INDIVIDUAL
         if(parentSelectionTypeReadFromFile == GATests.RANKING_PARENT_SELECTION) 
         { 
            try 
            {
               expectedOffspringNumberForFittestIndividualReadFromFile = Double.parseDouble(properties.getProperty("ExpectedOffspringNumberForFittestIndividual"));
               if(expectedOffspringNumberForFittestIndividualReadFromFile<1 || expectedOffspringNumberForFittestIndividualReadFromFile>2) 
               {
                  System.out.println("Expected offspring number for fittest individual in file " + fileName + " is out of range.");
                  status = false;
               }
            } 
            catch(NumberFormatException nfe) 
            {
               System.out.println("Expected offspring number for fittest individual in file " + fileName + " must be a real number.");
               status = false;
            }
         }

         // Read TOURNAMENT SIZE
         if(parentSelectionTypeReadFromFile == GATests.TOURNAMENT_PARENT_SELECTION) 
         { 
            try 
            {
               tournamentSizeReadFromFile = Integer.parseInt(properties.getProperty("TournamentSize"));
               if(individualsNumberStatus)
                  if(tournamentSizeReadFromFile<2 || tournamentSizeReadFromFile>individualsNumberReadFromFile) 
                  {
                     System.out.println("Tournament size in file " + fileName + " is out of range.");
                     status = false;
                  }
            } 
            catch(NumberFormatException nfe) 
            {
               System.out.println("Tournament size in file " + fileName + " must be an integer.");
               status = false;
            }
         }

         // Read CROSSOVER TYPE        
         try 
         {
            crossoverTypeReadFromFile = Integer.parseInt(properties.getProperty("CrossoverType"));
            if(crossoverTypeReadFromFile<1 || crossoverTypeReadFromFile>6) 
            {
               System.out.println("Crossover type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Crossover type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read CROSSOVER RATE
         try 
         {
            crossoverRateReadFromFile = Double.parseDouble(properties.getProperty("CrossoverRate"));
            if(crossoverRateReadFromFile<0 || crossoverRateReadFromFile>1) 
            {
               System.out.println("Crossover rate in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Crossover rate in file " + fileName + " must be a real number.");
            status = false;
         }

         // Read CROSSOVER POINTS NUMBER (just in case of N-Point Crossover)
         // The range of this parameter depends on the Bayesian network
         if(crossoverTypeReadFromFile == GATests.N_POINT_CROSSOVER)  
         { 
            crossoverPointsNumberReadFromFile = Integer.parseInt(properties.getProperty("CrossoverPointsNumber"));
            if(crossoverPointsNumberReadFromFile<1) // || crossoverPointsNumberReadFromFile>bn.getNodeList().size()-1) 
            {
               System.out.println("Number of crossover points for N-Point Crossover in file " + fileName + " is out of range.");
               status = false;
            }
         }

         // Read EXCHANGE PROBABILITY (just in case of Parameterized Uniform Crossover)
         if(crossoverTypeReadFromFile == GATests.PARAMETERIZED_UNIFORM_CROSSOVER) 
         { 
            try 
            {
               exchangeProbabilityReadFromFile = Double.parseDouble(properties.getProperty("ExchangeProbability"));
               if(exchangeProbabilityReadFromFile<0 || exchangeProbabilityReadFromFile>1) 
               {
                  System.out.println("Exchange probability for Parameterized Uniform Crossover in file " + fileName + " is out of range.");
                  status = false;
               }
            } 
            catch(NumberFormatException nfe) 
            {
               System.out.println("Exchange probability for Parameterized Uniform Crossover in file " + fileName + " must be a real number.");
               status = false;
            }
         }

         // Read ALPHA DISRUPTION (just in case of Arithmetic Recombination)
         try 
         {
            alphaDisruptionReadFromFile = Double.parseDouble(properties.getProperty("AlphaDisruption"));
            if(alphaDisruptionReadFromFile<0 || alphaDisruptionReadFromFile>1) 
            {
               System.out.println("Alpha disruption (just in case of arithmetic recombination) in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Alpha disruption in file " + fileName + " must be a real number.");
            status = false;
         }


         // Read MUTATION TYPE
         try 
         {
            mutationTypeReadFromFile = Integer.parseInt(properties.getProperty("MutationType"));
            if(mutationTypeReadFromFile<1 || mutationTypeReadFromFile>2) 
            {
               System.out.println("Mutation type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Mutation type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read MUTATION RATE
         try 
         {
            mutationRateReadFromFile = Double.parseDouble(properties.getProperty("MutationRate"));
            if(mutationRateReadFromFile<0 || mutationRateReadFromFile>1) 
            {
               System.out.println("Mutation rate in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Mutation rate in file " + fileName + " must be a real number.");
            status = false;
         }


         // Read SIGMA STEP SIZE (just in case of Gaussian Mutation)
         try 
         {
            sigmaStepSizeReadFromFile = Double.parseDouble(properties.getProperty("SigmaStepSize"));
            if(sigmaStepSizeReadFromFile<0) 
            {
               System.out.println("Sigma step size (or standard deviation just in case of arithmetic recombination) in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("SigmaStepSize in file " + fileName + " must be a real number.");
            status = false;
         }


         // Read CROWDING TYPE
         try 
         {
            crowdingTypeReadFromFile = Integer.parseInt(properties.getProperty("CrowdingType"));
            if(crowdingTypeReadFromFile<1 || crowdingTypeReadFromFile>6) 
            {
               System.out.println("Crowding type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Crowding type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read S
         try 
         {
            SReadFromFile = Integer.parseInt(properties.getProperty("S"));
            if(SReadFromFile<2 || ((SReadFromFile % 2) == 1)) 
            {
               System.out.println("S in file " + fileName + " must be an even integer greater than or equal to 2.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("S in file " + fileName + " must be an even integer greater than or equal to 2.");
            status = false;
         }

         // Read INITIAL TEMPERATURE
         try 
         {
            initialTemperatureReadFromFile = Double.parseDouble(properties.getProperty("InitialTemperature"));
            if(initialTemperatureReadFromFile<=0) 
            {
               System.out.println("Initial temperature in file " + fileName + " is out of range: It should be greater than zero!");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Initial temperature in file " + fileName + " must be a real number.");
            status = false;
         }

         // Read COOLING PERIOD
         try 
         {
            coolingPeriodReadFromFile = Integer.parseInt(properties.getProperty("CoolingPeriod"));
            if(coolingPeriodReadFromFile<1) 
            {
               System.out.println("Cooling period in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Cooling period in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read COOLING CONSTANT
         try 
         {
            coolingConstantReadFromFile = Double.parseDouble(properties.getProperty("CoolingConstant"));
            if(coolingConstantReadFromFile<=0 || coolingConstantReadFromFile>1) 
            {
               System.out.println("Cooling constant in file " + fileName + " is out of the range (0,1]");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Cooling constant in file " + fileName + " must be a real number.");
            status = false;
         }

         // Read INITIAL SCALING FACTOR
         try 
         {
            initialScalingFactorReadFromFile = Double.parseDouble(properties.getProperty("InitialScalingFactor"));
            if(initialScalingFactorReadFromFile<0) 
            {
               System.out.println("Inital scaling factor in file " + fileName + " is out of the range [0,Inifinity)");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Initial scaling factor in file " + fileName + " must be a real number.");
            status = false;
         }

         // Read SCHEDULE TYPE
         try 
         {
            scheduleTypeReadFromFile = Integer.parseInt(properties.getProperty("ScheduleType"));
            if(scheduleTypeReadFromFile<1 || survivorSelectionTypeReadFromFile>4) 
            {
               System.out.println("Schedule type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Schedule type in file " + fileName + " must be an integer.");
            status = false;
         }

         // Read DECAY CONSTANT
         try 
         {
            decayConstantReadFromFile = Double.parseDouble(properties.getProperty("DecayConstant"));
            if(decayConstantReadFromFile<0 || decayConstantReadFromFile>1) 
            {
               System.out.println("Decay constant in file " + fileName + " is out of the range [0,1]");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Decay constant in file " + fileName + " must be a real number.");
            status = false;
         }

         // Read SURVIVOR SELECTION TYPE
         try 
         {
            survivorSelectionTypeReadFromFile = Integer.parseInt(properties.getProperty("SurvivorSelectionType"));
            if(survivorSelectionTypeReadFromFile<1 || survivorSelectionTypeReadFromFile>4) 
            {
               System.out.println("Survivor selection type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Survivor selection type in file " + fileName + " must be an integer.");
            status = false;
         }


         // Read LAMBDA
         try 
         {
            lambdaReadFromFile = Integer.parseInt(properties.getProperty("Lambda"));
            if(individualsNumberStatus)
               if(survivorSelectionTypeReadFromFile==GATests.COMMA_SELECTION && lambdaReadFromFile<individualsNumberReadFromFile) 
               {
                  System.out.println("Lambda " + fileName + " is out of range. It should be equal or greater than the number of individuals.");
                  status = false;
               }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Lambda in file " + fileName + " must be an integer.");
            status = false;
         }



        // Read ELITISM
         try 
         {
            elitismReadFromFile = Integer.parseInt(properties.getProperty("Elitism"));
            if(elitismReadFromFile<1 || elitismReadFromFile>2) 
            {
               System.out.println("Elitism in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Elitism in file " + fileName + " must be an integer.");
            status = false;
         }
         
         // Read clustering type // by Jun
         try 
         {
            clusteringTypeReadFromFile = Integer.parseInt(properties.getProperty("ClusteringType"));
            
            System.out.println(clusteringTypeReadFromFile);
            if(clusteringTypeReadFromFile<1 || clusteringTypeReadFromFile>3) 
            {
               System.out.println("Clustering type in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Clustering type in file " + fileName + " must be an integer.");
            status = false;
         }
         
         // read number of clusters // by JUN
         try 
         {
            clusterNumberReadFromFile = Integer.parseInt(properties.getProperty("NumberOfClusters"));
            if(clusterNumberReadFromFile<0 || clusterNumberReadFromFile>1000) 
            {
               System.out.println("Cluster number in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Cluster number in file " + fileName + " must be an integer.");
            status = false;
         }
         
         
         
         // read control frequency// by JUN
         try 
         {
            controlFrequencyReadFromFile = Integer.parseInt(properties.getProperty("ControlFrequency"));
            if(controlFrequencyReadFromFile<0 || controlFrequencyReadFromFile>1000) 
            {
               System.out.println("Control frequency in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Control frequency in file " + fileName + " must be an integer.");
            status = false;
         }

         
         // read control parameters// by JUN
         try 
         {
            controlParameterReadFromFile = Double.parseDouble(properties.getProperty("ControlParameter"));
            if(controlParameterReadFromFile<0 || controlParameterReadFromFile>100000) 
            {
               System.out.println("Control parameter in file " + fileName + " is out of range.");
               status = false;
            }
         } 
         catch(NumberFormatException nfe) 
         {
            System.out.println("Control parameter in file " + fileName + " must be a double.");
            status = false;
         }
         
               
                  

         // Read GENERATIONAL GAP
         if(survivorSelectionTypeReadFromFile == GATests.STEADY_STATE_MODEL) 
         { 
            try 
            {
               generationalGapReadFromFile = Integer.parseInt(properties.getProperty("GenerationalGap"));
               if(individualsNumberStatus)
                  if(generationalGapReadFromFile<1 || generationalGapReadFromFile>individualsNumberReadFromFile) 
                  {
                     System.out.println("Generational gap in file " + fileName + " is out of range.");
                     status = false;
                  }
            } 
            catch(NumberFormatException nfe) 
            {
               System.out.println("Generational gap in file " + fileName + " must be an integer.");
               status = false;
            }
         }

      } 
      catch(IOException ioe) 
      {
         System.out.println("Cannot read " + fileName + " properly.");
         status = false;
      }
      return status;
   }

}