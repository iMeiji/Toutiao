package com.meiji.toutiao.photo.content;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.meiji.toutiao.R;
import com.meiji.toutiao.adapter.photo.PhotoContentAdapter;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;
import com.meiji.toutiao.photo.comment.PhotoCommentFragment;
import com.meiji.toutiao.view.ViewPagerFixed;

/**
 * Created by Meiji on 2017/3/1.
 */

public class PhotoContentFragment extends Fragment implements IPhotoContent.View, ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String TAG = "PhotoContentFragment";
    private Toolbar toolbar;
    private TextView tv_hint;
    private TextView tv_save;
    private ViewPagerFixed viewPager;
    private IPhotoContent.Presenter presenter;
    private ActionBar actionBar;
    private String shareUrl;
    private String shareTitle;
    private String group_id;
    private String item_id;
    private PhotoContentAdapter adapter;

    public static PhotoContentFragment newInstance(Parcelable dataBean) {
        PhotoContentFragment instance = new PhotoContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, dataBean);
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_content, container, false);
        presenter = new PhotoContentPresenter(this);
        initView(view);
        initData();
        setHasOptionsMenu(true);
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        PhotoArticleBean.DataBean dataBean = bundle.getParcelable(TAG);
        presenter.doRequestData(dataBean);
        shareUrl = "http://toutiao.com" + dataBean.getSource_url();
        shareTitle = dataBean.getTitle();
        actionBar.setTitle(dataBean.getMedia_name());
        group_id = dataBean.getGroup_id() + "";
        item_id = dataBean.getItem_id() + "";
    }

    private void initView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        tv_save = (TextView) view.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        viewPager = (ViewPagerFixed) view.findViewById(R.id.viewPager);
    }

    @Override
    public void onSetImageBrowser(PhotoGalleryBean bean, int position) {
        if (adapter == null) {
            adapter = new PhotoContentAdapter(getActivity(), bean);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
            viewPager.addOnPageChangeListener(this);
            tv_hint.setText(position + 1 + "/" + bean.getCount());
        }
    }

    @Override
    public void onFail() {
        Snackbar.make(viewPager, R.string.network_error, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.doSetPosition(position);
        tv_hint.setText(position + 1 + "/" + presenter.doGetImageCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_save:
                // 运行时权限处理
                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    presenter.doSaveImage();
                }
        }
    }

    @Override
    public void onSaveImageSuccess() {
        Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.doSaveImage();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_browser, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_open_comment:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, PhotoCommentFragment.newInstance(group_id, item_id), PhotoCommentFragment.class.getName())
                        .addToBackStack(PhotoCommentFragment.class.getName())
                        .hide(this)
                        .commit();
                break;

            case R.id.action_follow_media:
                break;

            case R.id.action_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(shareUrl)));
                break;

            case R.id.action_share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, shareTitle + "\n" + shareUrl);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
