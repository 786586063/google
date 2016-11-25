package com.wanghe.google.ui.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanghe.google.R;
import com.wanghe.google.domain.AppInfo;
import com.wanghe.google.http.OkhttpManger;
import com.wanghe.google.utils.UIUtils;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 两三人 on 2016/11/23.
 */

public class DetailAppSafeHolder extends BaseHolder<AppInfo> {

    private ImageView[] ivSafes;// 安全标识的控件数组
    private LinearLayout[] llDes;// 安全描述控件数组
    private ImageView[] ivDes;// 安全描述图片控件数组
    private TextView[] tvDes;// 安全描述文字控件数组
    private LinearLayout llDesRoot;// 安全描述根布局
    private int mDesRootHeight;// 安全描述整体布局高度
    private LinearLayout.LayoutParams mParams;// 安全描述整体控件布局参数

    private boolean isExpanded = false;// 标记当前安全描述打开还是关闭的状态
    private ImageView ivArrow;// 安全标识小箭头
    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(),
                R.layout.layout_detail_safeinfo, null);

        ivSafes = new ImageView[4];
        ivSafes[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        ivSafes[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        ivSafes[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        ivSafes[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        llDes = new LinearLayout[4];
        llDes[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        llDes[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        llDes[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        llDes[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        ivDes = new ImageView[4];
        ivDes[0] = (ImageView) view.findViewById(R.id.iv_des1);
        ivDes[1] = (ImageView) view.findViewById(R.id.iv_des2);
        ivDes[2] = (ImageView) view.findViewById(R.id.iv_des3);
        ivDes[3] = (ImageView) view.findViewById(R.id.iv_des4);

        tvDes = new TextView[4];
        tvDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        tvDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        tvDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        tvDes[3] = (TextView) view.findViewById(R.id.tv_des4);

        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);
        //设置安全描述整体布局为0 ，达到隐藏效果
        mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
        mParams.height = 0;
        llDesRoot.setLayoutParams(mParams);

        //设置安全标识整体控件的点击事件
        view.findViewById(R.id.rl_safe_root).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //展开或者收起安全描述信息
                        toggle();
                    }
                }
        );
        return view;
    }



    @Override
    public void refreshView(AppInfo data) {

        if (data != null) {
            ArrayList<AppInfo.SafeInfo> safe = data.safe;
            if (safe != null) {
                for (int i = 0; i < 4; i++) {
                    if (i < safe.size()) {
                        AppInfo.SafeInfo safeInfo = safe.get(i);

                        ivSafes[i].setVisibility(View.VISIBLE);
                        llDes[i].setVisibility(View.VISIBLE);

                        tvDes[i].setText(safeInfo.safeDes);
                        Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+safeInfo.safeUrl).into(ivSafes[i]);
                        Picasso.with(UIUtils.getContext()).load(OkhttpManger.url+"image?name="+safeInfo.safeDesUrl).into(ivDes[i]);

                    } else {
                        ivSafes[i].setVisibility(View.GONE);
                        llDes[i].setVisibility(View.GONE);
                    }
                }
            }
            //计算安全控件布局整体的高度
            llDesRoot.measure(0,0);
            //跟新完成后最后的高度
            mDesRootHeight = llDesRoot.getMeasuredHeight();


        }
    }

    /**
     * 展开或收起安全描述信息
     */
    private void toggle() {
        final ValueAnimator animator;
        if (isExpanded){
            //收起描述信息
            isExpanded = false;
            // 初始化按指定值变化的动画器, 布局高度从mDesRootHeight变化到0,此方法调用,并开启动画之后,
            // 会将最新的高度值不断回调在onAnimationUpdate方法中,在onAnimationUpdate中更新布局高度
            animator = ValueAnimator.ofInt(mDesRootHeight,0);

        }else{
            //展开描述信息
            isExpanded = true;
            animator = ValueAnimator.ofInt(0,mDesRootHeight);
        }
        //设置动画的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //动画开始后，每次动画有了最新的状态都会回掉此方法
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height = (Integer) animation.getAnimatedValue();
                mParams.height = height;
                //跟新安全信息的高度
                llDesRoot.setLayoutParams(mParams);
            }
        });
        //设置动画监听
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束
                //跟新安全标识的小箭头的方向
                if (isExpanded){
                    ivArrow.setImageResource(R.drawable.arrow_up);
                }else{
                    ivArrow.setImageResource(R.drawable.arrow_down);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        //设置动画时间
        animator.setDuration(200);
        //开启动画
        animator.start();
    }
}
