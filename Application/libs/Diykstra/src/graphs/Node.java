package graphs;

import java.util.ArrayList;

public class Node {
	public String name;
	public int degree_out = 0, degree_in = 0;
	public ArrayList<Edge> edges_out, edges_in;
	public int id = -1;
	
	public Node(String name) {
		this.name = name;
		edges_out = new ArrayList<Edge>();
		edges_in = new ArrayList<Edge>();
	}
	
	boolean addEdge(Edge e) {
		if (this == e.start_node && this == e.dest_node) {
			edges_out.add(e);
			edges_in.add(e);
			degree_out += 1;
			degree_in += 1;
		} else if (this == e.start_node) {
			edges_out.add(e);
			degree_out += 1;
		} else if (this == e.dest_node) {
			edges_in.add(e);
		} else
			return false;
		return true;
	}
	
}
