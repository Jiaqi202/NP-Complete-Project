package npcomplete;

import java.util.*;
import org.jgrapht.graph.*;

public class HamiltonianCycle2 {

	public static <V, E> List <V> getApproximateOptimalForCompleteGraph(SimpleWeightedGraph<V,E> g)
	{
		List<V> vertices = new LinkedList<V>(g.vertexSet());
		
		if((vertices.size() * (vertices.size() -1)/ 2) != g.edgeSet().size() )
		{
			return null;
		}
		
		List<V> tour = new LinkedList<V>();
		
		while (tour.size() != g.vertexSet().size())
		{
			boolean firstEdge = true;
			double minEdgeValue = 0;
			int minVertexFound = 0;
			int vertexConnectedTo = 0;
			
			for(int i =0; i< vertices.size(); i++){
				
				V v = tour.get(i);
				for (int j =0; j< vertices.size(); j++){
					
					double weight = g.getEdgeWeight(g.getEdge(v , vertices.get(j)));
					
					if (firstEdge || (weight < minEdgeValue)){
						firstEdge = false;
						minEdgeValue = weight;
						minVertexFound = j;
						vertexConnectedTo = i;
						
					}
				}
				
			}
			
			tour.add(vertexConnectedTo, vertices.get(minVertexFound));
			vertices.remove(minVertexFound);
		}
		return tour;
	}
}
