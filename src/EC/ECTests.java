package EC;

import EC.GA.GATests;

import java.io.*;

/** 
 * Performs tests in order to determine the viability of controlling the scaling factor in GAs used for optimization of real-valued functions
 * <p>
 * @param arg[1] name of the file containing the parameters for the genetic algorithm
 * @param arg[2] number of experiments to be carried out with the network
 * @param arg[3] optimum fitness supplied by the user
 * <p>
 * @author Severino F. Galan
 * @since October 2011
 */
public class ECTests {

   public static void main(String args[]) throws IOException {
	   
	//  System.out.println("hello"); 

      int numberOfExperiments = Integer.parseInt(args[2]); // Number of times the GA is executed
      double optimumFitness = Double.parseDouble(args[3]); // Optimum fitness 

      // Determine the type of evolutionary algorithm to be executed
      if(args[0].equals("GATests")) 
      {
         if(args[4].equals("Reports-off"))
            new GATests(args[1], numberOfExperiments, optimumFitness, false);
         else
            new GATests(args[1], numberOfExperiments, optimumFitness, true); 
      }
      else
      {
         System.out.println("Command incorrect. Evolutionary algorithm type: GATests.");
         return;
      }
   }

}