package com.meiji.toutiao.module.media.wip.tab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.meiji.toutiao.ErrorAction;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.media.MediaProfileBean;
import com.meiji.toutiao.database.dao.MediaChannelDao;
import com.meiji.toutiao.module.base.LazyLoadFragment;
import com.meiji.toutiao.utils.ImageLoader;
import com.meiji.toutiao.utils.SettingsUtil;
import com.meiji.toutiao.widget.CircleImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.meiji.toutiao.module.media.wip.tab.MediaProfilePresenter.KEY_MEDIAID;

/**
 * Created by Meiji on 2017/6/29.
 */

public class MediaLatestFragment extends LazyLoadFragment<IMediaProfile.Presenter> implements IMediaProfile.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MediaLatestFragment";
    private MediaProfileBean.DataBean dataBean = null;
    private MediaChannelDao dao = new MediaChannelDao();

    private ImageView iv_bg;
    private CircleImageView cv_avatar;
    private TextView tv_name;
    private TextView tv_desc;
    private TextView tv_is_sub;
    private TextView tv_sub_count;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static MediaLatestFragment newInstance(Parcelable parcelable) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, parcelable);
        MediaLatestFragment fragment = new MediaLatestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowNetError() {

    }

    @Override
    public void setPresenter(IMediaProfile.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new MediaProfilePresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<?> list) {

    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_media_latest2;
    }

    @Override
    protected void initViews(View view) {
        this.iv_bg = (ImageView) view.findViewById(R.id.iv_bg);
        this.cv_avatar = (CircleImageView) view.findViewById(R.id.cv_avatar);
        this.tv_name = (TextView) view.findViewById(R.id.tv_name);
        this.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        this.tv_is_sub = (TextView) view.findViewById(R.id.tv_is_sub);
        this.tv_sub_count = (TextView) view.findViewById(R.id.tv_sub_count);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeColors(SettingsUtil.getInstance().getColor());
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        this.dataBean = bundle.getParcelable(TAG);
        if (null == dataBean) {
            onShowNetError();
            return;
        }

        // 设置头条号信息
        String imgUrl = dataBean.getBg_img_url();
        if (!TextUtils.isEmpty(imgUrl)) {
            ImageLoader.loadCenterCrop(getActivity(), imgUrl, iv_bg, R.color.viewBackground);
        }
        String avatarUrl = dataBean.getBig_avatar_url();
        if (!TextUtils.isEmpty(imgUrl)) {
            ImageLoader.loadCenterCrop(getActivity(), avatarUrl, cv_avatar, R.color.viewBackground);
        }
        tv_name.setText(dataBean.getName());
        tv_desc.setText(dataBean.getDescription());
        tv_sub_count.setText(dataBean.getFollowers_count() + " 订阅量");

        setIsSub();

        RxView.clicks(tv_is_sub)
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .map(new Function<Object, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Object o) throws Exception {
                        return dao.queryIsExist(dataBean.getMedia_id());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean isExist) throws Exception {
                        if (isExist) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("取消订阅\" " + dataBean.getName() + " \"?");
                            builder.setPositiveButton(R.string.button_enter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dao.delete(dataBean.getMedia_id());
                                        }
                                    }).start();
                                    tv_is_sub.setText("订阅");
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tv_is_sub.setText("已订阅");
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                        if (!isExist) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 保存到数据库
                                    dao.add(dataBean.getMedia_id(),
                                            dataBean.getName(),
                                            dataBean.getAvatar_url(),
                                            "news",
                                            dataBean.getFollowers_count(),
                                            dataBean.getDescription(),
                                            "http://toutiao.com/m" + dataBean.getMedia_id());
                                }
                            }).start();
                            tv_is_sub.setText("已订阅");
                            Toast.makeText(getContext(), "订阅成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean isExist) throws Exception {
                        setIsSub();
                    }
                }, ErrorAction.error());
    }

    @Override
    public void fetchData() {
        onLoadData();
    }

    private void setIsSub() {
        Observable
                .create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                        boolean isExist = dao.queryIsExist(dataBean.getMedia_id());
                        e.onNext(isExist);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean isExist) throws Exception {
                        if (isExist) {
                            tv_is_sub.setText("已订阅");
                        } else {
                            tv_is_sub.setText("订阅");
                        }
                    }
                }, ErrorAction.error());
    }

    @Override
    public void onLoadData() {
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put(KEY_MEDIAID, dataBean.getMedia_id());
        presenter.doLoadData(map);
    }

    @Override
    public void onRefresh() {

    }
}
