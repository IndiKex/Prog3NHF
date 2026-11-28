package main.comm.asnwer;

import java.awt.Point;
import java.util.List;

public abstract class MapPointsAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	public List<List<Point>> points;
	
	public MapPointsAnswer(boolean recieved, boolean successful, List<List<Point>> points) {
		this.recieved = recieved;
		this.successful = successful;
		this.points = points;
	}
}
