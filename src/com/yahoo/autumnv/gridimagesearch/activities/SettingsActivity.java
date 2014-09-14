package com.yahoo.autumnv.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.yahoo.autumnv.gridimagesearch.R;
import com.yahoo.autumnv.gridimagesearch.models.Settings;

public class SettingsActivity extends Activity {
	private static final String NONE_SELECTED = "None Selected";
	private Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//Pull out arguments from the intent, if any
		settings = (Settings)getIntent().getSerializableExtra(Settings.SETTINGS);
		
		setSelectedEditText(settings.siteFilter, R.id.et_site_filter);
		setSelectedSpinnerValue(settings.colorFilter, R.id.sp_color_filter);
		setSelectedSpinnerValue(settings.imageSize, R.id.sp_image_size);
		setSelectedSpinnerValue(settings.typeFilter, R.id.sp_type_filter);
	}

	private void setSelectedSpinnerValue(String setting, int id) {
		if (setting != null && setting.length() > 0) {
			Spinner spinner = (Spinner)findViewById(id);
			setSpinnerToValue(spinner, setting);
		}
	}

	private void setSelectedEditText(String setting, int id) {
		if (setting != null && setting.length() > 0) {
			EditText etSetting = (EditText)findViewById(id);
			etSetting.setText(setting);
		}
	}
	
	public void setSpinnerToValue(Spinner spinner, String value) {
	    int index = 0;
	    SpinnerAdapter adapter = spinner.getAdapter();
	    for (int i = 0; i < adapter.getCount(); i++) {
	        if (adapter.getItem(i).equals(value)) {
	            index = i;
	            break; // terminate loop
	        }
	    }
	    spinner.setSelection(index);
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
		i.putExtra(Settings.SETTINGS, settings);
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
