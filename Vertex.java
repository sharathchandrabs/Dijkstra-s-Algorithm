//Name: Sharath Chandra Bagur Suryanarayanaprasad Student ID: 800974802
import java.util.HashMap;
import java.util.Map;

// Represents a vertex in the graph.
public class Vertex {
	public String name; // Vertex name
	public Map<Vertex, Edge> adj; // Adjacent vertices
	public Vertex prev; // Previous vertex on shortest path
	public float dist; // Distance of path
	public boolean isUp = true;

	public Vertex(){}
	
	public Vertex(String nm) {
		name = nm;
		adj = new HashMap<Vertex, Edge>();
		reset();
	}

	public void reset() {
		dist = Graph.INFINITY;
		prev = null;
	}

}