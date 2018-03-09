package com.meiji.toutiao;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.widget.imagebrowser.DismissFrameLayout;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Meiji on 2018/3/9.
 * TODO 显示图片下标，长按下载图片
 */

public class ImageBrowserActivity extends BaseActivity {

    private static final String TAG = "ImageBrowserActivity";
    private static final String EXTRA_URl = "url";
    private static final String EXTRA_LIST = "list";

    private static final int ALPHA_MAX = 0xFF;

    private int mCurrentPosition;
    private ArrayList<String> mImgList;

    private ViewPager mViewPager;
    private ColorDrawable mColorDrawable;

    private DismissFrameLayout.OnDismissListener onDismissListener = new DismissFrameLayout.OnDismissListener() {
        @Override
        public void onScaleProgress(float scale) {
            mColorDrawable.setAlpha(
                    Math.min(ALPHA_MAX, mColorDrawable.getAlpha() - (int) (scale * ALPHA_MAX)));
        }

        @Override
        public void onDismiss() {
            finish();
        }

        @Override
        public void onCancel() {
            mColorDrawable.setAlpha(ALPHA_MAX);
        }
    };


    public static void start(Context context, String url, ArrayList<String> imgList) {
        Intent starter = new Intent(context, ImageBrowserActivity.class);
        starter.putExtra(EXTRA_URl, url);
        starter.putStringArrayListExtra(EXTRA_LIST, imgList);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.activity_imagebrowser);

        FrameLayout container = findViewById(R.id.container);
        mColorDrawable = new ColorDrawable(getResources().getColor(R.color.Black));
        container.setBackgroundDrawable(mColorDrawable);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        mImgList = intent.getStringArrayListExtra(EXTRA_LIST);
        String url = intent.getStringExtra(EXTRA_URl);
        mCurrentPosition = mImgList.indexOf(url);

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new PhotoAdapter(mImgList, onDismissListener));
        mViewPager.setCurrentItem(mCurrentPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (slidrInterface != null) {
                    if (position == 0) {
                        slidrInterface.unlock();
                    } else {
                        slidrInterface.lock();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void fullScreen() {

        int newUiOptions = getWindow().getDecorView().getSystemUiVisibility();

        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public class PhotoAdapter extends PagerAdapter {
        List<String> mList;
        DismissFrameLayout.OnDismissListener mOnDismissListener;
        SparseArray<View> mCacheViewArray;

        PhotoAdapter(List<String> mList, DismissFrameLayout.OnDismissListener onDismissListener) {
            this.mList = mList;
            this.mOnDismissListener = onDismissListener;
            this.mCacheViewArray = new SparseArray<>(mList.size());
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view = mCacheViewArray.get(position);

            if (view == null) {
                Context context = container.getContext();
                view = LayoutInflater.from(context).inflate(R.layout.item_image_browser, container, false);
                view.setTag(position);
                PhotoView imageView = view.findViewById(R.id.photoView);
                ImageLoader.loadNormal(context, mList.get(position), imageView);

                DismissFrameLayout layout = view.findViewById(R.id.dismissContainter);
                layout.setDismissListener(mOnDismissListener);

                mCacheViewArray.put(position, view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
