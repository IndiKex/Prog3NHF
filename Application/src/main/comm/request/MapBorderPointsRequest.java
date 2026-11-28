package main.comm.request;

import java.awt.Rectangle;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;

public class MapBorderPointsRequest extends MapPointsRequest {

	public MapBorderPointsRequest(Rectangle boundingBox) {
		super(boundingBox);
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
	
}
