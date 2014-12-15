package elor.leon.android.argazki;

import android.content.Context;
import android.widget.ImageView;

public class ArgazkiRequest {

	private Context mContext;
	private String mUrl;
	private boolean mScale = false;
	private int mMaxDimension;
	private ImageView mImageView;
	private ArgazkiListener mCustomListener;
	
	ArgazkiRequest(Context context) {
		mContext = context.getApplicationContext();
	}

	Context getContext() {
		return mContext;
	}
	
	String getUrl() {
		return mUrl;
	}
	
	boolean shouldScale() {
		return mScale;
	}
	
	int getMaxDimension() {
		return mMaxDimension;
	}
	
	boolean hasImageView() {
		return mImageView != null;
	}
	
	ImageView getImageView() {
		return mImageView;
	}
	
	boolean hasListener() {
		return mCustomListener != null;
	}
	
	ArgazkiListener getListener() {
		return mCustomListener;
	}
	
	public ArgazkiRequest from(final String url) {
		mUrl = url;
		return this;
	}
	
	public ArgazkiRequest scaleTo(final int maxDimension) {
		mScale = true;
		mMaxDimension = maxDimension;
		return this;
	}
	
	/**
	 * With this method the fetched image will be set to the passed ImageView object automatically.
	 */
	public ArgazkiRequest to(final ImageView imageView) {
		mImageView = imageView;
		return this;
	}
	
	/**
	 * This method is called when the implementing object requires direct handling of the Bitmap
	 * once it is fetched.
	 */
	public ArgazkiRequest callback(final ArgazkiListener listener) {
		mCustomListener = listener;
		return this;
	}
	
	public void go() {
		ArgazkiManager
				.getInstance()
				.addRequest(this);
	}
}
