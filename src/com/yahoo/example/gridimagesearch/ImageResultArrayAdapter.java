package com.yahoo.example.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResults> {

	public ImageResultArrayAdapter(Context context, List<ImageResults> images) {
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResults imageInfo = this.getItem(position);
		SmartImageView sivImage;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			sivImage = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent, false);
		} else {
			sivImage = (SmartImageView) convertView;
			sivImage.setImageResource(android.R.color.transparent);
		}
		sivImage.setImageUrl(imageInfo.getThumbUrl());
		return sivImage;
	}

	
}
