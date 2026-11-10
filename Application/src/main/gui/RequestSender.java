package main.gui;

import main.backend.Backend;
import main.comm.RequestType;
import main.comm.asnwer.Answer;
import main.comm.request.Request;

public class RequestSender {
	public static Backend backend;
	
	public static Answer sendRequest(Request request, RequestType requestType) {
		return backend.postRequest(request, requestType);
	}
}
