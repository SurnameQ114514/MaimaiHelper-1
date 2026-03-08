package vip.cdms.maimaihelper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class SquareImageView extends ImageView {
    private boolean roundScreenEnabled = false;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRoundScreenEnabled(boolean enabled) {
        roundScreenEnabled = enabled;
        if (enabled) {
            setScaleType(ScaleType.FIT_CENTER);
        } else {
            setScaleType(ScaleType.FIT_XY);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (roundScreenEnabled) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            int size = Math.min(width, height);
            setMeasuredDimension(size, size);
        }
    }
}
