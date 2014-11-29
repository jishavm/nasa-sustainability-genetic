package EC.tools;

/**
 * Allows some operations related to permutations to be calculated
 * <p>
 * @author Severino F. Galan
 * @since June 2009
 */

public abstract class Permutations
{
   /**
    * Calculates the number of different permutations of <code>intNumber</code> elements
    * <p>
    * @param intNumber the number of elements
    * @return the number of different permutations of <code>intNumber</code> elements
    */
   public static int permutationsNumber(int intNumber) 
   {
      int intNumberAux = 1;

      for(int i=2; i<=intNumber; i++)
         intNumberAux *= i;
      return intNumberAux;
   }

   /**
    * Gives the next permutation to a given permutation of N elements, such that the underlying sequence of N! permutations is ordered lexicographically.
    * <p>
    * @param data a given permutation
    */
   public static void nextPermutation(int[] data) 
   {  // The code for this method was obtained from the Internet
      int i = data.length-1;
      while(data[i-1] >= data[i]) 
         i--;
      int j = data.length;
      while(data[j-1] <= data[i-1]) 
         j--;
      swap(i-1, j-1, data);
      i++; 
      j = data.length;
      while (i<j) 
      {
         swap(i-1, j-1, data);
         i++;
         j--;
      }
   }

   /**
    * Swaps two given elements of a permutation.
    * <p>
    * @param i index of the first element
    * @param j index of the second element
    * @param data the permutation whose two elements will be swapped
    */
   public static void swap(int i, int j, int[] data)
   {
      int intAux;
   
      intAux = data[i];
      data[i] = data[j];
      data[j] = intAux;
   }

} // End of class
