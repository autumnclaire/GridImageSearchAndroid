package com.yahoo.autumnv.gridimagesearch.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{

	private static final long serialVersionUID = 4346781548931842186L;
	public String fullUrl;
	public String thumbUrl;
	public String title;
	
	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
			this.title = json.getString("title");
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	
	//ImageResult.fromJSONArray([...,...])
	public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i < array.length(); i++) {
			try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
	
}
