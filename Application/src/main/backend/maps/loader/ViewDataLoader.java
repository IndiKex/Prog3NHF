package main.backend.maps.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.backend.maps.data.BorderData;
import main.backend.maps.view.ViewData;

public class ViewDataLoader {
	public static ViewData loadViewData(File dataFolder) {
		List<BorderData> borderDatas = new ArrayList<>();
		
		File[] folders = dataFolder.listFiles();
		for (int i = 0; i < folders.length; i++) {
			File folder = folders[i];
			if (!folder.isDirectory()) {
				continue;
			}
			
			borderDatas.add(BorderData.load(new File(folder, "Hatar.geojson")));
		}
		
		return new ViewData(borderDatas);
	}
}
