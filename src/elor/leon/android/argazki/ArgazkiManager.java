package elor.leon.android.argazki;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

class ArgazkiManager implements ArgazkiListener {

	private static ArgazkiManager mInstance;
	private LruCache<String, Bitmap> mCache;
	
	private List<ArgazkiRequest> mRequests;
	private boolean mRunning = false;
	
	public static ArgazkiManager getInstance() {
		if (mInstance == null) {
			mInstance = new ArgazkiManager();
		}
		
		return mInstance;
	}
	
	private ArgazkiManager() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;
		mCache = new LruCache<String, Bitmap>(cacheSize);
		mRequests = new ArrayList<ArgazkiRequest>();
	}
	
	public void addRequest(final ArgazkiRequest request) {
		mRequests.add(request);
		init();
	}
	
	private void init() {
		if (mRunning || mRequests == null || mRequests.isEmpty()) {
			return;
		}

		mRunning = true;
		
		ArgazkiRequest request = mRequests.get(0);
		
		boolean cached = setBitmapIfCached(request);
		
		Log.i("aaa", "cache?" + cached + "(" + request.getUrl() + ")");
		
		if (cached) {
			mRequests.remove(request);
			mRunning = false;
			init();
		} else {
			new ArgazkiDownloader(request, this).execute();
		}
	}
	
	private boolean setBitmapIfCached(final ArgazkiRequest request) {
		
		/*
		 * retrieve cached bitmap
		 * returns false if non existent
		 * returns false if existent but different size (cached bitmap is removed)
		 * returns true otherwise after setting bitmap into imageview (cached and same max dimension)
		 */
		
		Bitmap cachedBitmap = mCache.get(request.getUrl());
		
		if (cachedBitmap == null) {
			return false;
		} else {
			boolean differentMaxDimension =
					Math.max(cachedBitmap.getWidth(), cachedBitmap.getHeight()) != request.getMaxDimension();
			if (differentMaxDimension) {
				mCache.remove(request.getUrl());
				return false;
			} else {
				request.getImageView().setImageBitmap(cachedBitmap);
				return true;
			}
		}
	}

	@Override
	public void onDownload(boolean ok, ArgazkiRequest request, Bitmap bitmap) {
		if (ok) {
			Log.i("aaa", "size=" + bitmap.getByteCount() / 1024 + "kb (" + request.getUrl() + ")");
			mCache.put(request.getUrl(), bitmap);
			boolean cached = mCache.get(request.getUrl()) != null;
			Log.i("aaa", "cached just now (" + cached + ")");
			mRequests.remove(0);
			request.getImageView().setImageBitmap(bitmap);
		}
		
		mRunning = false;
		init();
	}
}
