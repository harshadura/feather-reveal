package com.example.test3;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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
	int preConeL =0;
	int preConeR=0;
	
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
	 */
	// public FeatherRevealView(Context context, HeartViewListener listener) {
	// super(context);
	// init(context);
	// }

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
		leftImage.setY(height - 1300);
		rightImage.setY(height - 1300);

		animateR();
	}

	private static float getDegreesFromRadians(float angle) {
		return (float)(angle * 180.0 / Math.PI);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (!isInitialized) {
			int w = getWidth();
			int h = getHeight();
			position.set(w / 2, h / 2);
			isInitialized = true;
		}

		Paint paint = new Paint();

		 transform.reset();
		 transform.postTranslate(-width / 2.0f, -height / 2.0f);
		 transform.postRotate(getDegreesFromRadians(angle));
		 transform.postScale(scale, scale);
		 transform.postTranslate(position.getX(), position.getY());
//		 canvas.drawBitmap(bitmap, transform, paint);

		try {
			paint.setColor(0xFF007F00);
			canvas.drawCircle(vca.getX(), vca.getY(), 64, paint);
//			paint.setColor(0xFF7F0000);
//			canvas.drawCircle(vcb.getX(), vcb.getY(), 64, paint);

//			paint.setColor(0xFFFF0000);
//			canvas.drawLine(vpa.getX(), vpa.getY(), vpb.getX(), vpb.getY(), paint);
//			paint.setColor(0xFF00FF00);
//			canvas.drawLine(vca.getX(), vca.getY(), vcb.getX(), vcb.getY(), paint);
		} catch (Exception e) {
			e.printStackTrace();
			// Just being lazy here...
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		vca = null;
		vcb = null;
		vpa = null;
		vpb = null;

		try {
			touchManager.update(event);

			if (touchManager.getPressCount() == 1) {
				vca = touchManager.getPoint(0);
				vpa = touchManager.getPreviousPoint(0);
				position.add(touchManager.moveDelta(0));
			} else {
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
		
		if(diff < 1){

			diff = diff*10;
			Log.v("TAF", "******* > " + diff);
			
			//		AnimatorSet set = new AnimatorSet();
//		set.playTogether(
//				ObjectAnimator.ofFloat(leftImage, "rotation", 0, -90), 
//				ObjectAnimator.ofFloat(rightImage, "rotation", 0, 90),
//				ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)
//		);
//		set.start();
		}
		if(1<=diff && diff < 2){
			AnimatorSet set = new AnimatorSet();
			set.playTogether(
					ObjectAnimator.ofFloat(leftImage, "rotation", preConeL, -70), 
					ObjectAnimator.ofFloat(rightImage, "rotation", preConeR, 70)
					
//					ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)
			);
			set.start();
			preConeL = -70; preConeR=70;
			}
		if(2<=diff && diff < 3){
			AnimatorSet set = new AnimatorSet();
			set.playTogether(
					ObjectAnimator.ofFloat(leftImage, "rotation", preConeL, -50), 
					ObjectAnimator.ofFloat(rightImage, "rotation", preConeR, 50)
//					ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)
			);
			set.start();
			preConeL = -50; preConeR=50;	
		}
		if(3<=diff && diff < 4){
			AnimatorSet set = new AnimatorSet();
			set.playTogether(
					ObjectAnimator.ofFloat(leftImage, "rotation", preConeL, -30), 
					ObjectAnimator.ofFloat(rightImage, "rotation", preConeR, 30)
//					ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)
			);
			set.start();
			preConeL = -30; preConeR=30;	
		}
		if(4<=diff && diff < 5){
			AnimatorSet set = new AnimatorSet();
			set.playTogether(
					ObjectAnimator.ofFloat(leftImage, "rotation", preConeL, -10), 
					ObjectAnimator.ofFloat(rightImage, "rotation", preConeR, 10)
//					ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)
			);
			set.start();
			preConeL = -10; preConeR=10;
		}
		
		if( diff >= 5){
			AnimatorSet set = new AnimatorSet();
			set.playTogether(
					ObjectAnimator.ofFloat(leftImage, "rotation", preConeL, 0), 
					ObjectAnimator.ofFloat(rightImage, "rotation", preConeR, 0)
//					ObjectAnimator.ofFloat(fadeImage, "alpha", 1, 0.25f, 0)
			);
			set.start();
			preConeL = 0; preConeR=0;
		}
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

}
