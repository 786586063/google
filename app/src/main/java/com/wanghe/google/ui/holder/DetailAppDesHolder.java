package com.wanghe.google.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.wanghe.google.R;
import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.utils.UIUtils;

/**
 * Created by 两三人 on 2016/11/25.
 */

public class DetailAppDesHolder extends BaseHolder<AppInfo> {

    private TextView tvDes;
    private TextView tvAuthor;
    private ImageView ivArrow;

    private LinearLayout.LayoutParams mParams;
    private boolean isOpen;
    @Override
    public View initView() {

        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);
        tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
        tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        view.findViewById(R.id.rl_detail_toggle).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggle();
                    }
                }
        );

        return view;
    }



    @Override
    public void refreshView(AppInfo data) {
        tvDes.setText(data.des);
        tvAuthor.setText(data.author);
        //获取阶段后的高度
        int shortHight = getShortHeight();
        mParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
        mParams.height = shortHight;
        tvDes.setLayoutParams(mParams);

    }

    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight  = getLongHeight();

        ValueAnimator animator = null;
        if (isOpen){
            isOpen = false;
            if (shortHeight<longHeight){
                animator = ValueAnimator.ofInt(shortHeight,longHeight);
            }
        }else{
            isOpen = true;
            if (shortHeight < longHeight){
                animator = ValueAnimator.ofInt(longHeight,shortHeight);
            }
        }
        if (animator != null){
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int height =(Integer)valueAnimator.getAnimatedValue();
                    mParams.height = height;
                    tvDes.setLayoutParams(mParams);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    if (isOpen) {
                        ivArrow.setImageResource(R.drawable.arrow_up);
                    } else {
                        ivArrow.setImageResource(R.drawable.arrow_down);
                    }
                    //页面滑动到底端
                    final ScrollView scrollView = getScrollView();
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
        animator.setDuration(200);
        animator.start();

    }

    /**
     * 模拟获取截断后7段的高度
     * @return
     */
    private int getShortHeight(){
        int measureWidth = tvDes.getMeasuredWidth();
        //结合模式和具体值，确定宽高
        int widthMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measureWidth, View.MeasureSpec.EXACTLY);
        int heightMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        TextView view = new TextView(UIUtils.getContext());
        view.setMaxLines(7);
        view.setText(getData().des);
        view.measure(widthMakeMeasureSpec,heightMakeMeasureSpec);

        return view.getMeasuredHeight();
    }
    /**
     * 模拟获取完整的高度
     * @return
     */
    private int getLongHeight(){
        int measureWidth = tvDes.getMeasuredWidth();
        //结合模式和具体值，确定宽高
        int widthMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measureWidth, View.MeasureSpec.EXACTLY);
        int heightMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        TextView view = new TextView(UIUtils.getContext());
//        view.setMaxLines(7);
        view.setText(getData().des);
        view.measure(widthMakeMeasureSpec,heightMakeMeasureSpec);

        return view.getMeasuredHeight();
    }
    private ScrollView getScrollView(){
        View parent = (View) tvDes.getParent();
        while (!(parent instanceof ScrollView)){
            parent = (View) parent.getParent();
        }
        return (ScrollView) parent;
    }
}
