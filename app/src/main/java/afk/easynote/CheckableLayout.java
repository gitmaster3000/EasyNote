package afk.easynote;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by The Abster on 11/25/2015.
 */
 class CheckableTextView extends TextView implements Checkable {
        private boolean mChecked;
private int colorC= getResources().getColor(R.color.Checkedblue);
    private int colorD= getResources().getColor(R.color.blue);
        public CheckableTextView(Context context) {
            super(context);
        }
    public CheckableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
        @SuppressWarnings("deprecation")
        public void setChecked(boolean checked) {
            mChecked = checked;
            if(mChecked)
                setBackgroundColor(colorC);

            else if(!mChecked)
            {

                setBackgroundColor(colorD);
            }
        }


        public boolean isChecked() {
            return mChecked;
        }

        public void toggle() {
            setChecked(!mChecked);
        }

    }

