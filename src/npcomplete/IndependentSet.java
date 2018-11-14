package npcomplete;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class IndependentSet {
	//Give an matrix for graph G, return Max independent set of G
	static VertexSet MaximumIndependentSet(int[][] G)
	{
		int numVerts = G.length;
		
		VertexSet B = new VertexSet(numVerts);
		VertexSet S = new VertexSet(numVerts);
		VertexSet F = new VertexSet(numVerts);
		
		FindSets (G, F, B, S, 0);
		
		return B;
	}
	
	static void FindSets(int[][] G, VertexSet F, VertexSet B, VertexSet S, int v)
	{
		int n = G.length;
		if (v == n)
		{
			if(S.getSize() > B.getSize()){
				B.copyFrom(S);
			}
			return;
		}
		VertexSet F_p = new VertexSet(n);
		F_p.copyFrom(F);
		F_p.addVertex(v);
		
		FindSets(G, F_p, B, S, v+1);
		
		if(!F.isInSet(v)){
			S.addVertex(v);
			for(int j =0; j<n; j++){
				if (G[v][j] !=0){
					F_p.addVertex(j);
				}
			}
			FindSets(G, F_p, B, S, v+1);
			S.removeVertex(v);
		}
	}
	static int verifyIndSet(int[][] G, VertexSet S){
			int n = G.length;
			int count = 0;
			for (int i =0; i<n; i++){
				if (!S.isInSet(i))
					continue;
				for(int j =0; j<n; j++)
					if (G[i][j] == 1 && S.isInSet(j))
						return -1;
				
				count++;
			}
			return count;
	}
	
	public static class VertexSet{
		private int size;
		private boolean[] set;
		
		public VertexSet(int numVerts){
			set = new boolean[numVerts];
			size = 0;
		}
		
		public void addVertex(int vertexIndex){
			if(set[vertexIndex])
				return;
			set [vertexIndex] = true;
			size++;
		}
		
		public void removeVertex(int vertexIndex){
			if(!set[vertexIndex])
				return;
			set[vertexIndex] = false;
			size--;
		}
		
		public boolean isInSet(int vertexIndex){
			return set[vertexIndex];
		}
		
		public int getSize(){
			return size;
		}
		
		public void copyFrom(VertexSet otherSet){
			this.size = otherSet.size;
			this.set = otherSet.set.clone();
			
		}
		
		public void printVertices(){
			for(int i =0; i< set.length;i++)
				if(set[i])
					System.out.printf("%d", i);
			System.out.printf("\n");
		}
	}
	
	public static void main(String[] args){
		Scanner s;
		if(args.length > 0){
			try{
				
				s = new Scanner(new File(args[0]));
			}catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n", args[0]);
				return;
				
			}
			
			System.out.printf("Reading input values from %s \n", args[0]);
		}else{
			
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
			
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n", graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i =0; i < n && s.hasNextInt(); i++){
				
				for(int j =0; j< n && s.hasNextInt(); j++){
					
					G[i][j] = s.nextInt();
					valuesRead++;
					
				}
			}
			
			if (valuesRead < n*n){
				
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n", graphNum);
				break;
				
			}
			long startTime = System.currentTimeMillis();
			
			VertexSet maxSet = MaximumIndependentSet(G);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime - startTime)/1000.0;
			
			int setSize = (maxSet == null)? -1 : verifyIndSet(G, maxSet);
			if(setSize == -1)
				System.out.printf("Graph %d: Maximum independent set found with size %d. \n", graphNum, maxSet.getSize());
			
			else{
				System.out.printf("Graph %2d: Maximum independent set found with size %d.\n", graphNum, setSize);
				System.out.printf("		S=");
				maxSet.printVertices();
			}
		}
		
		graphNum--;
		System.out.printf("Processed %d graph%s.\n", graphNum, (graphNum !=1)? "s":"");
		System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
		System.out.printf("Average Time (seconds): %.2f\n", (graphNum>0)? totalTimeSeconds/graphNum:0);
		
	}
}

