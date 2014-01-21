package com.yahoo.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ImageResultArrayAdapter imageAdapter;
	ArrayList <ImageResults> imgResults = new ArrayList<ImageResults>();
	int startImage = 0;
	SearchOptions sOptions;
	private final int REQUEST_OPTIONS = 20;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SetupViews();
        sOptions = new SearchOptions();
        imageAdapter = new ImageResultArrayAdapter(this, imgResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
            	Intent i = new Intent(getApplicationContext(), ImageViewActivity.class);
            	ImageResults image = imgResults.get(position);
            	// if serialized
            	i.putExtra("result", image);
            	// else - just string
            	// i.putExtra("url", image.getFullUrl());
            	startActivity(i);
        	}
		});
    }
  
    public void onOptions(MenuItem mi) {
    	Intent i = new Intent(this, SearchOptionsActivity.class);
    	i.putExtra("request", sOptions);
    	startActivityForResult(i, REQUEST_OPTIONS);
     }
    
 // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above
      if (resultCode == RESULT_OK && requestCode == REQUEST_OPTIONS) {
         // Extract name value from result extras
         sOptions = (SearchOptions) data.getExtras().getSerializable("result");
         TextView tvFilter = (TextView) findViewById(R.id.tvFilter);
         String filter = "";
 		if (sOptions.imageSize.length()>0)
			filter += sOptions.imageSize.toString() + " ";
		if (sOptions.imageColor.length()>0)
			filter += "| " + sOptions.imageColor.toString() + " ";
		if (sOptions.imageType.length()>0)
			filter += "| " + sOptions.imageType.toString() + " ";
         
         tvFilter.setText(filter);
         getSearch(0);
      }
    } 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    
    private void SetupViews() {
		etQuery = (EditText) findViewById(R.id.etSearch);
		gvResults = (GridView) findViewById(R.id.gvImageGrid);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
    
    private void imageSearch(String query) {
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
		String request = new String("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=" + startImage);
		if (sOptions.imageSize.length()>0)
			request += "&imgsz=" + sOptions.imageSize.toString();
		if (sOptions.imageColor.length()>0)
			request += "&imgcolor=" + sOptions.imageColor.toString();
		if (sOptions.imageType.length()>0)
			request += "&imgtype=" + sOptions.imageType.toString();
		// could not find it
//		if (sOptions.imageSite.length()>0)
//			request += "&????=" + sOptions.imageSite.toString();

		request += "&v=1.0&q=" + Uri.encode(query);
		client.get(request, new JsonHttpResponseHandler() {
			
		    @Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imgResults.clear();
					imageAdapter.addAll(ImageResults.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imgResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
    private void getSearch(int delta) {
    	String query = etQuery.getText().toString();
    	if (query.length() > 0) {
    		startImage = delta != 0 ? startImage+delta : 0;
    		if (startImage < 0)
    			startImage = 0;
    		imageSearch(query);
    	}
    	else {
    		Toast.makeText(this, "Please enter the search term", Toast.LENGTH_SHORT).show();
    	}
    }
    public void OnClickImageSeach(View v) {
    	getSearch(0);
	}
    
    public void OnClickLoadMore(View v) {
    	getSearch(8);
	}
    
    public void OnClickLoadLess(View v) {
    	getSearch(-8);
	}
}
