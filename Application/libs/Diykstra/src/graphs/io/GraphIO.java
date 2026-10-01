package graphs.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import graphs.Edge;
import graphs.Graph;
import graphs.Node;
import graphs.WeightedGraph;

public class GraphIO {
	
	private Exception raiseError(String err, FileReader fr) throws Exception {
		fr.close();
		return new Exception(err);
	}
	
	private void checkSyntax(String file_path, boolean weighted) throws Exception {
		FileReader fr = new FileReader(file_path);
		List<String> lines = fr.readAllLines();
		
		List<String> nodeNames = new ArrayList<>();
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] segments = line.split(" ");
			if (segments.length < 1) throw raiseError("InputFileSyntaxError (" + (i+1) + "): Name for node wasn't given!", fr);
			if (segments[0].length() == 0) throw raiseError("InputFileSyntaxError (" + (i+1) + "): Name for node wasn't given!", fr);
			if (segments[0].charAt(segments[0].length() - 1) != ':') throw raiseError("InputFileSyntaxError (" + (i+1) + "): Colon missing after node name!", fr);
			if (segments[0].length() == 1) throw raiseError("InputFileSyntaxError (" + (i+1) + "): Name for node wasn't given!", fr);
			
			String nodeName = segments[0].substring(0, segments[0].length() - 1);
			nodeNames.add(nodeName);
		}
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] segments = line.split(" ");
			
			for (int j = 1; j < segments.length; j++) {
				String destName;
				if (weighted) {
					String[] nameWeight = segments[j].split(":");
					if (nameWeight.length != 2) throw raiseError("InputFileSyntaxError (" + (i+1) + "): Edge name or weight not given!", fr);
					destName = nameWeight[0];
					try {
						float f = Float.parseFloat(nameWeight[1]);
					} catch(Exception e) {
						throw raiseError("InputFileSyntaxError (" + (i+1) + "): \"" + nameWeight[1] + "\" cannot be turned to float!", fr);
					}
				} else {
					destName = segments[j];
				}
				if (!nodeNames.contains(destName)) throw raiseError("InputFileSyntaxError (" + (i+1) + "): \"" + destName + "\" named node does not exist!", fr);
			}
		}
		
		fr.close();
	}
	
	public Graph loadGraph(String file_path) throws Exception {
		try {
			checkSyntax(file_path, false);
		} catch (Exception e) { throw e; }
		
		Graph Gres = new Graph();
		
		FileReader fr = new FileReader(file_path);
		List<String> lines = fr.readAllLines();
		
		List<String> nodeNames = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String[] segments = lines.get(i).split(" ");
			String nodeName = segments[0].substring(0, segments[0].length() - 1);
			nodeNames.add(nodeName);
			Gres.addNode(nodeName);
		}
		
		for (int i = 0; i < lines.size(); i++) {
			String[] segments = lines.get(i).split(" ");
			
			for (int j = 1; j < segments.length; j++) {
				Node startNode = Gres.nodes.get(i);
				int destNodeID = nodeNames.indexOf(segments[j]);
				Node destNode = Gres.nodes.get(destNodeID);
				
				Gres.drawEdge("", startNode, destNode);
			}
		}
		
		return Gres;
	}
	
	public WeightedGraph loadWeighted(String file_path) throws Exception {
		try {
			checkSyntax(file_path, true);
		} catch (Exception e) { throw e; }
		
		WeightedGraph Gres = new WeightedGraph();
		
		FileReader fr = new FileReader(file_path);
		List<String> lines = fr.readAllLines();
		
		List<String> nodeNames = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String[] segments = lines.get(i).split(" ");
			String nodeName = segments[0].substring(0, segments[0].length() - 1);
			nodeNames.add(nodeName);
			Gres.addNode(nodeName);
		}
		
		for (int i = 0; i < lines.size(); i++) {
			String[] segments = lines.get(i).split(" ");
			
			for (int j = 1; j < segments.length; j++) {
				String[] nameWeight = segments[j].split(":");
				float weight = Float.parseFloat(nameWeight[1]);
				
				Node startNode = Gres.nodes.get(i);
				int destNodeID = nodeNames.indexOf(nameWeight[0]);
				Node destNode = Gres.nodes.get(destNodeID);
				
				Gres.drawEdge("", startNode, destNode, weight);
			}
		}
		
		fr.close();
		
		return Gres;
	}
	
	public void write(String file_path, Graph G) throws Exception {
		try {
			FileWriter fw = new FileWriter(file_path);
			
			String res = "";
			
			for (Node node : G.nodes) {
				res += node.name + ":";
				for (Edge edge : node.edges_out) {
					res += " " + edge.dest_node.name;
				}
				res += "\n";
			}
			
			fw.write(res);
			fw.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void write(String file_path, WeightedGraph G) throws Exception {
		try {
			FileWriter fw = new FileWriter(file_path);
			
			String res = "";
			
			for (Node node : G.nodes) {
				res += node.name + ":";
				for (Edge edge : node.edges_out) {
					res += " " + edge.dest_node.name + ":" + edge.weight;
				}
				res += "\n";
			}
			
			fw.write(res);
			fw.close();
		} catch (Exception e) {
			throw e;
		}
	}
}
