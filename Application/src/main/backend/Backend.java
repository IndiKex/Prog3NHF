package main.backend;

import main.comm.asnwer.Answer;
import main.comm.request.Request;

public interface Backend {
	public void init();
	public void close();
	
	public Answer request(Request r);
}
