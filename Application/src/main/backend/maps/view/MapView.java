package main.backend.maps.view;

import main.backend.maps.model.MapModel;

public class MapView {
	private MapModel mapModel;
	private ViewData viewData;
	
	public MapView(MapModel mapModel, ViewData viewData) {
		this.mapModel = mapModel;
		this.viewData = viewData;
	}
	
	public MapModel getMapModel() {
		return mapModel;
	}
	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}
	public ViewData getViewData() {
		return viewData;
	}
	public void setViewData(ViewData viewData) {
		this.viewData = viewData;
	}
}
