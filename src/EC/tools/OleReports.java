package EC.tools;

import java.io.*;

import EC.GA.fitness.*;

/**
 * Allows some reports suggested by Ole to be generated
 * <p>
 * @author Severino F. Galan
 * @since December 2011
 */

public abstract class OleReports 
{
   /**
    * Reports (in 'OleReport1-PopDis.txt') the number of individuals that appear in each discrete interval of the function
    * <p>
    * @param x The real values (third index of the array IN CASE OF n-dimensional functions with n>1) for the variables of the individuals in the final population of the GA (second index of the array) over a set of runs (first index of the array)
    *
    */
   public static void doOleReportPopulationDistribution(double[][] x) throws IOException
   {
      int[][] individualsNumber = new int[x.length][FunctionDefinedOnRealNumbers.intervalsNumber]; // Number of individuals in the final population for each run and for each interval
      int[] auxInt = new int[x.length];      
      double[] means = new double[FunctionDefinedOnRealNumbers.intervalsNumber];
      PrintWriter pwMeans = new PrintWriter("EC/reports/OleReport1-PopDis.txt");

      // Calculate 'individualsNumber' from 'x'
      for(int i=0; i<individualsNumber.length; i++)
         for(int j=0; j<FunctionDefinedOnRealNumbers.intervalsNumber; j++)
            individualsNumber[i][j]=0;
      for(int i=0; i<individualsNumber.length; i++)  // Runs number
         for(int j=0; j<x[0].length; j++)  // Population size
            individualsNumber[i][toInteger(x[i][j])]++; 

      // Create report files 
      for(int i=0; i<FunctionDefinedOnRealNumbers.intervalsNumber; i++)
      {
         for(int j=0; j<x.length; j++)
            auxInt[j] = individualsNumber[j][i];     
         means[i] = Statistics.mean(auxInt);  // Results for report on means stored in array 'means'
      }
      for(int i=0; i<FunctionDefinedOnRealNumbers.intervalsNumber; i++)
         pwMeans.println(means[i]);
      pwMeans.close();
   }

   /**
    * Calculates the integer corresponding to a real value: 0 <- first interval, 1 <- second interval...
    * <p>
    * @param x_value the real value to be transformed into an integer
    * @return the integer value corresponding to the real value
    *
    */
   public static int toInteger(double x_value) 
   {
      double intervalsLength = (FunctionDefinedOnRealNumbers.x_f - FunctionDefinedOnRealNumbers.x_i) / FunctionDefinedOnRealNumbers.intervalsNumber;
      int auxInt;

      auxInt = (int)((x_value - FunctionDefinedOnRealNumbers.x_i) / intervalsLength);
      if(auxInt == FunctionDefinedOnRealNumbers.intervalsNumber) // The real value is at the right boundary of the interval!!!
         auxInt--;
      return auxInt;
   }

} // End of class
