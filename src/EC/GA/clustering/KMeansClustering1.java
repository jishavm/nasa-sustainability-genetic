package EC.GA.clustering;

import EC.GA.*;

import java.util.*;

public class KMeansClustering1 extends GAClustering
{
	public int Clustering(GAPopulation population, int setpoint, int generation, double[][] clusterFitness)
	{
		//int currentNumberOfClusters = setpoint;
		//performClustering(population,setpoint);
		double[] Deviation = new double[10];
		double[] Delta = new double[9];
		int currentNumberOfClusters = 1;
		boolean isNumberOfClustersFound = false;
		//int threshold = 10;
		double maxChange = 1;
		Deviation[0] = performClustering(population, 1, false);
		Deviation[1] = performClustering(population, 2, false);
		Delta[0] = Deviation[0]-Deviation[1];
//		System.out.println(Delta[0]);
		for(int k=2; k<10; k++)
		{
			Deviation[k] = performClustering(population, k+1, false);
			Delta[k-1] = Deviation[k-1]-Deviation[k];
//			System.out.println(Delta[k-1]);
//			if(Delta[k-1]<0.5*threshold & !isNumberOfClustersFound)
//			{
//				currentNumberOfClusters = k;
//				isNumberOfClustersFound = true;
//			}
//			if(Delta[k-1]>1.5*threshold & isNumberOfClustersFound)
//				isNumberOfClustersFound = false;
			if(Math.abs(Delta[k-2]/Delta[k-1])>maxChange)
			{
				currentNumberOfClusters = k;
				maxChange = Math.abs(Delta[k-2]/Delta[k-1]);
			}
			isNumberOfClustersFound = true;
		}	
			
		if(generation == 500)
			{
				//population.print();
				System.out.println(currentNumberOfClusters);
				Deviation[currentNumberOfClusters-1] = performClustering(population, currentNumberOfClusters, true);	
			}
			
		//for(int k=0; k<2*setpoint; k++)
			//System.out.println(Deviation[k]);
		//
		return currentNumberOfClusters;		
	}
	
	public double performClustering(GAPopulation population, int K, boolean print)
	{
		// cluster and its entries
		int[][] clusters = new int[K][population.individuals.length];
		int[] cards = new int[K];
		// center of the clusters
		GAIndividual[] center = new GAIndividual[K];
		GAIndividual[] sums = new GAIndividual[K];
		GAIndividual[] best = new GAIndividual[K];
		double[] distanceToCenter = new double[K];
		double distanceTotheCenter;
		double deviation = 0;
		
		//System.out.println(population.individuals[0].variables.length);
		//System.out.println(K);
		
		for(int k=0; k<K; k++)
		{
			center[k] = new GAIndividual();
			sums[k] = new GAIndividual();
			center[k].variables = new double[population.individuals[0].variables.length];
			sums[k].variables = new double[population.individuals[0].variables.length];
			for(int j=0; j<population.individuals[0].variables.length; j++)
			{
				sums[k].variables[j] = 0;
				center[k].variables[j] = 0;
			}
		}
		// distances among individuals
		// double[][] distances = new double[population.individuals.length][population.individuals.length];
		// calculate the distance
		// for(int i=0; i<population.individuals.length; i++)
		//	for(int j=0; j<population.individuals.length; j++)
		//		distances[i][j] = population.individuals[i].distanceTo(population.individuals[j]);
				
		// initialization (Random) Partition
		int numberOfEntries = (int)Math.floor(population.individuals.length/K);
		cards[K-1] = population.individuals.length;
		for(int k=0; k<K-1; k++)
		{
			for(int i=0; i<numberOfEntries; i++)
				clusters[k][i] = k*numberOfEntries+i;
			cards[k] = numberOfEntries;
			cards[K-1] = cards[K-1]-cards[k];
		}
		for(int i=0; i<cards[K-1]; i++)
			clusters[K-1][i] = (K-1)*numberOfEntries+i;
		
		for(int k=0; k<K; k++)
			for(int j=0; j<population.individuals[0].variables.length; j++)
			{
				for(int i=0; i<cards[k]; i++)
					sums[k].variables[j] = sums[k].variables[j]+population.individuals[clusters[k][i]].variables[j];
				center[k].variables[j] = sums[k].variables[j]/cards[k];
			}
		
		// Adjustment 
		boolean isAllClustersDetermined = false;
		boolean isOneClusterDetermined = true;
		int newClusterIndex;
		double newDistance;
		class Change
		{
			int locationInOldCluster;
			int oldCluster;
			int newCluster;	
			Change()
			{
				locationInOldCluster = 0;
				oldCluster = 0;
				newCluster = 0;				
			}
		}
		Change[] change = new Change[population.individuals.length];
		for(int i=0; i<population.individuals.length; i++)
			change[i] = new Change();
		int numberOfChanges = 0;
		int numberOfIterations = 0;
		
		while(!isAllClustersDetermined)
		{
			numberOfChanges = 0;
			for(int k=0; k<K && numberOfChanges==0; k++)
				for(int i=0; i<cards[k] && numberOfChanges==0; i++)
				{
					isOneClusterDetermined = true;
					newClusterIndex = k;
					distanceTotheCenter = population.individuals[clusters[k][i]].distanceTo(center[k]);
					newDistance = distanceTotheCenter;
					for(int kk=0; kk<K; kk++)
					{
						distanceToCenter[kk] = population.individuals[clusters[k][i]].distanceTo(center[kk]);
						if(newDistance>distanceToCenter[kk])
						{
							isOneClusterDetermined = false;
							newClusterIndex = kk;
							newDistance = distanceToCenter[kk];
						}
					}
					if(isOneClusterDetermined==false)
					{
						//System.out.println(i);
						//System.out.println(k);
						//System.out.println(newClusterIndex);
						change[numberOfChanges].locationInOldCluster = i;
						change[numberOfChanges].oldCluster = k;
						change[numberOfChanges].newCluster = newClusterIndex;
						numberOfChanges++;
						//System.out.println("number of changes"+ numberOfChanges);
					}	
				}
					for(int j=0; j<numberOfChanges; j++)
					{
						clusters[change[j].newCluster][cards[change[j].newCluster]] = clusters[change[j].oldCluster][change[j].locationInOldCluster];
						cards[change[j].newCluster]++;
						
						clusters[change[j].oldCluster][change[j].locationInOldCluster] = clusters[change[j].oldCluster][cards[change[j].oldCluster]-1];
						cards[change[j].oldCluster] = cards[change[j].oldCluster]-1;
					}
					
					for(int kk=0; kk<K; kk++)
					{
						for(int j=0; j<population.individuals[0].variables.length; j++)
						{
							sums[kk].variables[j] = 0;
							center[kk].variables[j] = 0;
							for(int ii=0; ii<cards[kk]; ii++)
								sums[kk].variables[j] = sums[kk].variables[j]+population.individuals[clusters[kk][ii]].variables[j];
							center[kk].variables[j] = sums[kk].variables[j]/cards[kk];
						}
					}
					if(numberOfChanges==0||numberOfIterations>1000000)
						isAllClustersDetermined = true;
					
			
			//System.out.println("TOTAL Number of Exchange = "+numberOfChanges);
			
//			for(int j=0; j<numberOfChanges; j++)
//			{
//				clusters[change[j].newCluster][cards[change[j].newCluster]] = clusters[change[j].oldCluster][change[j].locationInOldCluster];
//				cards[change[j].newCluster]++;
//				
//				clusters[change[j].oldCluster][change[j].locationInOldCluster] = clusters[change[j].oldCluster][cards[change[j].oldCluster]-1];
//				cards[change[j].oldCluster] = cards[change[j].oldCluster]-1;
//			}
//			

			numberOfIterations++;
			//System.out.println("Number of iterations = " + numberOfIterations);		
						
			
			//for(int k=0; k<K; k++)
			//	if(cards[k]==0)
			//		isAllClustersDetermined = false;
			
		}//end of while	
		deviation = 0;
		for(int k=0; k<K; k++)
			for(int i=0; i<cards[k]; i++)
				deviation = deviation+population.individuals[clusters[k][i]].distanceTo(center[k]);
		//System.out.println("deviation = "+deviation);
		
		
		if(print==true)
			for(int k=0; k<K; k++)
			{
				//System.out.println(cards[k]);
				//System.out.println(center[k].variables[0]);
				best[k] = population.individuals[clusters[k][0]];
				for(int i=1; i<cards[k]; i++)
					if(population.individuals[clusters[k][i]].fitness>best[k].fitness)
						best[k] = population.individuals[clusters[k][i]];
				best[k].print();
			}
		
		return deviation;
	}//end of performClustering 
}