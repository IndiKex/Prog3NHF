package graphs;


public class GraphTester {

	public static void main(String[] args) {
		WeightedGraph G = new WeightedGraph();
		System.out.println(G);
		
		Node node_a = G.addNode("a");
		Node node_b = G.addNode("b");
		G.addNode("c");
		
		Node node_c = G.findNode("c");
		
		G.drawEdge("ab", node_a, node_b, 1f);
		G.drawEdge("alma", node_a, node_b, 2.5f);
		G.drawEdge("bc", node_a, node_c, -3f);
		G.drawEdge("cc", node_c, node_c, 1/4f);
		
		System.out.println(G);
	}

}
