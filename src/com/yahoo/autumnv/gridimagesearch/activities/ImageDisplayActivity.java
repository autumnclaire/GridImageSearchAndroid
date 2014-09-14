package com.yahoo.autumnv.gridimagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yahoo.autumnv.gridimagesearch.R;
import com.yahoo.autumnv.gridimagesearch.models.ImageResult;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		//remove the actionbar on the image display activity
		getActionBar().hide();
		//pull out the url from the intent
		ImageResult result = (ImageResult)getIntent().getSerializableExtra("result");
		//find the image view
		ImageView ivImageResult = (ImageView)findViewById(R.id.ivImageResult);
		//load the image url into the image view using Picasso
		Picasso.with(this).load(result.fullUrl).into(ivImageResult);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
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
}
