package com.meiji.toutiao.adapter.photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.utils.WindowUtil;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Meiji on 2017/2/17.
 */

public class PhotoContentAdapter extends PagerAdapter {
    private Context context;
    private PhotoGalleryBean galleryBean;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    public PhotoContentAdapter(Context context, PhotoGalleryBean galleryBean) {
        this.context = context;
        this.galleryBean = galleryBean;
        cacheView = new SparseArray<>(galleryBean.getCount());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if (containerTemp == null)
            containerTemp = container;

        View view = cacheView.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.photo_content_item, container, false);
            view.setTag(position);
            ImageView image = (ImageView) view.findViewById(R.id.iv_image);
            TextView text = (TextView) view.findViewById(R.id.tv_abstract);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);


            List<PhotoGalleryBean.SubImagesBean> sub_images = galleryBean.getSub_images();
            List<String> sub_abstracts = galleryBean.getSub_abstracts();

            //Glide.with(context).load(sub_images.get(position)).asBitmap().into(new MyTarget(photoViewAttacher));
            Glide.with(context).load(sub_images.get(position).getUrl()).asBitmap().centerCrop().into(image);
            text.setText(sub_abstracts.get(position));


            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
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

    private class MyTarget extends SimpleTarget<Bitmap> {

        private PhotoViewAttacher viewAttacher;

        public MyTarget(PhotoViewAttacher viewAttacher) {
            this.viewAttacher = viewAttacher;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            int width = resource.getWidth();
            int height = resource.getHeight();

            int newWidth = width;
            int newHeight = height;

            int screenWidth = WindowUtil.getInstance().getScreenWidth((Activity) context);
            int screenHeight = WindowUtil.getInstance().getScreenHeight((Activity) context);

            if (width > screenWidth) {
                newWidth = screenWidth;
            }

            if (height > screenHeight) {
                newHeight = screenHeight;
            }


            if (newWidth == width && newHeight == height) {
                viewAttacher.getImageView().setImageBitmap(resource);
                viewAttacher.update();
                return;
            }

            //计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            Log.v("size", width + "");
            Log.v("size", height + "");

            Bitmap resizeBitmap = Bitmap.createBitmap(resource, 0, 0, width, height, matrix, true);

            viewAttacher.getImageView().setImageBitmap(resizeBitmap);
            viewAttacher.update();
        }

    }

}
