package frusso.radartest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by jfabrix101 on 16/07/14.
 */
public class RadarView extends View {

    private final String LOG = "RadarView";
    private final int POINT_ARRAY_SIZE = 25;

    private int fps = 100;
    private boolean showCircles = true;

    float alpha = 0;
    Point latestPoint[] = new Point[POINT_ARRAY_SIZE];
    Paint latestPaint[] = new Paint[POINT_ARRAY_SIZE];

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Paint localPaint = new Paint();
        localPaint.setColor(Color.GREEN);
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setStrokeWidth(1.0F);
        localPaint.setAlpha(0);

        int alpha_step = 255 / POINT_ARRAY_SIZE;
        for (int i=0; i < latestPaint.length; i++) {
            latestPaint[i] = new Paint(localPaint);
            latestPaint[i].setAlpha(255 - (i* alpha_step));
        }
    }


    android.os.Handler mHandler = new android.os.Handler();
    Runnable mTick = new Runnable() {
        @Override
        public void run() {
            invalidate();
            mHandler.postDelayed(this, 1000 / fps);
        }
    };


    public void startAnimation() {
        mHandler.removeCallbacks(mTick);
        mHandler.post(mTick);
    }

    public void stopAnimation() {
        mHandler.removeCallbacks(mTick);
    }

    public void setFrameRate(int fps) { this.fps = fps; }
    public int getFrameRate() { return this.fps; };

    public void setShowCircles(boolean showCircles) { this.showCircles = showCircles; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int width = getWidth();
        int height = getHeight();

        int r = Math.min(width, height);


        //canvas.drawRect(0, 0, getWidth(), getHeight(), localPaint);

        int i = r / 2;
        int j = i - 1;
        Paint localPaint = latestPaint[0]; // GREEN

        if (showCircles) {
            canvas.drawCircle(i, i, j, localPaint);
            canvas.drawCircle(i, i, j, localPaint);
            canvas.drawCircle(i, i, j * 3 / 4, localPaint);
            canvas.drawCircle(i, i, j >> 1, localPaint);
            canvas.drawCircle(i, i, j >> 2, localPaint);
        }

        alpha -= 0.5;
        if (alpha < -360) alpha = 0;
        double angle = Math.toRadians(alpha);
        int offsetX =  (int) (i + (float)(i * Math.cos(angle)));
        int offsetY = (int) (i - (float)(i * Math.sin(angle)));

        latestPoint[0]= new Point(offsetX, offsetY);

        for (int x=POINT_ARRAY_SIZE-1; x > 0; x--) {
            latestPoint[x] = latestPoint[x-1];
        }



        int lines = 0;
        for (int x = 0; x < POINT_ARRAY_SIZE; x++) {
            Point point = latestPoint[x];
            if (point != null) {
                canvas.drawLine(i, i, point.x, point.y, latestPaint[x]);
            }
        }


        lines = 0;
        for (Point p : latestPoint) if (p != null) lines++;

        boolean debug = false;
        if (debug) {
            StringBuilder sb = new StringBuilder(" >> ");
            for (Point p : latestPoint) {
                if (p != null) sb.append(" (" + p.x + "x" + p.y + ")");
            }

            Log.d(LOG, sb.toString());
            //  " - R:" + r + ", i=" + i +
            //  " - Size: " + width + "x" + height +
            //  " - Angle: " + angle +
            //  " - Offset: " + offsetX + "," + offsetY);
        }

    }

}
