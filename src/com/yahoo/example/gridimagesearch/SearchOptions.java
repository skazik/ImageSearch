package com.yahoo.example.gridimagesearch;

import java.io.Serializable;

public class SearchOptions implements Serializable {
	private static final long serialVersionUID = 8254667858748156550L;
	public String imageSize;
	public String imageColor;
	public String imageType;
	public String imageSite;
	public SearchOptions() {
		imageSize = "";
		imageColor = "";
		imageType = "";
		imageSite = "";
	}
}
