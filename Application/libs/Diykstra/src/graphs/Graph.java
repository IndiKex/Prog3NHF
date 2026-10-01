package graphs;

import java.util.ArrayList;

public class Graph {
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Edge> edges = new ArrayList<>();
	public ArrayList<Float> weights = new ArrayList<>();
	
	public final Node addNode(String name) {
		Node n = new Node(name);
		nodes.add(n);
		n.id = nodes.size() - 1;
		return n;
	}
	
	public final Node findNode(String name) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).name.equals(name))
				return nodes.get(i);
		}
		return null;
	}
	
	public boolean drawEdge(String name, Node start_node, Node dest_node) {
		Edge e = new Edge(name, start_node, dest_node);
		edges.add(e);
		e.id = edges.size() - 1;
		start_node.addEdge(e);
		if (e.start_node != e.dest_node) dest_node.addEdge(e);
		return true;
	}
	
	public String toString() {
		String[] rows = new String[nodes.size()];
		
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			String row = node.name + ": ";
			for (int j = 0; j < node.edges_out.size(); j++) {
				Edge edge = node.edges_out.get(j);
				Node neighbour = edge.dest_node;
				if (j < node.edges_out.size() - 1)
					row += neighbour.name + "(" + edge.name + "), ";
				else
					row += neighbour.name + "(" + edge.name + ")";
			}
			rows[i] = row + "\n";
		}
		
		String edge_list = "";
		for (int i = 0; i < rows.length; i++) {
			edge_list += rows[i];
		}
		return edge_list;
	}
}
