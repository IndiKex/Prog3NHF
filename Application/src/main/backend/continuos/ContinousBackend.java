package main.backend.continuos;

import java.io.File;

import main.backend.Backend;
import main.backend.continuos.dbm.DataBaseManager;
import main.backend.maps.loader.MapLoader;
import main.backend.maps.loader.ViewDataLoader;
import main.backend.maps.model.MapModel;
import main.backend.maps.view.ViewData;
import main.comm.asnwer.Answer;
import main.comm.request.Request;

public class ContinousBackend implements Backend {
	private static ContinousBackend _instance;
	
	private RequestHandler requestHandler = new RequestHandler();
	private DataBaseManager dbm = DataBaseManager.get_instance();
	private MapModel mapModel;
	private ViewData viewData;
	
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
		viewData = ViewDataLoader.loadViewData(new File("assets/MagyarorszagKozigazgatasiAdatok/OrszagMegyeAdatok"));
	}
	
	public void close() {
		dbm.close();
	}
	
	public Answer postRequest(Request request) {
		return request.accept(requestHandler);
	}

	public ViewData getViewData() {
		return viewData;
	}

	public void setViewData(ViewData viewData) {
		this.viewData = viewData;
	}
}
