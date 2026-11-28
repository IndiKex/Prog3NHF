package main.comm.request;

import java.awt.Rectangle;

public abstract class MapPointsRequest implements Request {
	public Rectangle boundingBox;
	
	public MapPointsRequest(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
}
