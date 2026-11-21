package main.backend.continuos;

import java.io.File;

import main.backend.Backend;
import main.backend.continuos.dbm.DataBaseManager;
import main.backend.maps.loader.MapLoader;
import main.backend.maps.model.MapModel;
import main.comm.asnwer.Answer;
import main.comm.request.Request;

public class ContinousBackend implements Backend {
	private static ContinousBackend _instance;
	
	private RequestHandler requestHandler = new RequestHandler();
	private DataBaseManager dbm = DataBaseManager.get_instance();
	private MapModel mapModel;
	
	public static ContinousBackend get_instance() {
		if (_instance == null)
			_instance = new ContinousBackend();
		return _instance;
	}
	
	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	private ContinousBackend() {
		
	}
	
	public void init() {
		dbm.init();
		mapModel = MapLoader.loadMap(new File("assets/MAVData/gtfsMavMenetrend"));
	}
	
	public void close() {
		dbm.close();
	}
	
	public Answer postRequest(Request request) {
		return request.accept(requestHandler);
	}
}
