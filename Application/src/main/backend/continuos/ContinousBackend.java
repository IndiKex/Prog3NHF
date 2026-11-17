package main.backend.continuos;

import main.backend.Backend;
import main.backend.continuos.dbm.DataBaseManager;
import main.comm.RequestType;
import main.comm.asnwer.Answer;
import main.comm.request.Request;

public class ContinousBackend implements Backend {
	private RequestHandler requestHandler = new RequestHandler();
	private DataBaseManager dbm = DataBaseManager.get_instance();
	
	public void init() {
		dbm.init();
	}
	
	public void close() {
		dbm.close();
	}
	
	public Answer postRequest(Request request, RequestType requestType) {
		return requestHandler.handleRequest(request, requestType);
	}
}
