package elor.leon.android.argazki;

import android.content.Context;
import android.widget.ImageView;

public class ArgazkiRequest {

	private Context mContext;
	private String mUrl;
	private boolean mScale = false;
	private int mMaxDimension;
	private ImageView mImageView;
	
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
	
	ImageView getImageView() {
		return mImageView;
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
	
	public void to(final ImageView imageView) {
		mImageView = imageView;
		ArgazkiManager
				.getInstance()
				.addRequest(this);
	}
}
