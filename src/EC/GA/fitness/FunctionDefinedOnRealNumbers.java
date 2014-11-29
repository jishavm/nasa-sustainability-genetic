package EC.GA.fitness;

import EC.GA.*;

import java.util.*;

/**
 * Implements the fitness of an individual as a function defined on real numbers.
 * WARNING: If you are using Fitness Propotional Selection, all of the fitness values must be positive.
 * <p>
 * @since October 2011
 */
public class FunctionDefinedOnRealNumbers extends GAFitness 
{
   // The parameters that the user must specify are represented with "<user>" at the beginning of their comment line
   
   /** Dimension of the function, typed by the user */
   public static final int functionDimension = 1; //<user> 



   // Variables range

   /** Initial values of the ranges considered for each variable, typed by the user */
   // public static final double[] x_i = {-500, -500, -500, -500, -500, -500, -500, -500, -500, -500}; //<user> 
   // public static final double x_i = -10;  //<user>
   public static final double x_i = 0;

   /** Final values of the ranges considered for each variable */
   // public static final double[] x_f = {500, 500, 500, 500, 500, 500, 500, 500, 500, 500}; //<user> 
   // public static final double x_f = 10;  //<user>
   public static final double x_f = 1;



   // Number of discretization intervals for each variable (used just for calculating ENTROPY OF THE POPULATION under real variables)

   /** Number of discretization intervals for each variable (for ENTROPY OF THE POPULATION calculation) */
   // public static final int[] intervalsNumber = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100}; //<user>
   public static final int intervalsNumber = 65536; //<user>



   /**
    * Updates the fitness of an individual
    * <p>
    * @param the individual to be updated
    */
   public void evaluate(GAIndividual individual) 
   {
      individual.fitness = fitnessFunction(individual.variables);
   }
 
   /**
    * Calculates the fitness of an individual whose phenotype is an array of double numbers.
    * <p>
    * @param x the phenotype of the individual
    * @return the fitness associated to <code>x</code>
    */
   private double fitnessFunction(double[] x) 
   {
      int fitnessFunctionSelector = 1; //<user>
      double result = 0;

      switch(fitnessFunctionSelector)
      {
         case 1:
            result = userDefinedFunction(x);
            break;
         case 2:
            result = ackleyFunction(x);
            break;
         case 3:
            result = rastriginFunction(x);
            break;
         case 4:
            result = schwefelFunction(x);
            break;
         case 5:
            result = michalewiczFunction(x);
            break;
      }
      return result;
   }

   /**
    * Implements a user-defined function.
    * <p>
    * @param x The real-valued vector to which the function is applied
    * @return the value of the user-defined function applied to vector <code>x</code>
    */
   private double userDefinedFunction(double[] x) 
   {
      // Sphere      
      // return 200000-x[0]*x[0]-x[1]*x[1]-x[2]*x[2]-x[3]*x[3]-x[4]*x[4]-x[5]*x[5]-x[6]*x[6]-x[7]*x[7]-x[8]*x[8]-x[9]*x[9]-x[10]*x[10]-x[11]*x[11]-x[12]*x[12]-x[13]*x[13]-x[14]*x[14]-x[15]*x[15]-x[16]*x[16]-x[17]*x[17]-x[18]*x[18]-x[19]*x[19]; //<user> Function defined over variables x[0], x[1], ..., x[functionDimension-1]

      // Ballester&Carter F1 N=2
      // return -x[0]*x[0] -2*x[1]*x[1] +0.3*Math.cos(3*Math.PI*x[0]) +0.4*Math.cos(4*Math.PI*x[1]) -0.7 + 301.4;

      // Ballester&Carter F2 N=2
      // return -Math.pow(x[0],2) -2*Math.pow(x[1],2) +0.3*Math.cos(3*Math.PI*x[0])*Math.cos(4*Math.PI*x[1]) -0.3 + 300.6;
      // Ballester&Carter F2 N=3
      // return -Math.pow(x[0],2) -2*Math.pow(x[1],2) -3*Math.pow(x[2],2) +0.3*Math.cos(3*Math.PI*x[0])*Math.cos(4*Math.PI*x[1])*Math.cos(5*Math.PI*x[2]) -0.3 + 600.6;
      // Ballester&Carter F2 N=4
      // return -Math.pow(x[0],2) -2*Math.pow(x[1],2) -3*Math.pow(x[2],2) -4*Math.pow(x[3],2) +0.3*Math.cos(3*Math.PI*x[0])*Math.cos(4*Math.PI*x[1])*Math.cos(5*Math.PI*x[2])*Math.cos(6*Math.PI*x[3]) -0.3 + 1000.6;

      // Ballester&Carter F3 N=2
      // return -x[0]*x[0] -2*x[1]*x[1] +0.3*Math.cos(3*Math.PI*x[0]+4*Math.PI*x[1]) -0.3 + 300.6;

      // Ballester&Carter F4 N=2
      // return -x[0]*x[0] -x[1]*x[1] +Math.cos(Math.PI*(x[0]*x[0]+x[1]*x[1])) -1 
      //        -Math.pow(x[0]-0.75,2) -Math.pow(x[1]-0.75,2) +Math.cos(Math.PI*(Math.pow(x[0]-0.75,2)+Math.pow(x[1]-0.75,2))) -1 
      //        + 435.125;
      // Ballester&Carter F4 N=3
      // return -x[0]*x[0] -x[1]*x[1] -x[2]*x[2] +Math.cos(Math.PI*(x[0]*x[0]+x[1]*x[1]+x[2]*x[2])) -1 
      //        -Math.pow(x[0]-0.75,2) -Math.pow(x[1]-0.75,2) -Math.pow(x[2]-0.75,2) 
      //        +Math.cos(Math.PI*(Math.pow(x[0]-0.75,2)+Math.pow(x[1]-0.75,2)+Math.pow(x[2]-0.75,2))) -1 
      //        + 560.6875;

      // Ballester&Carter F5 N=2
      // return -x[0]*x[0] -x[1]*x[1] +Math.cos(Math.PI*(x[0]*x[0]+x[1]*x[1])) -1 
      //        -Math.pow(x[0]-0.375,2) -Math.pow(x[1]-0.375,2) +Math.cos(Math.PI*(Math.pow(x[0]-0.75,2)+Math.pow(x[1]-0.75,2))) -1 
      //         + 419.28125;
      // Ballester&Carter F5 N=3
      // return -x[0]*x[0] -x[1]*x[1] -x[2]*x[2] +Math.cos(Math.PI*(x[0]*x[0]+x[1]*x[1]+x[2]*x[2])) -1 
      //        -Math.pow(x[0]-0.375,2) -Math.pow(x[1]-0.375,2) -Math.pow(x[2]-0.375,2) 
      //        +Math.cos(Math.PI*(Math.pow(x[0]-0.75,2)+Math.pow(x[1]-0.75,2)+Math.pow(x[2]-0.75,2))) -1 
      //        + 581.921875;

      // Ballester&Carter F6 N=2
      // return -x[0]*x[0] -x[1]*x[1] +Math.cos(Math.PI*(x[0]*x[0]+x[1]*x[1])) -1 
      //        -Math.pow(x[0]-0.375,2) -Math.pow(x[1]-0.375,2) +Math.cos(Math.PI*(Math.pow(x[0]-0.75,2)+Math.pow(x[1]+0.75,2))) -1 
      //        + 419.28125;

      // Mengshoel&Goldberg f1
      // return Math.pow(Math.sin(5*Math.PI*x[0]),6);

      // Mengshoel&Goldberg f2
      return Math.pow(Math.E,-2*Math.log(2)*Math.pow((x[0]-0.1)/0.8,2)) * Math.pow(Math.sin(5*Math.PI*x[0]),6);
   }

   /**
    * Implements the Ackley function.
    * <p>
    * @param x The real-valued vector to which the function is applied, such that x[i] \in [-32.768,32.768]
    * @return the value of the Ackley function applied to vector <code>x</code>
    */
   private double ackleyFunction(double[] x) 
   {
      double c1=20; //<user>
      double c2=0.2; //<user>
      double aux1=0;
      double aux2=0;

      for (int i=0; i<x.length; i++) 
      {
         aux1 += x[i]*x[i];
         aux2 += Math.cos(2*Math.PI*x[i]);
      }
      return 22.322-(-c1*Math.exp(-c2*Math.sqrt(aux1/x.length)) - Math.exp(aux2/x.length) + c1 + Math.E);  // 22.322 value so that the returned value is always positive
   }

   /**
    * Implements Rastrigin's function.
    * <p>
    * @param x The real-valued vector to which the function is applied, usually constrainted so that each variable lies in the range [-5.12,5.12]
    * @return the value of Rastrigin's function applied to vector <code>x</code>
    */
   private double rastriginFunction(double[] x) 
   {
      double a=10; //<user>
      double w=2*Math.PI; //<user>
      double aux=0;

      for (int i=0; i<x.length; i++) 
         aux += x[i]*x[i]-a*Math.cos(w*x[i]);
      return -(x.length*a + aux) + functionDimension*(Math.pow(5.12,2)+ 2*a);
   }

   /**
    * Implements Schwefel's function. Global minimum: f(x)=-n*418.9829, x[i]=420.9687 i=1:n
    * <p>
    * @param x The real-valued vector to which the function is applied, usually constrained so that each variable lies in the range [-500,500]
    * @return the value of Schwefel's function applied to vector <code>x</code>
    */
   private double schwefelFunction(double[] x) 
   {
      double aux=0;

      for (int i=0; i<x.length; i++) 
         aux += (x[i]*2000-1000)*Math.sin(Math.sqrt(Math.abs(x[i]*2000-1000)));
      return aux + 1000*functionDimension;
   }

   /**
    * Implements Michalewicz's function.
    * <p>
    * @param x The real-valued vector to which the function is applied, usually constrainted so that each variable lies in the range [0,PI]
    * @return the value of Michalewicz's function applied to vector <code>x</code>
    */
   private double michalewiczFunction(double[] x) 
   {
      double m=10; //<user>
      double aux=0;

      for (int i=0; i<x.length; i++) 
         aux += Math.sin(x[i]) * Math.pow(Math.sin(((i+1)*Math.pow(x[i],2))/Math.PI),2*m);
      return aux;
   }

}