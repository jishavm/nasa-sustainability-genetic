package EC.GA;

import java.util.*;

/** 
* Perform clustering approach to cluster the population
*/

public abstract class GAClustering
{
	public abstract int Clustering(GAPopulation population, int K, int generation, double[][][] clusterFitness);
}//end of GAClustering class