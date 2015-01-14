package com.example.test3;

import java.text.DecimalFormat;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FeatherRevealView extends RelativeLayout implements OnTouchListener {
	ImageView fadeImage, frame, leftImage, rightImage;
	float diffrence;
	int preConeL =0;
	int preConeR=0;
	float preAlpha = 1;
	
	private int width;
	private int height;
	
	private Context mContext;
	private Matrix transform = new Matrix();

	private Vector2D position = new Vector2D();
	private float scale = 1;
	private float angle = 0;

	private TouchManager touchManager = new TouchManager(2);
	private boolean isInitialized = false;

	// Debug helpers to draw lines between the two touch points
	private Vector2D vca = null;
	private Vector2D vcb = null;
	private Vector2D vpa = null;
	private Vector2D vpb = null;

	public FeatherRevealView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public FeatherRevealView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public FeatherRevealView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		 mContext = context;
		 setWillNotDraw(false); 
		 setOnTouchListener(this);
		View inflater = LayoutInflater.from(context).inflate(R.layout.activity_main, this, true);
		
		leftImage = (ImageView) inflater.findViewById(R.id.leftImage);
	    rightImage = (ImageView) inflater.findViewById(R.id.rightImage);
		fadeImage = (ImageView) inflater.findViewById(R.id.fadeImg);
		frame = (ImageView) inflater.findViewById(R.id.frame);
		
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		this.width = width;
		this.height = height;
		
		// leftImage.setX(width);
		leftImage.setX(-100);
		leftImage.setY(height - 1200);
		
		rightImage.setX(200);
		rightImage.setY(height -1200);

//		animateR();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		vca = null;
		vcb = null;
		vpa = null;
		vpb = null;

		try {
			touchManager.update(event);

			Log.v("TAG", String.valueOf("Touches >>>>>>" + touchManager.getPressCount()));
			
	
				if (touchManager.getPressCount() == 2) {
					vca = touchManager.getPoint(0);
					vpa = touchManager.getPreviousPoint(0);
					vcb = touchManager.getPoint(1);
					vpb = touchManager.getPreviousPoint(1);

					Vector2D current = touchManager.getVector(0, 1);
					Vector2D previous = touchManager.getPreviousVector(0, 1);
					float currentDistance = current.getLength();
					float previousDistance = previous.getLength();

					if (previousDistance != 0) {
						scale *= currentDistance / previousDistance;
					}

					angle -= Vector2D.getSignedAngleBetween(current, previous);
				}
			

//			invalidate();
//			animateR();
			
			revealFeather(scale);
			
		} catch (Throwable t) {
			// So lazy...
		}
		return true;
	}

	public void revealFeather(float diff) {
		// AnimatorProxy.wrap(leftImage).setPivotX(100);
		// AnimatorProxy.wrap(leftImage).setPivotY(100);

		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		leftImage.setPivotX(width / 2);
		leftImage.setPivotY(height - 100);

		rightImage.setPivotX(width / 2);
		rightImage.setPivotY(height - 100);
		Log.v("TAF", "******* > " + diff);
		
		    
//		boolean flag = animateOnRange(diff);
//		if (flag == true)
			moveOnSpecifix(diff);
		
		
	}
	
	private boolean animateOnRange(float diff){
		if(1<=diff && diff < 3){
			animateFeatherParas(0, 1);
			return true;
		}
		if(3<=diff && diff < 5){
			animateFeatherParas(100, 0);
			return true;
		}
		return false;
	}
	
	private void moveOnSpecifix(float diff){
		diffrence = diff;
//	int distanceBetweenFingers = (int) Math.round(diff);
		
	double rounded = Math.round(diff*100.0)/100.0;
	
	if (rounded == 1){
		changeFeatherParas(0, 1);
	}else if (rounded == 1.5){
		changeFeatherParas(9, 0.9f);
	}else if (rounded == 2){
		changeFeatherParas(18, 0.8f);
	}else if (rounded == 2.5){
		changeFeatherParas(27, 0.7f);
	}else if (rounded == 3){
		changeFeatherParas(36, 0.6f);
	}else if (rounded == 3.5){
		changeFeatherParas(45, 0.5f);
	}else if (rounded == 4){
		changeFeatherParas(54, 0.4f);
	}else if (rounded == 4.5){
		changeFeatherParas(63, 0.3f);
	}else if (rounded == 5){
		changeFeatherParas(72, 0.2f);
	}else if (rounded == 5.5){
		changeFeatherParas(81, 0.1f);
	}else if (rounded == 6){
		changeFeatherParas(100, 0);
	}
	
	
//		switch (rounded) {
//		
//		case 1: changeFeatherParas(0, 1);
//		break;
//		case 2: changeFeatherParas(9, 0.9f);
//		break;
//		case 3: changeFeatherParas(18, 0.8f);
//		break;
//		case 4: changeFeatherParas(27, 0.7f);
//		break;
//		case 5: changeFeatherParas(36, 0.6f);
//		break;
//		case 6: changeFeatherParas(45, 0.5f);
//		break;
//		case 2: changeFeatherParas(54, 0.4f);
//		break;
//		case 3: changeFeatherParas(63, 0.3f);
//		break;
//		case 4: changeFeatherParas(72, 0.2f);
//		break;
//		case 5: changeFeatherParas(81, 0.1f);
//		break;
//		case 6: changeFeatherParas(100, 0);
//		break;
//		
//		default:
//			break;
//		}
	}
	private void changeFeatherParas(int angle, float alpha){
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
				ObjectAnimator.ofFloat(leftImage, "rotation", preConeL, angle*-1), 
				ObjectAnimator.ofFloat(rightImage, "rotation", preConeR, angle),
				ObjectAnimator.ofFloat(fadeImage, "alpha", preAlpha, alpha), 
				ObjectAnimator.ofFloat(frame, "alpha", preAlpha, alpha)
		);
		set.start();
		preConeL = angle*-1; 
		preConeR = angle; 
		preAlpha = alpha;
		
		 animateOnRange(diffrence);
	}

	private void animateFeatherParas(int angle, float alpha){
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
				ObjectAnimator.ofFloat(leftImage, "rotation", preConeL, angle*-1), 
				ObjectAnimator.ofFloat(rightImage, "rotation", preConeR, angle),
				ObjectAnimator.ofFloat(fadeImage, "alpha", preAlpha, alpha), 
				ObjectAnimator.ofFloat(frame, "alpha", preAlpha, alpha)
		);
		set.setDuration(5 * 1000).start();
		preConeL = angle*-1; 
		preConeR = angle; 
		preAlpha = alpha;
	}
	
	public void animateR() {
		// AnimatorProxy.wrap(leftImage).setPivotX(100);
		// AnimatorProxy.wrap(leftImage).setPivotY(100);

		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		leftImage.setPivotX(width / 2);
		leftImage.setPivotY(height - 100);

		rightImage.setPivotX(width / 2);
		rightImage.setPivotY(height - 100);

		AnimatorSet set = new AnimatorSet();
		set.playTogether(
				ObjectAnimator.ofFloat(leftImage, "rotation", 0, -90), 
				ObjectAnimator.ofFloat(rightImage, "rotation", 0, 90),
				ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)
		);
		set.setDuration(5 * 1000).start();

	}

}
