package elor.leon.android.argazki;

import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

class ArgazkiDownloader extends AsyncTask<Void, Void, Bitmap> {

	private ArgazkiRequest mRequest;
	private ArgazkiListener mListener;
	
	public ArgazkiDownloader(ArgazkiRequest request, final ArgazkiListener l) {
		mRequest = request;
		mListener = l;
	}
	
	@Override
	protected Bitmap doInBackground(Void... params) {
		if (mRequest == null || mListener == null) {
			return null;
		}

		Bitmap bitmap = null;
		
		HttpURLConnection connection = null;
		
		try {
			URL url = new URL(mRequest.getUrl());
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				bitmap = BitmapFactory.decodeStream(connection.getInputStream());
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		if (mRequest.shouldScale()) {
			Bitmap resized = ArgazkiUtils.resizeBitmap(bitmap, mRequest.getMaxDimension(), true);
			bitmap.recycle();
			return resized;
		}
		
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		
		if (mListener != null) {
			mListener.onDownload(result != null, mRequest, result);
		}
	}
}
