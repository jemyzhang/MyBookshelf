//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.kunfei.bookshelf;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
//import androidx.multidex.MultiDex;

import com.kunfei.bookshelf.constant.AppConstant;
import com.kunfei.bookshelf.help.AppFrontBackHelper;
import com.kunfei.bookshelf.help.CrashHandler;
import com.kunfei.bookshelf.help.FileHelp;
import com.kunfei.bookshelf.model.UpLastChapterModel;
import com.kunfei.bookshelf.utils.theme.ThemeStore;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;

public class MApplication extends Application {
    public final static String channelIdDownload = "channel_download";
    public static String downloadPath;
    public static String SEARCH_GROUP = null;
    private static MApplication instance;
    private static String versionName;
    private static int versionCode;
    private SharedPreferences configPreferences;

    public static MApplication getInstance() {
        return instance;
    }

    public static int getVersionCode() {
        return versionCode;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static Resources getAppResources() {
        return getInstance().getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);
        RxJavaPlugins.setErrorHandler(Functions.emptyConsumer());
        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = 0;
            versionName = "0.0.0";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId();
        }
        configPreferences = getSharedPreferences("CONFIG", 0);
        downloadPath = configPreferences.getString(getString(R.string.pk_download_path), "");
        if (TextUtils.isEmpty(downloadPath) | Objects.equals(downloadPath, FileHelp.getCachePath())) {
            setDownloadPath(null);
        }
        if (!ThemeStore.isConfigured(this, versionCode)) {
            upThemeStore();
        }
        AppFrontBackHelper.getInstance().register(this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
            }

            @Override
            public void onBack() {
                UpLastChapterModel.destroy();
            }
        });
    }

    /**
     * 初始化主题
     */
    public void upThemeStore() {
        ThemeStore.editTheme(this)
                .primaryColor(configPreferences.getInt("colorPrimary", getResources().getColor(R.color.md_grey_100)))
                .accentColor(configPreferences.getInt("colorAccent", getResources().getColor(R.color.md_pink_600)))
                .backgroundColor(configPreferences.getInt("colorBackground", getResources().getColor(R.color.md_grey_100)))
                .apply();
    }

    /*
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

     */

    /**
     * 设置下载地址
     */
    public void setDownloadPath(String path) {
        if (TextUtils.isEmpty(path)) {
            downloadPath = FileHelp.getFilesPath();
        } else {
            downloadPath = path;
        }
        AppConstant.BOOK_CACHE_PATH = downloadPath + File.separator + "book_cache" + File.separator;
        configPreferences.edit()
                .putString(getString(R.string.pk_download_path), path)
                .apply();
    }

    public static SharedPreferences getConfigPreferences() {
        return getInstance().configPreferences;
    }

    /**
     * 创建通知ID
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannelId() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //用唯一的ID创建渠道对象
        NotificationChannel downloadChannel = new NotificationChannel(channelIdDownload,
                getString(R.string.download_offline),
                NotificationManager.IMPORTANCE_LOW);
        //初始化channel
        downloadChannel.enableLights(false);
        downloadChannel.enableVibration(false);
        downloadChannel.setSound(null, null);

        //向notification manager 提交channel
        if (notificationManager != null) {
            notificationManager.createNotificationChannels(Collections.singletonList(downloadChannel));
        }
    }

}
