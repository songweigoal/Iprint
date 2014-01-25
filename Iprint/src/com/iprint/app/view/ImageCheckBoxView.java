package com.iprint.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.iprint.app.R;
import com.iprint.app.bean.PhotoEntity;
import com.iprint.app.common.PrintHelper;

public class ImageCheckBoxView extends ImageView {
	Boolean mChecked = false;
	private PhotoEntity photo;
	private Paint mSelectionPaint = null;
	boolean mShowCropBox = false;
	protected String originalID;
	public String uriEncodedPath;
	Boolean showHighlight = false;
	Boolean mWifiChecked = false;
	Bitmap selectedCheckbox = null;
	Bitmap untag = null;
	float scale = 1.0f;
	int pixels = 0;

	public ImageCheckBoxView(Context context) {
		super(context);
		mSelectionPaint = new Paint();
		mSelectionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mSelectionPaint.setXfermode(new PorterDuffXfermode(
				PorterDuff.Mode.SRC_ATOP));
		mSelectionPaint.setARGB(255, 255, 0, 0);

		this.setMinimumHeight(96);
		this.setAdjustViewBounds(true);
		selectedCheckbox = PrintHelper.readBitMap(context,
				R.drawable.selectedcheckbox);
	}

	public void setChecked(Boolean checked) {
		mChecked = checked;
		invalidate();
	}

	public Boolean getChecked() {
		return mChecked;
	}

	public PhotoEntity getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoEntity photo) {
		this.photo = photo;
	}

	//
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		if (photo.isChecked()) {
			if (selectedCheckbox == null)
				Log.e("ImageCheckBoxView", "selectedCheckbox");
			try {
				canvas.drawBitmap(selectedCheckbox, 0, 0, this.mSelectionPaint);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
