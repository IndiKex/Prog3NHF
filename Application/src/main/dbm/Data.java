package main.dbm;

import java.io.Serializable;

public class Data implements Serializable {
	public enum DataTypes {
		INT,
		FLOAT,
		STRING
	}
	
	private int iData;
	private float fData;
	private String sData;
	private DataTypes dataType;
	
	public Data(DataTypes dataType, String data) {
		switch (dataType) {
		case DataTypes.INT -> iData = Integer.parseInt(data);
		case DataTypes.FLOAT -> fData = Float.parseFloat(data);
		case DataTypes.STRING -> sData = data;
		}
		this.dataType = dataType;
	}
	
	public Data(int iData) {
		this.iData = iData;
		this.dataType = DataTypes.INT;
	}
	
	public Data(String sData) {
		this.sData = sData;
		this.dataType = DataTypes.STRING;
	}
	
	public Data(float fData) {
		this.fData = fData;
		this.dataType = DataTypes.FLOAT;
	}
	
	public int getInt() {
		return iData;
	}
	
	public float getFloat() {
		return fData;
	}
	
	public String getString() {
		return sData;
	}
	
	public DataTypes getDataType() {
		return dataType;
	}
	
	public String toString() {
		switch (dataType) {
		case DataTypes.INT: return Integer.toString(iData);
		case DataTypes.FLOAT: return Float.toString(fData);
		case DataTypes.STRING: return sData;
		}
		return "";
	}
	
	public boolean equals(Data d) {
		if (this.dataType != d.dataType) return false;
		if (this.iData != d.iData) return false;
		if (this.fData != d.fData) return false;
		if (!this.sData.equals(d.sData)) return false;
		return true;
	}
}
