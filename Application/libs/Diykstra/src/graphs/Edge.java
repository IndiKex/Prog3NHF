package graphs;

public class Edge {
	public Node start_node, dest_node;
	public String name;
	public float weight;
	public int id = -1;
	
	public Edge(String name, Node start_node, Node dest_node) {
		this.start_node = start_node;
		this.dest_node = dest_node;
		this.name = name;
	}
	
	public Edge(String name, Node start_node, Node dest_node, float weight) {
		this(name, start_node, dest_node);
		this.weight = weight;
	}
}
