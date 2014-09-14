package com.yahoo.autumnv.gridimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yahoo.autumnv.gridimagesearch.R;
import com.yahoo.autumnv.gridimagesearch.models.ImageResult;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult>{

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, android.R.layout.simple_list_item_1, images);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
		}
		ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
//		TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
		
		//clear out the image from last time
		ivImage.setImageResource(0);
		
//		//populate title and remote download image url
//		tvTitle.setText(Html.fromHtml(imageInfo.title));
		
		//remotely download the image data in the background (with Picasso)
		Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
		
		//return the completed view to be displayed
		return convertView;
	}

}
