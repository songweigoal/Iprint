package com.iprint.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.iprint.app.bean.PhotoEntity;
import com.iprint.app.common.ImageWorkerTN;
import com.iprint.app.common.PrintHelper;
import com.iprint.app.view.ImageCheckBoxView;

public class ImageSelectionAdapter extends BaseAdapter {
	private Context contex;
	private ArrayList<PhotoEntity> photoes = new ArrayList<PhotoEntity>();
	private String albumName;
	private ImageWorkerTN mImageWorker;

	public ImageSelectionAdapter() {

	}

	public ImageSelectionAdapter(Context contex, ArrayList<PhotoEntity> photos,
			String albumName) {
		this.contex = contex;
		this.photoes = photos;
		this.albumName = albumName;
		mImageWorker = new ImageWorkerTN(contex);
	}

	@Override
	public int getCount() {
		return photoes.size();
		// TODO Auto-generated method stub

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ImageCheckBoxView imgView = null;
		PhotoEntity photo = photoes.get(position);
		if (convertView == null) {
			imgView = new ImageCheckBoxView(contex);
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					AbsListView.LayoutParams.MATCH_PARENT, PrintHelper.screenHeight /4);
			imgView.setLayoutParams(param);
			imgView.setBackgroundColor(Color.BLACK);
			convertView = imgView;
			
		} else {
			imgView = (ImageCheckBoxView) convertView;
		}
		try {
			mImageWorker.loadImage(photo.getPhotoURI(), imgView);			
			imgView.setPhoto(photo);
		} catch (OutOfMemoryError oome) {
			oome.printStackTrace();
		}
		convertView = imgView;
		return convertView;

	}

}
