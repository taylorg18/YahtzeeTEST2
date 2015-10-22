package edu.up.cs301.animation;

import android.graphics.Color;
import android.view.SurfaceView;
import android.view.View;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.ImageButton;

/**
 * Created by Grayson on 10/22/2015.
 */

public class dice extends ImageButton{


        // instance variables
       private Paint backgroundPaint = new Paint(); // painter for painting background
        private int flashCount; // counts down ticks for background-flash
        private Paint flashPaint; // has color for background flash
        public boolean keep;
        int dieNum;


        /**
         * Constructor for the AnimationSurface class. In order to be useful, an
         * object must be supplied that implements the Animator interface. This
         * can either be done by overriding the 'createAnimator' method (which by
         * default give null, or by invoking the setAnimator method.
         *
         * @param context
         *            - a reference to the activity this animation is run under
         */
        public dice(Context context) {
            super(context);
            init();


        }// ctor

        /**
         * An alternate constructor for use when a subclass is directly specified
         * in the layout. It is expected that the subclass will have overridden
         * the 'createAnimator' method.
         *
         * @param context
         *            - a reference to the activity this animation is run under
         * @param attrs
         *            - set of attributes passed from system
         */
        public dice(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();

        }// ctor

        /**
         * Helper-method for the constructors
         */
        private void init() {

            // Tell the OS that *yes* I will draw stuff
            setWillNotDraw(false);
            keep = false;



        }


        public void onDraw(Canvas g)
        {
            Paint black = new Paint();
            black.setColor(Color.BLACK);
            dieNum = (int)(Math.random()*6 + 1);
            switch (dieNum)
            {
                case 1: g.drawCircle(g.getHeight()/2,g.getWidth()/2,20, black);
                    break;
                case 2: g.drawCircle(40,40,20, black);
                        g.drawCircle(g.getHeight()-40,g.getWidth()-40,20,black);
                    break;
                case 3: g.drawCircle(g.getHeight()/2,g.getWidth()/2,20, black);
                        g.drawCircle(40, 40, 20, black);
                        g.drawCircle(g.getHeight()-40,g.getWidth()-40,20,black);
                    break;
                case 5: g.drawCircle(g.getHeight()/2,g.getWidth()/2,20, black);
                case 4: g.drawCircle(40,40,20, black);
                        g.drawCircle(g.getHeight()-40,g.getWidth()-40,20,black);
                        g.drawCircle(40,getWidth()-40,20, black);
                        g.drawCircle(g.getHeight()-40,40,20,black);
                    break;
                case 6: g.drawCircle(40,40,20, black);
                        g.drawCircle(g.getHeight()-40,g.getWidth()-40,20,black);
                        g.drawCircle(40,getWidth()-40,20, black);
                        g.drawCircle(g.getHeight()-40,40,20,black);
                        g.drawCircle(getWidth()-40,getHeight()/2,20, black);
                        g.drawCircle(40,getHeight()/2,20,black);

                    break;
            }


        }


    }


