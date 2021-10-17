package com.wkh.picturesharingapplication.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 该方法用于绘制内容区以外
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildLayoutPosition(view);
        int padding = DisplayUtils.dip2px(BaseApplication.sApplication, 8);
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.set(padding, padding, padding, padding);
        } else {
            outRect.set(padding, padding, padding, 0);
        }
    }
}
