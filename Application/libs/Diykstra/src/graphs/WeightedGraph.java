package graphs;


public class WeightedGraph extends Graph {
	public boolean drawEdge(String name, Node start_node, Node dest_node) {
		throw new UnsupportedOperationException();
	}
	
	public boolean drawEdge(String name, Node start_node, Node dest_node, float weight) {
		Edge e = new Edge(name, start_node, dest_node, weight);
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
					row += neighbour.name + "(" + edge.name + "," + edge.weight + "), ";
				else
					row += neighbour.name + "(" + edge.name + "," + edge.weight + ")";
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
