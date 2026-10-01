package searchAlgorithms.BFS;

import java.util.ArrayList;

import containers.FIFO;
import graphs.Graph;
import graphs.Node;
import searchAlgorithms.Route;
import searchAlgorithms.SearchAlgorithm;
import searchAlgorithms.SearchNode;

public class BFS implements SearchAlgorithm {
	Graph G = new Graph();
	ArrayList<Integer> distances = new ArrayList<>();
	ArrayList<SearchNode> bfs_nodes_bts = new ArrayList<>();
	ArrayList<SearchNode> bfs_nodes = new ArrayList<>();
	
	private Node search_start, search_dest;
	
	public BFS(Graph G) {
		this.G = G;
		
		bfs_nodes_bts.ensureCapacity(G.nodes.size());
		bfs_nodes.ensureCapacity(G.nodes.size());
		for (int i = 0; i < G.nodes.size(); i++) {
			SearchNode sn = new SearchNode(G.nodes.get(i), i);
			bfs_nodes_bts.add(sn);
		}
	}
	
	private void initializeBFSNodes() {
		boolean fill = bfs_nodes.size() == 0;
		for (int i = 0; i < bfs_nodes_bts.size(); i++) {
			if (fill) bfs_nodes.add(bfs_nodes_bts.get(i));
			else bfs_nodes.set(i, bfs_nodes_bts.get(i));
		}
	}
	
	public void search(Node start_node, Node dest_node) {
		if (start_node == search_start && dest_node == search_dest)
			return;
		search_start = start_node;
		search_dest = dest_node;
		
		initializeBFSNodes();
		
		ArrayList<Integer> visited = new ArrayList<>();
		FIFO<Integer> neighbours = new FIFO<>();
		
		SearchNode start_bfs_node = bfs_nodes.get(start_node.id);
		start_bfs_node.distance = 0;
		start_bfs_node.parent = -1;
		start_bfs_node.parent_edge_id = -1;
		neighbours.push(start_node.id);
		
		while (neighbours.size() > 0) {
			int current_node_id = neighbours.pop();
			SearchNode current_node = bfs_nodes.get(current_node_id);
			visited.add(current_node_id);
			
			if (dest_node != null && current_node_id == dest_node.id)
				break;
			
			for (int i = 0; i < current_node.neighbours.size(); i++) {
				int neighbour_id = current_node.neighbours.get(i);
				SearchNode neighbour = bfs_nodes.get(neighbour_id);

				if (neighbour.distance == Float.MAX_VALUE) { 
					neighbours.push(neighbour_id);
					neighbour.distance = current_node.distance + 1f;
					neighbour.parent = current_node_id;
					neighbour.parent_edge_id = current_node.edges.get(i);
				}
				
			}
		}
	}
	
	public Route searchRoute(Node start_node, Node dest_node) {
		search(start_node, dest_node);
		
		Route route = new Route();
		SearchNode dest_bfs_node = bfs_nodes.get(dest_node.id);
		route.add(dest_node);
		route.add(G.edges.get(dest_bfs_node.parent_edge_id));
		int parent_id = dest_bfs_node.parent;
		while (parent_id != -1 && route.nodes.size() <= G.nodes.size()) {
			Node parent_node = G.nodes.get(parent_id);
			route.add(parent_node);
			SearchNode parent_bfs_node = bfs_nodes.get(parent_id);
			parent_id = parent_bfs_node.parent;

			if (parent_bfs_node.parent_edge_id != -1)
				route.add(G.edges.get(parent_bfs_node.parent_edge_id));
		}
		
		route.reverse();
		return route;
	}
	
	public float searchDistance(Node start_node, Node dest_node) {
		search(start_node, dest_node);
		return bfs_nodes.get(dest_node.id).distance;
	}
	
	public ArrayList<Float> searchDistances(Node start_node) {
		search(start_node, null);
		
		ArrayList<Float> distances = new ArrayList<>();
		distances.ensureCapacity(bfs_nodes.size());
		for (int i = 0; i < bfs_nodes.size(); i++) {
			distances.add((float)bfs_nodes.get(i).distance);
		}
		return distances;
	}
}
