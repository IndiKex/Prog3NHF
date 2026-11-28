package main.backend.maps.view;

import java.util.List;

import main.backend.maps.data.BorderData;

public class ViewData {
	public List<BorderData> borderDatas;
	
	public ViewData(List<BorderData> borderDatas) {
		this.borderDatas = borderDatas;
	}

	public List<BorderData> getBorderDatas() {
		return borderDatas;
	}

	public void setBorderDatas(List<BorderData> borderDatas) {
		this.borderDatas = borderDatas;
	}
}
