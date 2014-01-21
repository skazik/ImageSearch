package com.yahoo.example.gridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResults implements Serializable {
	private static final long serialVersionUID = 2110635092433680741L;
	private String fullUrl;
	private String thumbUrl;

	public ImageResults(JSONObject json)
	{
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
		} catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
		}
	}
	
	public String getFullUrl() {
		return fullUrl;
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}

	public static ArrayList<ImageResults> fromJSONArray(JSONArray array) {
		ArrayList<ImageResults> results = new ArrayList<ImageResults>();
		for (int i = 0; i < array.length(); i++) {
			try {
				results.add(new ImageResults(array.getJSONObject(i)));
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
	
	@Override
	public String toString() {
		return thumbUrl.toString();
	}
	
	
}
