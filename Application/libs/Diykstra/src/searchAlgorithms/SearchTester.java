package searchAlgorithms;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import graphs.Graph;
import graphs.Node;
import graphs.WeightedGraph;
import graphs.io.GraphIO;
import searchAlgorithms.BFS.BFS;
import searchAlgorithms.dijkstra.Dijkstra;

public class SearchTester {

	public static void main(String[] args) {
		try {
		Path currentRelativePath = Paths.get("");
		String currentDir = currentRelativePath.toAbsolutePath().toString();
		
			
		GraphIO gio = new GraphIO();
		WeightedGraph Ginput = gio.loadWeighted(currentDir + "/assets/graph_inputs/debug/trialWeightedGraph.txt");
		System.out.println(Ginput);
		
		String startNodeName = "Finom";
		String destNodeName = "Makos";
		
		Node startNode = Ginput.findNode(startNodeName);
		Node destNode = Ginput.findNode(destNodeName);
		
		SearchAlgorithm dijkstra = new Dijkstra(Ginput);
		
		float dist = dijkstra.searchDistance(startNode, destNode);
		
		Route route = dijkstra.searchRoute(startNode, destNode);
		
		ArrayList<Float> distances = dijkstra.searchDistances(startNode);
		
		System.out.println(dist);
		System.out.println(route);
		System.out.println(distances);
		
		gio.write(currentDir + "/assets/graph_inputs/debug/res.txt", Ginput);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
