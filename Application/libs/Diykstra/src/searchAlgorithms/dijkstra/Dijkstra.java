package searchAlgorithms.dijkstra;

import java.util.ArrayList;

import graphs.Edge;
import graphs.Node;
import graphs.WeightedGraph;
import searchAlgorithms.Route;
import searchAlgorithms.SearchAlgorithm;
import searchAlgorithms.SearchNode;

public class Dijkstra implements SearchAlgorithm {
	WeightedGraph G;
	ArrayList<Float> distances = new ArrayList<>();
	ArrayList<SearchNode> search_nodes_bts = new ArrayList<>();
	ArrayList<SearchNode> search_nodes = new ArrayList<>();

	private Node search_start, search_dest;
	
	public Dijkstra(WeightedGraph G) {
		this.G = G;
		
		search_nodes_bts.ensureCapacity(G.nodes.size());
		search_nodes.ensureCapacity(G.nodes.size());
		for (int i = 0; i < G.nodes.size(); i++) {
			search_nodes_bts.add(new SearchNode(G.nodes.get(i), i));
			search_nodes.add(new SearchNode(G.nodes.get(i), i));
		}
	}
	
	private void initializeSearchNodes() {
		for (int i = 0; i < search_nodes_bts.size(); i++) {
			search_nodes.set(i, search_nodes_bts.get(i));
		}
	}
	
	private boolean canContinue(SearchNode current_node) {
		for (int i = 0; i < current_node.neighbours.size(); i++) {
			int neighbour_id = current_node.neighbours.get(i);
			SearchNode neighbour = search_nodes.get(neighbour_id);
			if (!neighbour.done) return true;
		}
		return false;
	}
	
	public void search(Node start_node, Node dest_node) {
		if (start_node == search_start && dest_node == search_dest)
			return;
		search_start = start_node;
		search_dest = dest_node;
		
		initializeSearchNodes();
		
		SearchNode current_node = search_nodes.get(start_node.id);
		current_node.distance = 0;
		current_node.parent = -1;
		current_node.parent_edge_id = -1;
		current_node.done = true;
		if (dest_node != null && current_node.id == dest_node.id)
			return;
		
		while (canContinue(current_node)) {
			float smallest_distance = Float.MAX_VALUE;
			SearchNode smallest_neighbour = search_nodes.get(current_node.neighbours.get(0));
			for (int i = 0; i < current_node.neighbours.size(); i++) {
				int neighbour_id = current_node.neighbours.get(i);
				SearchNode neighbour = search_nodes.get(neighbour_id);
				if (!neighbour.done) smallest_neighbour = neighbour;
			}
			
			for (int i = 0; i < current_node.neighbours.size(); i++) {
				int neighbour_id = current_node.neighbours.get(i);
				SearchNode neighbour = search_nodes.get(neighbour_id);
				
				if (!neighbour.done) {
					Edge neighbour_edge = G.edges.get(current_node.edges.get(i));
					float new_distance = current_node.distance + neighbour_edge.weight;
					if (new_distance < neighbour.distance) {
						neighbour.distance = new_distance;
						neighbour.parent = current_node.id;
						neighbour.parent_edge_id = current_node.edges.get(i);
					}
					if (neighbour.distance < smallest_distance) {
						smallest_distance = neighbour.distance;
						smallest_neighbour = neighbour;
					}
				}
			}
			
			current_node = smallest_neighbour;
			current_node.done = true;
			if (dest_node != null && current_node.id == dest_node.id)
				break;
		}
		
	}
	
	public Route searchRoute(Node start_node, Node dest_node) {
		search(start_node, dest_node);
		
		Route route = new Route();
		SearchNode dest_bfs_node = search_nodes.get(dest_node.id);
		route.add(dest_node);
		route.add(G.edges.get(dest_bfs_node.parent_edge_id));
		int parent_id = dest_bfs_node.parent;
		while (parent_id != -1 && route.nodes.size() <= G.nodes.size()) {
			Node parent_node = G.nodes.get(parent_id);
			route.add(parent_node);
			SearchNode parent_bfs_node = search_nodes.get(parent_id);
			parent_id = parent_bfs_node.parent;

			if (parent_bfs_node.parent_edge_id != -1) {
				route.add(G.edges.get(parent_bfs_node.parent_edge_id));
			}
		}
		
		route.reverse();
		return route;
	}
	
	public ArrayList<Float> searchDistances(Node start_node) {
		search(start_node, null);
		
		ArrayList<Float> distances = new ArrayList<>();
		distances.ensureCapacity(search_nodes.size());
		for (int i = 0; i < search_nodes.size(); i++) {
			distances.add((float)search_nodes.get(i).distance);
		}
		return distances;
	}
	
	public float searchDistance(Node start_node, Node dest_node) {
		search(start_node, dest_node);
		
		return search_nodes.get(dest_node.id).distance;
	}
}
