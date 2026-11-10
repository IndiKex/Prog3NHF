package main.backend.continuos;

import java.util.List;

import main.comm.RequestType;
import main.comm.asnwer.Answer;
import main.comm.asnwer.UserRegistAnswer;
import main.comm.request.Request;
import main.comm.request.UserRegistRequest;
import main.dbm.Data;
import main.dbm.DataBaseManager;
import main.dbm.Database;

public class RequestHandler {
	public Answer handleRequest(Request request, RequestType requestType) {
		switch (requestType) {
		case RequestType.USERREGIST: return handleRequest((UserRegistRequest)request);
		}
		return null;
	}
	
	public UserRegistAnswer handleRequest(UserRegistRequest request) {
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userIDBase = dbm.getDatabase("UserID");
		Database userDataBase = dbm.getDatabase("UserData");
		
		List<Data> userIDRecord = userIDBase.readRecord(new Data(request.username));
		if (userIDRecord != null) {
			return new UserRegistAnswer(false, true);
		}
		
		int userID = userIDBase.getNRecords();
		userIDBase.writeRecord(new Data(userID), List.of(new Data(request.username), new Data(userID)));
		
		userDataBase.writeRecord(new Data(userID), List.of(
				new Data(userID),
				new Data(request.username),
				new Data(request.dateOfBirth),
				new Data(request.IDNumber),
				new Data(request.address)
		));
		
		return new UserRegistAnswer(true, true);
	}
}
