package com.lwh.accounting.entity;

import java.io.Serializable;
import java.util.Map;

public class SerializableMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -669648250936173883L;
	private Map<String,Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
