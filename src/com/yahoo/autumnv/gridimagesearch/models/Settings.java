package com.yahoo.autumnv.gridimagesearch.models;

import java.io.Serializable;

public class Settings implements Serializable {
	public static final String SETTINGS = "settings";

	/**
	 * 
	 */
	private static final long serialVersionUID = -7485235293164060533L;
	public String siteFilter = "";
	public String imageSize = "";
	public String typeFilter = "";
	public String colorFilter = "";
}
