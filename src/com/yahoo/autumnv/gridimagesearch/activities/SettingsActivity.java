package com.yahoo.autumnv.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.autumnv.gridimagesearch.R;
import com.yahoo.autumnv.gridimagesearch.models.Settings;

public class SettingsActivity extends Activity {
	private static final String NONE_SELECTED = "None Selected";
	private static final String SETTINGS2 = "settings";
	private Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//Pull out arguments from the intent, if any
		settings = (Settings)getIntent().getSerializableExtra(SETTINGS2);
		
		setSelectedEditText(settings.siteFilter, R.id.et_site_filter);
//		Toast.makeText(this, "this is the settings page " + settings.value, Toast.LENGTH_SHORT).show();
	}

	private void setSelectedEditText(String setting, int id) {
		if (setting != null && setting.length() > 0) {
			EditText etSetting = (EditText)findViewById(id);
			etSetting.setText(setting);
		}
	}
	
	public void onSubmit(View v) {
//		Toast.makeText(this, "clicked the settings submit button!", Toast.LENGTH_SHORT).show();
		//Serialize form data
		
		settings.siteFilter = getEditTextData(R.id.et_site_filter);
		settings.typeFilter = getSpinnerData(R.id.sp_type_filter);
		settings.imageSize = getSpinnerData(R.id.sp_image_size);
		settings.colorFilter = getSpinnerData(R.id.sp_color_filter);
		
		//Create result
		Intent i = new Intent();
		i.putExtra(SETTINGS2, settings);
		//Submit result to parent activity
		setResult(RESULT_OK, i);
		finish();
	}
	
	private String getEditTextData(int id) {
		EditText editText = (EditText)findViewById(id);
		String etData = editText.getText().toString();
		if (etData == null || NONE_SELECTED.equals(etData)) {
			return "";
		}
		return etData;
	}
	
	private String getSpinnerData(int id) {
		Spinner spinner = (Spinner)findViewById(id);
		String spinnerData = spinner.getSelectedItem().toString();
		if (spinnerData == null || NONE_SELECTED.equals(spinnerData)) {
			return "";
		}
		return spinnerData;
	}

}
