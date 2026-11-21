package main.gui;

import main.backend.Backend;
import main.comm.asnwer.Answer;
import main.comm.request.Request;

public class RequestSender {
	public static Backend backend;
	public static String username;
	
	public static Answer sendRequest(Request request) {
		return backend.postRequest(request);
	}
}
