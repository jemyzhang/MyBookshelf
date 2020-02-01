package com.kunfei.bookshelf.model.content;

import android.annotation.SuppressLint;

import com.hwangjr.rxbus.RxBus;
import com.kunfei.bookshelf.constant.RxBusTag;
import com.kunfei.bookshelf.utils.StringUtils;
import com.kunfei.bookshelf.utils.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;


public class Debug {
    public static String SOURCE_DEBUG_TAG;
    @SuppressLint("ConstantLocale")
    private static final DateFormat DEBUG_TIME_FORMAT = new SimpleDateFormat("[mm:ss.SSS]", Locale.getDefault());
    private static long startTime;

    private static String getDoTime() {
        return TimeUtils.millis2String(System.currentTimeMillis() - startTime, DEBUG_TIME_FORMAT);
    }

    public static void printLog(String tag, String msg) {
        printLog(tag, 1, msg, true);
    }

    public static void printLog(String tag, int state, String msg) {
        printLog(tag, state, msg, true);
    }

    static void printLog(String tag, int state, String msg, boolean print) {
        printLog(tag, state, msg, print, false);
    }

    static void printLog(String tag, int state, String msg, boolean print, boolean formatHtml) {
        if (print && Objects.equals(SOURCE_DEBUG_TAG, tag)) {
            if (formatHtml) {
                msg = StringUtils.formatHtml(msg);
            }
            if (state == 111) {
                msg = msg.replace("\n", ",");
            }
            msg = String.format("%s %s", getDoTime(), msg);
            RxBus.get().post(RxBusTag.PRINT_DEBUG_LOG, msg);
        }
    }

}