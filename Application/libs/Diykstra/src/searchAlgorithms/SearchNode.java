package searchAlgorithms;

import java.util.ArrayList;

import graphs.Edge;
import graphs.Node;

public class SearchNode {
	public float distance;
	public int parent, parent_edge_id;
	public int id;
	public boolean done = false;
	public ArrayList<Integer> neighbours = new ArrayList<>();
	public ArrayList<Integer> edges = new ArrayList<>();
	
	public SearchNode(Node n, int id) {
		this.id = id;
		this.distance = Float.MAX_VALUE;
		this.parent = -1;
		for (int i = 0; i < n.edges_out.size(); i++) {
			Edge e = n.edges_out.get(i);
			edges.add(e.id);
			neighbours.add(e.dest_node.id);
		}
	}
}
