package main.backend.continuos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import main.backend.continuos.dbm.DataBaseManager;
import main.backend.continuos.dbm.Database;
import main.backend.continuos.dbm.Database.KeyExistsException;
import main.backend.continuos.dbm.Database.KeyNotExistsException;
import main.comm.RequestType;
import main.comm.asnwer.Answer;
import main.comm.asnwer.TicketListAnswer;
import main.comm.asnwer.TicketListAnswer.TicketData;
import main.comm.asnwer.UserAuthAnswer;
import main.comm.asnwer.UserChangePasswordAnswer;
import main.comm.asnwer.UserDataAnswer;
import main.comm.asnwer.UserDeleteAnswer;
import main.comm.asnwer.UserRegistAnswer;
import main.comm.request.Request;
import main.comm.request.TicketListRequest;
import main.comm.request.UserAuthRequest;
import main.comm.request.UserChangePasswordRequest;
import main.comm.request.UserDataRequest;
import main.comm.request.UserDeleteRequest;
import main.comm.request.UserRegistRequest;

public class RequestHandler {
	public Answer handleRequest(Request request, RequestType requestType) {
		switch (requestType) {
		case RequestType.USERREGIST: return handleRequest((UserRegistRequest)request);
		case RequestType.USERDATA: return handleRequest((UserDataRequest)request);
		case RequestType.USERAUTH: return handleRequest((UserAuthRequest) request);
		case RequestType.USERCHANGEPASSWORD: return handleRequest((UserChangePasswordRequest) request);
		case RequestType.USERDELETE: return handleRequest((UserDeleteRequest) request);
		case RequestType.TICKETLIST: return handleRequest((TicketListRequest) request);
		}
		return null;
	}
	
	public TicketListAnswer handleRequest(TicketListRequest request) {
		List<TicketData> data = List.of(
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 8, 30), LocalDateTime.of(2026, 11, 18, 10, 04), 1450),
			new TicketData("1", LocalDateTime.of(2026, 11, 18, 9, 00), LocalDateTime.of(2026, 11, 18, 11, 40), 1670)
		);
		return new TicketListAnswer(true, true, data);
	}
	
	public UserDeleteAnswer handleRequest(UserDeleteRequest request) {
		/*DataBaseManager dbm = DataBaseManager.get_instance();
		Database userIDBase = dbm.getDatabase("UserID");
		Database userAuthBase = dbm.getDatabase("UserAuth");
		
		List<Data> userIDRecord = userIDBase.readRecord(new Data(request.username));
		if (userIDRecord == null) {
			return new UserDeleteAnswer(true, false);
		}
		
		int userID = userIDRecord.get(1).getInt();
		
		userIDBase.writeRecord(new Data(request.username), List.of(new Data(request.username), new Data(userID), new Data(0)));
		
		return new UserDeleteAnswer(true, true);*/
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			userBase.deleteRecord(request.username);
			return new UserDeleteAnswer(true, true);
		} catch (KeyNotExistsException e) {
			return new UserDeleteAnswer(true, false);
		}
	}
	
	public UserChangePasswordAnswer handleRequest(UserChangePasswordRequest request) {
		/*DataBaseManager dbm = DataBaseManager.get_instance();
		Database userIDBase = dbm.getDatabase("UserID");
		Database userAuthBase = dbm.getDatabase("UserAuth");
		
		List<Data> userIDRecord = userIDBase.readRecord(new Data(request.username));
		if (userIDRecord == null || userIDRecord.get(2).getInt() == 0) {
			return new UserChangePasswordAnswer(true, false);
		}
		
		int userID = userIDRecord.get(1).getInt();
		
		List<Data> userAuthRecord = userAuthBase.readRecord(new Data(userID));
		if (userAuthRecord == null) {
			return new UserChangePasswordAnswer(true, false);
		}
		if (userAuthRecord.get(1).getString().equals(request.password)) {
			return new UserChangePasswordAnswer(true, false);
		}
		
		userAuthBase.writeRecord(new Data(userID), List.of(new Data(userID), new Data(request.password)));
		
		return new UserChangePasswordAnswer(true, true);*/
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> readRecord = userBase.getRecord(request.username);
			int passwordID = userBase.getFieldID("password");
			if (readRecord.get(passwordID).equals(request.password)) {
				return new UserChangePasswordAnswer(true, false);
			}
			
			List<String> userRecord = new ArrayList<>();
			userRecord.addAll(readRecord);
			userRecord.set(userBase.getFieldID("password"), request.password);
			
			userBase.changeRecord(request.username, userRecord);
			
			return new UserChangePasswordAnswer(true, true);
		} catch (KeyNotExistsException e) {
			return new UserChangePasswordAnswer(true, false);
		}
	}
	
	public UserAuthAnswer handleRequest(UserAuthRequest request) {
		/*DataBaseManager dbm = DataBaseManager.get_instance();
		Database userIDBase = dbm.getDatabase("UserID");
		Database userAuthBase = dbm.getDatabase("UserAuth");
		
		List<Data> userIDRecord = userIDBase.readRecord(new Data(request.username));
		if (userIDRecord == null || userIDRecord.get(2).getInt() == 0) {
			return new UserAuthAnswer(true, false, null);
		}
		
		int userID = userIDRecord.get(1).getInt();
		
		List<Data> userAuthRecord = userAuthBase.readRecord(new Data(userID));
		if (userAuthRecord == null) {
			return new UserAuthAnswer(true, false, null);
		}
		
		String readPassword = userAuthRecord.get(1).getString();
		if (!request.password.equals(readPassword)) {
			return new UserAuthAnswer(true, false, null);
		}
		
		return new UserAuthAnswer(true, true, request.username);*/
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> userRecord = userBase.getRecord(request.username);
			int passwordID = userBase.getFieldID("password");
			if (!userRecord.get(passwordID).equals(request.password)) {
				return new UserAuthAnswer(true, false, null);
			}
			
			return new UserAuthAnswer(true, true, request.username);
		} catch (KeyNotExistsException e) {
			return new UserAuthAnswer(true, false, null);
		}
	}
	
	public UserDataAnswer handleRequest(UserDataRequest request) {
		/*DataBaseManager dbm = DataBaseManager.get_instance();
		Database userIDBase = dbm.getDatabase("UserID");
		Database userDataBase = dbm.getDatabase("UserData");
		
		List<Data> userIDRecord = userIDBase.readRecord(new Data(request.username));
		if (userIDRecord == null || userIDRecord.get(2).getInt() == 0) {
			return new UserDataAnswer(true, false, null, null, null, null);
		}
		
		int userID = userIDRecord.get(1).getInt();
		
		List<Data> userDataRecord = userDataBase.readRecord(new Data(userID));
		if (userDataRecord == null) {
			return new UserDataAnswer(true, false, null, null, null, null);
		}
		
		return new UserDataAnswer(
				true,
				true,
				userDataRecord.get(1).toString(),
				userDataRecord.get(2).toString(),
				userDataRecord.get(3).toString(),
				userDataRecord.get(4).toString()
		);*/
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> userRecord = userBase.getRecord(request.username);
			return new UserDataAnswer(true, true, userRecord.get(0), userRecord.get(2), userRecord.get(3), userRecord.get(4));
			
		} catch (KeyNotExistsException e) {
			return new UserDataAnswer(true, false, null, null, null, null);
		}
	}
	
	public UserRegistAnswer handleRequest(UserRegistRequest request) {
		/*DataBaseManager dbm = DataBaseManager.get_instance();
		Database userIDBase = dbm.getDatabase("UserID");
		Database userDataBase = dbm.getDatabase("UserData");
		Database userAuthBase = dbm.getDatabase("UserAuth");
		
		List<Data> userIDRecord = userIDBase.readRecord(new Data(request.username));
		if (userIDRecord != null && userIDRecord.get(2).getInt() == 1) {
			return new UserRegistAnswer(false, true, "");
		}
		
		int userID = userIDBase.getNRecords();
		userIDBase.writeRecord(new Data(request.username), List.of(new Data(request.username), new Data(userID), new Data(1)));
		
		userDataBase.writeRecord(new Data(userID), List.of(
				new Data(userID),
				new Data(request.username),
				new Data(request.dateOfBirth),
				new Data(request.IDNumber),
				new Data(request.address)
		));
		
		userAuthBase.writeRecord(new Data(userID), List.of(new Data(userID), new Data(request.password)));
		
		return new UserRegistAnswer(true, true, request.username);*/
		
		DataBaseManager dbm = DataBaseManager.get_instance();
		Database userBase = dbm.getDatabase("user");
		
		try {
			List<String> record = List.of(request.username, request.password, request.dateOfBirth, request.IDNumber, request.address);
			userBase.addRecord(record);
			return new UserRegistAnswer(true, true, request.username);
		} catch (KeyExistsException e) {
			return new UserRegistAnswer(true, false, null);
		}
	}
}
