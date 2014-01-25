package com.iprint.app.common;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.provider.MediaStore;

public  class PrintHelper {
	public static boolean isScrolling = false;
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	public static Bitmap loadThumbnailImage(String uriEncodedString, int kind, Options options, Context context)
	{
		int originalImageId = Integer.parseInt(uriEncodedString.substring(uriEncodedString.lastIndexOf("/") + 1, uriEncodedString.length()));
		Bitmap mBitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), originalImageId, kind, options);
		
		return mBitmap;
	}
	
	public static Bitmap readBitMap(Context context, int resId){     
		   BitmapFactory.Options opt = new BitmapFactory.Options();     
		   opt.inPreferredConfig = Bitmap.Config.ALPHA_8;      
		   opt.inPurgeable = true;     
		   opt.inInputShareable = true;   
		   //opt.inSampleSize = 2;  
		   InputStream is = context.getResources().openRawResource(resId);     
		   return BitmapFactory.decodeStream(is,null,opt);     
		}   
}
