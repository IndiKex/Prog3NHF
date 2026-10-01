package graphs;

public class SimpleGraph extends Graph {
	public boolean drawEdge(String name, Node start_node, Node dest_node) {
		if (!isSimpleEdge(start_node, dest_node)) return false;
		
		Edge e = new Edge(name, start_node, dest_node);
		edges.add(e);
		e.id = edges.size() - 1;
		start_node.addEdge(e);
		dest_node.addEdge(e);
		return true;
	}
	
	private boolean isSimpleEdge(Node start_node, Node dest_node) {
		if (start_node == dest_node)
			return false;
		for (int i = 0; i < start_node.edges_out.size(); i++) {
			if (start_node.edges_out.get(i).dest_node == dest_node)
				return false;
		}
		return true;
	}
}
