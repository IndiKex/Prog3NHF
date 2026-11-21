package main.comm.request;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;

public class StopNameRequest implements Request {
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
