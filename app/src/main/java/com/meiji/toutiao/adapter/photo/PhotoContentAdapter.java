package com.meiji.toutiao.adapter.photo;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.module.base.BaseActivity;
import com.meiji.toutiao.util.ImageLoader;
import com.meiji.toutiao.util.SettingUtil;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Meiji on 2017/2/17.
 */

public class PhotoContentAdapter extends PagerAdapter {
    private static final String TAG = "PhotoContentAdapter";
    private Context context;
    private PhotoGalleryBean galleryBean;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    public PhotoContentAdapter(Context context, PhotoGalleryBean galleryBean) {
        this.context = context;
        this.galleryBean = galleryBean;
        this.cacheView = new SparseArray<>(galleryBean.getCount());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        if (containerTemp == null)
            containerTemp = container;

        View view = cacheView.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo_content, container, false);
            view.setTag(position);
            final ImageView iv_image = view.findViewById(R.id.iv_image);
            final TextView tv_abstract = view.findViewById(R.id.tv_abstract);
            final TextView tv_onclick = view.findViewById(R.id.tv_onclick);
            final ProgressBar progressBar = view.findViewById(R.id.pb_progress);
            int color = SettingUtil.getInstance().getColor();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, color);
                progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else {
                progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }

            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(iv_image);
            photoViewAttacher.setOnPhotoTapListener((view12, x, y) -> {
                BaseActivity activity = (BaseActivity) context;
                activity.finish();
            });

            final List<PhotoGalleryBean.SubImagesBean> sub_images = galleryBean.getSub_images();
            List<String> sub_abstracts = galleryBean.getSub_abstracts();

            final RequestListener listener = new RequestListener() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            };

            if (!SettingUtil.getInstance().getIsNoPhotoMode()) {
                ImageLoader.loadCenterCrop(context, sub_images.get(position).getUrl(), iv_image, listener);
            } else {
                progressBar.setVisibility(View.GONE);
                tv_onclick.setVisibility(View.VISIBLE);
                view.findViewById(R.id.layout_onclick).setOnClickListener(view1 -> {
                    progressBar.setVisibility(View.VISIBLE);
                    tv_onclick.setVisibility(View.GONE);
                    ImageLoader.loadCenterCrop(context, sub_images.get(position).getUrl(), iv_image, listener);
                });
            }
            tv_abstract.setText(sub_abstracts.get(position));
            cacheView.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return galleryBean.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
