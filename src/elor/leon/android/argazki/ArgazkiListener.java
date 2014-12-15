package elor.leon.android.argazki;

import android.graphics.Bitmap;

public interface ArgazkiListener {
	void onDownload(final boolean ok, final ArgazkiRequest request, final Bitmap bitmap);
}
