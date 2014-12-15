#android-argazki
===============

Argazki is a basque word meaning Photo/Photograph

Android project that was started to handle remote images via URLs.
The idea is simple enough: While there are robust solutions such as (Picasso by Square)[http://square.github.io/picasso/], it was intended to do the following with the images:
* Fetch
* Cache via LruCache
* Resize if request
* Return cached image if URL matches with equal or better quality
* Queue requests for several requests being made at a time
* Fetch and cache image with better quality if requested one was already cached with lower dimensions due to the request
* Coded it so the methods can be chained in order to simplify the process
* Add at some point support for several retries on a faulty and/or limited network
* Improve at some point the usage of the right data structures such as the interface Queue to implement the request queue the appropriate way

My repos are pretty idle due to lack of time BUT with lollipop out there, and Android Studio bumped up to 1.X+, I'm aiming to test several interesting things with the support libraries (AppCompat, Palette, CardView, and RecyclerView)

##Usage

This project has yet to be imported to Android Studio. Once imported to Eclipse, include into other projects as a library project. Then proceed making use of it as the following:

```
Argazki
		.at (context)
		.from(url)
		.scaleTo(200)	//max dimension--keeping aspect
		.to(mImageView)
		.go();
```

Above is the basic way to use it. If you need just the iamge instead of applying the fetched Bitmap object automatically into an ImageView, then do the following:

```
ArgazkiListener listener = new ArgazkiListener {
	@Override
	public void onDownload(final boolean ok, final ArgazkiRequest request, final Bitmap bitmap) {
		if (ok) {
			handleBitmap(bitmap);
		} else {
			//notify error
		}
	}
}

Argazki
		.at(context)
		.from(url)
		.callback(listener)
		.go();
```

####Notes:
All requests are queued and handled sequentially. Parallel handle of the requests should be added at some point in the future since this was developed to handle small jobs in projects.