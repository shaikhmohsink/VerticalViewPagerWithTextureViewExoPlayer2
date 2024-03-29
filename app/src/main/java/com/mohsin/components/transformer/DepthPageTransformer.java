package com.mohsin.components.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer {
	//private static float MIN_SCALE = 0.75f;
	private static float MIN_SCALE = 1f;

	public void transformPage(View view, float position) {
		int pageWidth = view.getWidth();

		/*if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			view.setAlpha(0);
		} else if (position <= 0) { // [-1,0]
			// Use the default slide transition when moving to the left page
			view.setAlpha(1);
			view.setTranslationX(0);
			view.setScaleX(1);
			view.setScaleY(1);
		} else if (position <= 1) { // (0,1]
			// Fade the page out.
			view.setAlpha(1 - position);
			// Counteract the default slide transition
			view.setTranslationX(pageWidth * -position);
			// Scale the page down (between MIN_SCALE and 1)
			float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
					* (1 - Math.abs(position));
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
		} else { // (1,+Infinity]
			// This page is way off-screen to the right.
			view.setAlpha(0);
		}*/






		/*if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			ViewHelper.setAlpha(view,0);
		} else if (position <= 0) { // [-1,0]
			// Use the default slide transition when moving to the left page
			ViewHelper.setAlpha(view,1);
			ViewHelper.setTranslationX(view,0);
			ViewHelper.setScaleX(view,1);
			ViewHelper.setScaleY(view,1);
		} else if (position <= 1) { // (0,1]
			// Fade the page out.
			ViewHelper.setAlpha(view,1 - position);
			// Counteract the default slide transition
			ViewHelper.setTranslationX(view,pageWidth * -position);
			// Scale the page down (between MIN_SCALE and 1)
			float scaleFactor = MIN_SCALE
					+ (1 - MIN_SCALE) * (1 - Math.abs(position));
			ViewHelper.setScaleX(view,scaleFactor);
			ViewHelper.setScaleY(view,scaleFactor);
		} else { // (1,+Infinity]
			// This page is way off-screen to the right.
			ViewHelper.setAlpha(view,0);
		}*/









		if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			view.setAlpha(0);

		}  else if (position <= 0) { // [-1,0]
			// Use the default slide transition when moving to the left page
			view.setAlpha(1);
			// Counteract the default slide transition
			view.setTranslationX(view.getWidth() * -position);

			//set Y position to swipe in from top
			float yPosition = position * view.getHeight();
			view.setTranslationY(yPosition);
			view.setScaleX(1);
			view.setScaleY(1);

		} else if (position <= 1) { // [0,1]
			view.setAlpha(1);

			// Counteract the default slide transition
			view.setTranslationX(view.getWidth() * -position);


			// Scale the page down (between MIN_SCALE and 1)
			float scaleFactor = MIN_SCALE
					+ (1 - MIN_SCALE) * (1 - Math.abs(position));
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);

		} else { // (1,+Infinity]
			// This page is way off-screen to the right.
			view.setAlpha(0);
		}
	}
}