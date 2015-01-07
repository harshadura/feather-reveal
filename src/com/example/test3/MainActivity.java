package com.example.test3;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	ImageView fadeImage, frame, leftImage, rightImage;
	int i = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
		FeatherRevealView featherRevealView = new FeatherRevealView(this);
		setContentView(featherRevealView);
		
				
//		leftImage = (ImageView) this.findViewById(R.id.leftImage);
//	    rightImage = (ImageView) this.findViewById(R.id.rightImage);
//		
//		fadeImage = (ImageView) this.findViewById(R.id.fadeImg);
//		fadeImage.setOnClickListener(this);
//		
//		frame = (ImageView) this.findViewById(R.id.frame);
//		frame.setOnClickListener(new OnClickListener() {
//
//		    @Override
//		    public void onClick(View v) {
//		        // TODO Auto-generated method stub
//		        i++;
//		        Handler handler = new Handler();
//		        Runnable r = new Runnable() {
//
//		            @Override
//		            public void run() {
//		                i = 0;
//		            }
//		        };
//
//		        if (i == 1) {
//		            //Single click
//		            handler.postDelayed(r, 250);
//		            animateR();
//		        } else if (i == 3) {
//		            //Double click
//		            i = 0;
//		       restart();
//		        }
//
//
//		    }
//		});
//		
		
		
//				int amountOffscreen = (int)(leftImage.getWidth() * 0.5); /* or whatever */
//				boolean offscreen = true; /* true or false */
//				int xOffset = (offscreen) ? amountOffscreen : 0;
//				RelativeLayout.LayoutParams rlParams = 
//				    (RelativeLayout.LayoutParams)leftImage.getLayoutParams();
//				rlParams.setMargins(-1*xOffset, 0, xOffset, 0);
//				leftImage.setLayoutParams(rlParams);
				
//				Display display = getWindowManager().getDefaultDisplay();
//				Point size = new Point();
//				display.getSize(size);
//				int width = size.x;
//				int height = size.y;
//
//				
////		leftImage.setX(width);
//		leftImage.setY(height-1300);
//		rightImage.setY(height-1300);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.fadeImg:
			animateR();
			break;
//		case R.id.frame:
//			restart();
//			break;
		default:
			break;
		}
	}

	public void animateR() {
		// AnimatorProxy.wrap(leftImage).setPivotX(100);
		// AnimatorProxy.wrap(leftImage).setPivotY(100);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		leftImage.setPivotX(width / 2);
		leftImage.setPivotY(height-100);

		rightImage.setPivotX(width / 2);
		rightImage.setPivotY(height-100);

		AnimatorSet set = new AnimatorSet();
		set.playTogether(ObjectAnimator.ofFloat(leftImage, "rotation", 0, -90), ObjectAnimator.ofFloat(rightImage, "rotation", 0, 90),
				ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)

		);
		set.setDuration(5 * 1000).start();

		// AnimatorSet set = new AnimatorSet();
		// set.playTogether(
		// // ObjectAnimator.ofFloat(leftImage, "rotationY", 0, 360),
		// // ObjectAnimator.ofFloat(leftImage, "rotationX", 0, 180)
		// ObjectAnimator.ofFloat(leftImage, "rotation", 0, -90)
		// // ObjectAnimator.ofFloat(leftImage, "translationX", 0, 360),
		// // ObjectAnimator.ofFloat(leftImage, "translationY", 0, 90)
		// // ObjectAnimator.ofFloat(leftImage, "scaleX", 1, 1.5f),
		// // ObjectAnimator.ofFloat(leftImage, "scaleY", 1, 0.5f),
		// // ObjectAnimator.ofFloat(leftImage, "alpha", 1, 0.25f, 1)
		// );
		// set.setDuration(5 * 1000).start();
		//

	}

	public void restart() {
		Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
