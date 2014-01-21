package com.yahoo.example.gridimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SearchOptionsActivity extends Activity {
	private SearchOptions sOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		sOptions = (SearchOptions) getIntent().getSerializableExtra("request");
		EditText etSize = (EditText) findViewById(R.id.etSz);
		EditText etColor = (EditText) findViewById(R.id.etColor);
		EditText etType = (EditText) findViewById(R.id.etType);
		EditText etSite = (EditText) findViewById(R.id.etSite);
		
		etSize.setText(sOptions.imageSize);
		etColor.setText(sOptions.imageColor);
		etType.setText(sOptions.imageType);
		etSite.setText(sOptions.imageSite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}

	public void OnClickApply(View v) {
		EditText etSize = (EditText) findViewById(R.id.etSz);
		EditText etColor = (EditText) findViewById(R.id.etColor);
		EditText etType = (EditText) findViewById(R.id.etType);
		EditText etSite = (EditText) findViewById(R.id.etSite);
		
		sOptions.imageSize = etSize.getText().toString();
		sOptions.imageColor = etColor.getText().toString();
		sOptions.imageType = etType.getText().toString();
		sOptions.imageSite = etSite.getText().toString();
		  
		Intent data = new Intent();
		data.putExtra("result", sOptions);
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent		
	}
}
