package com.meiji.toutiao.module.photo.content;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.meiji.toutiao.Constant;
import com.meiji.toutiao.InitApp;
import com.meiji.toutiao.bean.photo.PhotoGalleryBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.ExecutionException;

import static android.R.attr.path;

/**
 * Created by Meiji on 2017/2/16.
 */

class PhotoContentModel implements IPhotoContent.Model {

    private static final String TAG = "PhotoContentModel";
    private Gson gson = new Gson();
    private PhotoGalleryBean bean;

    @Override
    public boolean getRequestData(String url) {
        boolean flag = false;
        Log.d(TAG, "getRequestData: " + url);
        try {
            Document doc = Jsoup
                    .connect(url)
                    .userAgent(Constant.USER_AGENT_PC)
                    .get();
            // 取得所有的script tag
            Elements scripts = doc.getElementsByTag("script");
            for (Element e : scripts) {
                // 过滤字符串
                String script = e.toString();
                if (script.contains("var gallery = {")) {
                    // 只取得script的內容
                    script = e.childNode(0).toString();
                    // 取得JS变量数组
                    String[] vars = script.split("var ");
                    // 取得单个JS变量
                    for (String var : vars) {
                        // 取到满足条件的JS变量
                        if (var.contains("gallery = ")) {
                            int start = var.indexOf("=");
                            int end = var.lastIndexOf(";");
                            String json = var.substring(start + 1, end + 1);
                            // 处理特殊符号
                            JsonReader reader = new JsonReader(new StringReader(json));
                            reader.setLenient(true);
                            Log.d(TAG, "getRequestData: " + reader);
                            bean = gson.fromJson(reader, PhotoGalleryBean.class);
                            flag = true;
                        }
                    }
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public PhotoGalleryBean getData() {
        return bean;
    }

    @Override
    public boolean SaveImage(final String url, Context context) {
        boolean flag = false;

        try {
            // 获取 bitmap
            Bitmap bitmap = Glide.with(InitApp.AppContext).load(url).asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            // http://stormzhang.com/android/2014/07/24/android-save-image-to-gallery/
            if (bitmap != null) {
                // 首先保存图片
                File appDir = new File(Environment.getExternalStorageDirectory(), "Toutiao");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                // 其次把文件插入到系统图库
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

                flag = true;
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
