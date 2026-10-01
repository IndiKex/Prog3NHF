package searchAlgorithms;

import java.util.ArrayList;

import graphs.Node;

public interface SearchAlgorithm {
	public Route searchRoute(Node start_node, Node dest_node);
	public ArrayList<Float> searchDistances(Node start_node);
	public float searchDistance(Node start_node, Node dest_node);
}
