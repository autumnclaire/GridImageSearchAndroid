package com.yahoo.autumnv.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.autumnv.gridimagesearch.R;
import com.yahoo.autumnv.gridimagesearch.adapters.ImageResultsAdapter;
import com.yahoo.autumnv.gridimagesearch.models.EndlessScrollListener;
import com.yahoo.autumnv.gridimagesearch.models.ImageResult;
import com.yahoo.autumnv.gridimagesearch.models.Settings;

public class SearchActivity extends Activity {
	private static final String RESULT = "result";
	private static final int REQUEST_CODE = 0;
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private Settings settings;
	
	private void setupViews() {
		etQuery = (EditText)findViewById(R.id.etSearchQuery);
		gvResults = (GridView)findViewById(R.id.gvSearchResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Launch the image display activity
				//create an intent
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				//Get the image result to display
				ImageResult result = imageResults.get(position);
				//pass the image result into the intent
				i.putExtra(RESULT, result);
				//launch the new activity
				startActivity(i);
			}
		});
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		setupImageResults();
		setupSettings();

	}


	private void setupImageResults() {
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
		setupCustomScrollListener();
	}


	private void setupCustomScrollListener() {
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				customLoadMoreDataFromApi(page);
			}
		});
	}

	private void customLoadMoreDataFromApi(int page) {
		loadData(page, false);
		
	}
	
	//fired whenever the search button is pressed
	public void onImageSearch(View v) {
		loadData(0, true);
	}


	private void loadData(int page, final boolean clearResults) {
		AsyncHttpClient client = new AsyncHttpClient();
		String searchUrl = buildSearchUrl(page);
		client.get(searchUrl, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					if (clearResults) {
						imageResults.clear();	//clear the existing images from the array
					}
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setupSettings() {
		this.settings = (Settings) this.getIntent().getSerializableExtra(Settings.SETTINGS);
		if (this.settings == null) {
			this.settings = new Settings();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
    public void onSettingsClicked(MenuItem menuItem) {
//    	Toast.makeText(this, "Settings icon clicked", Toast.LENGTH_SHORT).show();
    	//Create Intent
    	Intent i = new Intent(this, SettingsActivity.class);
		//pass any arguments
    	i.putExtra(Settings.SETTINGS, settings);
    	//execute Intent startActivityForResult
    	startActivityForResult(i, REQUEST_CODE);
    }
	



	private String buildSearchUrl(int page) {
		
		StringBuilder searchUrl = new StringBuilder("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8");
		searchUrl.append(buildQueryParameter());
		searchUrl.append(buildImageSizeParameter());
		searchUrl.append(buildImageTypeParameter());
		searchUrl.append(buildSiteFilterParameter());
		searchUrl.append(buildColorFilterParameter());
		searchUrl.append(buildPageParameter(page));
		String searchString = searchUrl.toString();
		Toast.makeText(this, searchString, Toast.LENGTH_SHORT).show();
		return searchString;
	}
	
	private String buildPageParameter(int page) {
		return "&start=" + page;
	}


	private String buildColorFilterParameter() {
		return "&imgcolor=" + settings.colorFilter;
	}


	private String buildSiteFilterParameter() {
		return "&as_sitesearch=" + settings.siteFilter;
	}

	private String buildImageTypeParameter() {
		return "&imgtype=" + settings.typeFilter;
	}

	private String buildImageSizeParameter() {
		return "&imgsz=" + settings.imageSize;
	}


	private String buildQueryParameter() {
		String query = etQuery.getText().toString();
		return "&q=" + query;
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQUEST_CODE) {
    		if (resultCode == RESULT_OK) {
    			Settings settings = (Settings)data.getSerializableExtra(Settings.SETTINGS);
    			this.settings = settings;
    		}
    	}
    }
}
