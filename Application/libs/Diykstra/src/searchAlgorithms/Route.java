package searchAlgorithms;

import java.util.ArrayList;

import graphs.Edge;
import graphs.Node;

public class Route {
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Edge> edges = new ArrayList<>();
	private boolean added_node = false;
	
	public void add(Node n) {
		if (added_node) return; 
		nodes.add(n);
		added_node = true;
	}
	
	public void add(Edge e) {
		if (!added_node) return;
		edges.add(e);
		added_node = false;
	}
	
	public void reverse() {
		nodes = new ArrayList<Node>(nodes.reversed());
		edges = new ArrayList<Edge>(edges.reversed());
	}
	
	public String toString() {
		String res = nodes.get(0).name;
		
		for (int i = 0; i < nodes.size() - 1; i++) {
			res += "->";
			res += edges.get(i).name;
			res += "->";
			res += nodes.get(i+1).name;
		}
		
		return res;
		
	}
}
