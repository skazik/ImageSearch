package com.yahoo.example.gridimagesearch;

import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ImageViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);
		// if not Serializable 
		// String url = getIntent().getStringExtra("url");
		// SmartImageView img = (SmartImageView) findViewById(R.id.sivImage);
		// img.setImageUrl(url);
		ImageResults image = (ImageResults) getIntent().getSerializableExtra("result");
		SmartImageView img = (SmartImageView) findViewById(R.id.sivImage);
		img.setImageUrl(image.getFullUrl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_view, menu);
		return true;
	}

}
