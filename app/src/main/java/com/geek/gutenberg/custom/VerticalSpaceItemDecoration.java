package com.geek.gutenberg.custom;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private static final int VERTICAL_ITEM_SPACE = 24; //Spacing between Genre card
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration() {
        this.verticalSpaceHeight = VERTICAL_ITEM_SPACE;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}
