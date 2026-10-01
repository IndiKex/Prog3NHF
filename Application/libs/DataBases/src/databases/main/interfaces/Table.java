package databases.main.interfaces;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public interface Table {
	public String getName();
	public int getSize();
	public void loadCSV(File sourceFile);
	public void loadCSV(File sourceFile, String keyField);
	public int getFieldSize();
	public List<String> getFields();
	public List<String> searchKeysByField(String field, String data);
	public String searchFieldByKey(String field, String keyData);
	public HashMap<String, String> searchRowByKey(String keyData);
}
