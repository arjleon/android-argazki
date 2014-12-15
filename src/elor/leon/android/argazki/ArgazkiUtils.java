package elor.leon.android.argazki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

class ArgazkiUtils {

	static Bitmap resizeBitmap(Bitmap bitmap, int maximumDimension, boolean scaleUpIfSmaller) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float newScale;
		
		if (Math.max(width, height) <= maximumDimension && !scaleUpIfSmaller) {
			return bitmap;
		}
		
		if (width > height) {
			newScale = (float)maximumDimension / (float)width;
		} else {
			newScale = (float)maximumDimension / (float)height;
		}
		
		Matrix matrix = new Matrix();
		matrix.postScale(newScale, newScale);
		
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}
	
	static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}
