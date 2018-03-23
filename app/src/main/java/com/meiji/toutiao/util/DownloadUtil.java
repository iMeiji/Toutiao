package com.meiji.toutiao.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.meiji.toutiao.ErrorAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static android.R.attr.path;

/**
 * Created by Meiji on 2018/3/23.
 */

public class DownloadUtil {

    public static Boolean saveImage(String url, Context context) {
        boolean flag = false;
        try {
            // 获取 bitmap
            Bitmap bitmap = Glide.with(context).asBitmap().load(url)
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
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
//                MediaStore.Images.Media.insertImage(InitApp.AppContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

                flag = true;
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            ErrorAction.print(e);
            return false;
        }
        return flag;
    }
}
