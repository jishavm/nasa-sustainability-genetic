package EC.tools;

/**
 * Allows some statistical measures to be taken from a set of numbers
 * <p>
 * @author Severino F. Galan
 * @since November 2006
 */

public abstract class Statistics 
{
   /**
    * Calculates the mean of a set of int values
    */
   public static double mean(int[] values) 
   {
      double acc=0.0;

      for(int i=0; i<values.length; i++)
         acc += values[i];
      return acc / values.length;
   }

   /**
    * Calculates the mean of a set of double values
    */
   public static double mean(double[] values)
   {
      double acc=0.0;

      for(int i=0; i<values.length; i++)
         acc += values[i];
      return acc / values.length;
   }

   /**
    * Calculates the standard deviation of a set of int values
    */
   public static double standardDeviation(int[] values) 
   {
      double acc=0.0;

      for(int i=0; i<values.length; i++)
         acc += Math.pow(values[i]-mean(values), 2.0);
      return Math.sqrt(acc / values.length);
   }

   /**
    * Calculates the standard deviation of a set of double values
    */
   public static double standardDeviation(double[] values) 
   {
      double acc=0.0;

      for(int i=0; i<values.length; i++)
         acc += Math.pow(values[i]-mean(values), 2.0);
      return Math.sqrt(acc / values.length);
   }


} // End of class
