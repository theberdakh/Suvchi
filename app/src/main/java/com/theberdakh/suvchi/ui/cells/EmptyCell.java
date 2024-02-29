package com.theberdakh.suvchi.ui.cells;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class EmptyCell extends FrameLayout {
    private int cellHeight;
    public EmptyCell(@NonNull Context context) {
        this(context, 8);
    }

    public EmptyCell(@NonNull Context context, int height) {
        super(context);
        cellHeight = height;
    }

    public void setHeight(int height) {
        if (cellHeight != height){
            cellHeight = height;
            requestLayout();
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(cellHeight, MeasureSpec.EXACTLY));
    }
}
