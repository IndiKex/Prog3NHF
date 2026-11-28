package main.comm.asnwer;

import java.awt.Point;
import java.util.List;

public class MapStopDataAnswer extends MapPointsAnswer {
	public List<String> names;
	
	public MapStopDataAnswer(boolean recieved, boolean successful, List<Point> points, List<String> names) {
		super(recieved, successful, List.of(points));
		this.names = names;
	}
}
