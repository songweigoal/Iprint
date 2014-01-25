package com.iprint.app.common;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

public class MemoryLruCache {

	// å¼€è¾Ÿ8Mç¡¬ç¼“å­˜ç©ºé—´
	private final int hardCachedSize = 8 * 1024 * 1024;
	// hard cache
	private final LruCache<String, Bitmap> sHardBitmapCache = new LruCache<String, Bitmap>(
			hardCachedSize) {
		@Override
		public int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight();
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			Log.v("tag", "hard cache is full , push to soft cache");
			// ç¡¬å¼•ç”¨ç¼“å­˜åŒºæ»¡ï¼Œå°†ä¸€ä¸ªæœ€ä¸�ç»�å¸¸ä½¿ç”¨çš„oldvalueæŽ¨å…¥åˆ°è½¯å¼•ç”¨ç¼“å­˜åŒº
			sSoftBitmapCache.put(key, new SoftReference<Bitmap>(oldValue));
		}
	};

	// è½¯å¼•ç”¨
	private static final int SOFT_CACHE_CAPACITY = 40;
	@SuppressWarnings("serial")
	private final static LinkedHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
			SOFT_CACHE_CAPACITY, 0.75f, true) {
		@Override
		public SoftReference<Bitmap> put(String key, SoftReference<Bitmap> value) {
			return super.put(key, value);
		}

	};

	// ç¼“å­˜bitmap
	public boolean putBitmap(String key, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(key, bitmap);
			}
			return true;
		}
		return false;
	}

	// ä»Žç¼“å­˜ä¸­èŽ·å�–bitmap
	public Bitmap getBitmap(String key) {
		synchronized (sHardBitmapCache) {
			final Bitmap bitmap = sHardBitmapCache.get(key);
			if (bitmap != null)
				return bitmap;
		}
		// ç¡¬å¼•ç”¨ç¼“å­˜åŒºé—´ä¸­è¯»å�–å¤±è´¥ï¼Œä»Žè½¯å¼•ç”¨ç¼“å­˜åŒºé—´è¯»å�–
		synchronized (sSoftBitmapCache) {
			SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(key);
			if (bitmapReference != null) {
				final Bitmap bitmap2 = bitmapReference.get();
				if (bitmap2 != null)
					return bitmap2;
				else {
					Log.v("tag", "soft reference å·²ç»�è¢«å›žæ”¶");
					sSoftBitmapCache.remove(key);
				}
			}
		}
		return null;
	}
}
