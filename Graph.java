//Name: Sharath Chandra Bagur Suryanarayanaprasad Student ID: 800974802
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.List;
import java.util.Queue;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Graph {
	public static final int INFINITY = Integer.MAX_VALUE;
	private Map<String, Vertex> graphVerticesMap = new HashMap<String, Vertex>();
	static MinimumHeap heap;
	static File file;
	static PrintWriter pw;

	/**
	 * Add a new edge to the graph.
	 */
	public void addEdge(StringTokenizer st) {
		String source = st.nextToken();
		String dest = st.nextToken();
		String edgeWeight = st.nextToken();
		addEdge(source, dest, edgeWeight);
	}

	public void addEdge(String sourceName, String destName, String edgeWeight) {
		Vertex v = getVertex(sourceName);
		Vertex w = getVertex(destName);
		float edgeweight = (float) 0.0;
		try {
			edgeweight = Float.parseFloat(edgeWeight);
		} catch (NumberFormatException e) {
			System.out.println("Invalid weight value.");
			throw e;
		}
		Edge e = new Edge(edgeweight);
		v.adj.put(w, e);
	}


	public void printPath(String destName) {
		Vertex w = graphVerticesMap.get(destName);
		if (w == null)
			throw new NoSuchElementException("Destination vertex not found");
		else if (w.dist == INFINITY)
			System.out.println(destName + " is not reachable");
		else {
			System.out.print("(Distance is: " + w.dist + ") ");
			printPath(w);
			System.out.println();
		}
	}


	private Vertex getVertex(String vertexName) {
		Vertex v = graphVerticesMap.get(vertexName);
		if (v == null) {
			v = new Vertex(vertexName);
			graphVerticesMap.put(vertexName, v);
		}
		return v;
	}


	private void printPath(Vertex dest) {
		if (dest.prev != null) {
			printPath(dest.prev);
			System.out.print(" ");
			pw.print(" ");
			
		}

		System.out.print(dest.name);
		pw.print(dest.name);
	}

	/**
	 * Initializes the vertex output info prior to running any shortest path
	 * algorithm.
	 */
	private void clearAll() {
		for (Vertex v : graphVerticesMap.values())
			v.reset();
	}

	/**
	 * Single-source unweighted shortest-path algorithm.
	 */
	public List<Vertex> unweighted(String startName) {
		clearAll();

		Vertex start = graphVerticesMap.get(startName);
		if (start == null)
			throw new NoSuchElementException("Start vertex not found");

		Queue<Vertex> q = new LinkedList<Vertex>();
		List<Vertex> discoveredVertices = new ArrayList<Vertex>();
		q.add(start);
		start.dist = 0;

		while (!q.isEmpty()) {
			Vertex v = q.remove();
			for (Map.Entry<Vertex, Edge> w : v.adj.entrySet()) {
				if (w.getKey().dist == INFINITY && (w.getKey().isUp && w.getValue().isUp)) {
					w.getKey().dist = v.dist + 1;
					w.getKey().prev = v;
					discoveredVertices.add(w.getKey());
					q.add(w.getKey());
				}
			}
		}

		return discoveredVertices;
	}



	public void alterEdge(StringTokenizer st, boolean isUp) {
		String source = st.nextToken(), dest = st.nextToken();
		if (getVertex((source)) != null && getVertex(dest) != null) {
			if (getVertex((source)).adj.keySet().contains(getVertex(dest))) {
				getVertex((source)).adj.get(getVertex(dest)).isUp = isUp;
			} else {
				System.out.println("Destination not adjacent to given source.");
			}

		} else {
			System.out.println("Source/Destination name invalide. try again");
		}
	}

	public void alterVertex(StringTokenizer st, boolean isUp) {
		String source = st.nextToken();
		if (getVertex(source) != null) {
			getVertex(source).isUp = isUp;
		} else {
			System.out.println("Invalid source. Check again.");
		}
	}

	static void onInputError() {
		System.out.println("Invalid input, please provide an input using one of the below mentioned values");
		System.out.println("1. addedge tailvertex headvertex transmit-time");
		System.out.println("2. deleteedge tailvertex headvertex");
		System.out.println("3. edgedown tailvertex headvertex");
		System.out.println("4. edgeup tailvertex headvertex");
		System.out.println("5. vertexdown vertex");
		System.out.println("6. vertexup vertex");
		System.out.println("7. path from_vertex to_vertex");
		System.out.println("8. print");
		System.out.println("9. reachable");
		System.out.println("10. quit");
	}

	public List<Vertex> shortestPath(String startName, String destName) {
		clearAll();
		// String evaluationNode;
		List<Vertex> shortestPathVertices = new ArrayList();
		Vertex start = graphVerticesMap.get(startName);
		if (start == null)
			throw new NoSuchElementException("Start vertex not found");
		else if (!start.isUp)
			throw new NoSuchElementException("Start vertex is not up");
		else {
			start.dist = 0;
			heap.insert(start);
			while (!heap.isEmpty()) {
				Vertex evaluationNode = heap.deleteMin();
				shortestPathVertices.add(evaluationNode);
				for (Map.Entry<Vertex, Edge> adjvertex : evaluationNode.adj.entrySet()) {
					if (!adjvertex.getKey().isUp || !adjvertex.getValue().isUp) {
						continue;
					} else {
						if (adjvertex.getKey().dist > evaluationNode.dist + adjvertex.getValue().weight) {
							int indexValue;
							if ((indexValue = heap.returnIndex(adjvertex.getKey())) > -1) {
								heap.delete(indexValue);
							}

							adjvertex.getKey().dist = evaluationNode.dist + adjvertex.getValue().weight;
							adjvertex.getKey().prev = evaluationNode;
							heap.insert(adjvertex.getKey());
						}
					}
				}
			}

			return shortestPathVertices;
		}
	}


	public static void main(String[] args) {
		
		Graph g = new Graph();
		
		
		try {
			System.out.println(args[0]);
			FileReader fin = new FileReader(args[0]);
			Scanner graphFile = new Scanner(fin);
			
			
			
			// Read the edges and insert
			String line;
			while (graphFile.hasNextLine()) {
				line = graphFile.nextLine();
				StringTokenizer st = new StringTokenizer(line);
				try {
					if (st.countTokens() != 3) {
						System.err.println("Skipping ill-formatted line " + line);
						continue;
					}
					String source = st.nextToken();
					String dest = st.nextToken();
					String edgeWeight = st.nextToken();
					g.addEdge(source, dest, edgeWeight);
					g.addEdge(dest, source, edgeWeight);
				} catch (NumberFormatException e) {
					System.err.println("Skipping ill-formatted line " + line);
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		Scanner in;

		
		FileReader fnet = null;
		try {
			fnet = new FileReader(args[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		  Scanner networkFile = new Scanner(fnet);
		  String lines;
		  
		  file = new File(args[2]);
			FileWriter fw = null;
			try {
				fw = new FileWriter(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			pw = new PrintWriter(fw);
			//pw.println("Hello World");
			//pw.close();
		  
		  
		  
		  
		loop: while (networkFile.hasNextLine()) {
		   lines = networkFile.nextLine();
		   StringTokenizer st = new StringTokenizer(lines);


			/*in = new Scanner(System.in);
			String line = in.nextLine();
			StringTokenizer st = new StringTokenizer(line);*/
			if (st.countTokens() == 1) {
				switch (st.nextToken()) {
				case "quit":
					break loop;
				case "print":
					Map<String, Vertex> sortedMap1 = new TreeMap<String, Vertex>(g.graphVerticesMap);
					for (Map.Entry<String, Vertex> values : sortedMap1.entrySet()) {
						System.out.println(values.getKey());
						pw.println(values.getKey());
						Map<Vertex, Edge> sortedVertices = new TreeMap<Vertex, Edge>(new Comparator<Vertex>() {

							@Override
							public int compare(Vertex o1, Vertex o2) {
								return o1.name.compareTo(o2.name);
							}
						});
						for (Map.Entry<Vertex, Edge> map : values.getValue().adj.entrySet()) {
							sortedVertices.put(map.getKey(), map.getValue());
						}
						for (Map.Entry<Vertex, Edge> adjVertex : sortedVertices.entrySet()) {
							System.out.print("\t" + adjVertex.getKey().name);
							pw.print("\t" + adjVertex.getKey().name);
							if (!adjVertex.getKey().isUp) {
								System.out.print(" Down");
								pw.print(" Down");
							}
							System.out.print(" " + adjVertex.getValue().weight);
							pw.print(" " + adjVertex.getValue().weight);
							if (!adjVertex.getValue().isUp) {
								System.out.print(" Down");
								pw.print(" Down");
							}
							System.out.println("");
							pw.println("");
						}
					}
					break;
				case "reachable":
					
					Map<String, Vertex> sortedMap = new TreeMap<String, Vertex>(g.graphVerticesMap);
					for (Map.Entry<String, Vertex> value : sortedMap.entrySet()) {
						if (value.getValue().isUp) {
							System.out.println(value.getKey());
							pw.println(value.getKey());
							List<Vertex> reachableVertices = g.unweighted(value.getKey());
							Collections.sort(reachableVertices, new Comparator<Vertex>() {

								@Override
								public int compare(Vertex o1, Vertex o2) {
									return o1.name.compareTo(o2.name);
								}
							});
							for (Vertex vertex : reachableVertices) {
								System.out.println("\t" + vertex.name);
								pw.println("\t" + vertex.name);
							}
							System.out.println();
							pw.println();
						}
					}
					break;
				default:
					onInputError();
					break;
				}
			} else if (st.countTokens() == 2) {
				switch (st.nextToken()) {
				case "vertexdown":
					g.alterVertex(st, false);
					break;
				case "vertexup":
					g.alterVertex(st, true);
					break;
				default:
					onInputError();
					break;
				}
			} else if (st.countTokens() == 3) {
				switch (st.nextToken()) {
				case "deleteedge":
					String headVertex = st.nextToken(), tailVertex = st.nextToken();
					if (g.getVertex(headVertex) != null && g.getVertex(tailVertex) != null) {
						if (g.getVertex(headVertex).adj.keySet().contains(g.getVertex(tailVertex))) {
							g.getVertex(headVertex).adj.remove(g.getVertex(tailVertex));
						} else {
							System.out.println(
									"Destination vertex not adjacent to given source.");
							pw.println("Destination vertex not adjacent to given source.");
						}
					} else {
						System.out.println(
								"The entered source or destination vertex name is not valid, kindly check again!");
						pw.println("The entered source or destination vertex name is not valid, kindly check again!");
					}
					break;
				case "edgedown":
					g.alterEdge(st, false);
					break;
				case "edgeup":
					g.alterEdge(st, true);
					break;
				case "path":
					String source = st.nextToken(), dest = st.nextToken();
					if (g.getVertex((source)) != null && g.getVertex(dest) != null) {
						heap = new MinimumHeap(g.graphVerticesMap.size());
						List<Vertex> value = g.shortestPath(source, dest);
						Vertex v = null;
						for (Vertex vertex : value) {
							v = vertex;
							if (vertex.name.equals(dest)) {
								break;
							}
						}
						if (v != null) {
							g.printPath(v);
							System.out.println(" " + v.dist);
							pw.println(" " + v.dist);
						} else{
							System.out.println("No path exists. Please check and try again");
							pw.println("No path exists. Please check and try again");
						}
							

					} else {
						System.out.println(
								"Source/Destination name invalid! Check again");
						pw.println("Source/Destination name invalid! Check again");
					}
					break;
				default:
					onInputError();
					break;
				}

			} else if (st.countTokens() == 4) {
				switch (st.nextToken()) {
				case "addedge":
					String source = st.nextToken();
					String dest = st.nextToken();
					String edgeWeight = st.nextToken();
					g.addEdge(source, dest, edgeWeight);
					break;
				default:
					onInputError();
					break;
				}

			} else {
				onInputError();
			}

		}
		pw.close();
		System.exit(0);
	}
}
