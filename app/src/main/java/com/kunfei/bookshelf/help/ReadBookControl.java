//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.kunfei.bookshelf.help;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.kunfei.bookshelf.MApplication;
import com.kunfei.bookshelf.utils.BitmapUtil;
import com.kunfei.bookshelf.utils.MeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kunfei.bookshelf.widget.page.PageLoader.DEFAULT_MARGIN_WIDTH;

public class ReadBookControl {
    private Map<String, Integer> textDrawable;
    private int screenDirection;
    private int textSize;
    private float lineMultiplier;
    private float paragraphSize;
    private int pageMode;
    private Boolean hideStatusBar;
    private Boolean hideNavigationBar;
    private String fontPath;
    private int textConvert;
    private Boolean textBold;
    private Boolean canClickTurn;
    private Boolean canKeyTurn;
    private Boolean clickAllNext;
    private Boolean showTitle;
    private Boolean showLine;
    private int indent;
    private int screenTimeOut;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private int tipPaddingLeft;
    private int tipPaddingTop;
    private int tipPaddingRight;
    private int tipPaddingBottom;
    private float textLetterSpacing;
    private boolean canSelectText;

    private SharedPreferences preferences;

    private static ReadBookControl readBookControl;

    public static ReadBookControl getInstance() {
        if (readBookControl == null) {
            synchronized (ReadBookControl.class) {
                if (readBookControl == null) {
                    readBookControl = new ReadBookControl();
                }
            }
        }
        return readBookControl;
    }

    private ReadBookControl() {
        preferences = MApplication.getConfigPreferences();
        initTextDrawable();
        updateReaderSettings();
    }

    void updateReaderSettings() {
        this.hideStatusBar = preferences.getBoolean("hide_status_bar", false);
        this.hideNavigationBar = preferences.getBoolean("hide_navigation_bar", false);
        this.indent = preferences.getInt("indent", 2);
        this.textSize = preferences.getInt("textSize", 20);
        this.canClickTurn = preferences.getBoolean("canClickTurn", true);
        this.canKeyTurn = preferences.getBoolean("canKeyTurn", true);
        this.lineMultiplier = preferences.getFloat("lineMultiplier", 1);
        this.paragraphSize = preferences.getFloat("paragraphSize", 1);
        this.clickAllNext = preferences.getBoolean("clickAllNext", false);
        this.fontPath = preferences.getString("fontPath", null);
        this.textConvert = preferences.getInt("textConvertInt", 0);
        this.textBold = preferences.getBoolean("textBold", false);
        this.showTitle = preferences.getBoolean("showTitle", true);
        this.showLine = preferences.getBoolean("showLine", true);
        this.screenTimeOut = preferences.getInt("screenTimeOut", 0);
        this.paddingLeft = preferences.getInt("paddingLeft", DEFAULT_MARGIN_WIDTH);
        this.paddingTop = preferences.getInt("paddingTop", 0);
        this.paddingRight = preferences.getInt("paddingRight", DEFAULT_MARGIN_WIDTH);
        this.paddingBottom = preferences.getInt("paddingBottom", 0);
        this.tipPaddingLeft = preferences.getInt("tipPaddingLeft", DEFAULT_MARGIN_WIDTH);
        this.tipPaddingTop = preferences.getInt("tipPaddingTop", 0);
        this.tipPaddingRight = preferences.getInt("tipPaddingRight", DEFAULT_MARGIN_WIDTH);
        this.tipPaddingBottom = preferences.getInt("tipPaddingBottom", 0);
        this.pageMode = preferences.getInt("pageMode", 0);
        this.screenDirection = preferences.getInt("screenDirection", 0);
        this.textLetterSpacing = preferences.getFloat("textLetterSpacing", 0);
        this.canSelectText = preferences.getBoolean("canSelectText", false);
    }

    //阅读背景
    private void initTextDrawable() {
        if (null == textDrawable) {
            textDrawable = new HashMap<>();
            textDrawable.put("textColor", Color.parseColor("#000000"));
            textDrawable.put("bgIsColor", 1);
            textDrawable.put("textBackground", Color.parseColor("#FFFFFF"));
        }
    }

    public int getTextColor() {
        return textDrawable.get("textColor");
    }

    public void setTextColor(int textColor) {
        preferences.edit()
                .putInt("textColor", textColor)
                .apply();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        preferences.edit()
                .putInt("textSize", textSize)
                .apply();
    }

    public Drawable getTextBackground(Context context) {
        int bgColor = getBgColor();
        return new ColorDrawable(bgColor);
    }

    public int getBgColor() {
        return textDrawable.get("textBackground");
    }

    public void setTextConvert(int textConvert) {
        this.textConvert = textConvert;
        preferences.edit()
                .putInt("textConvertInt", textConvert)
                .apply();
    }

    public void setTextBold(boolean textBold) {
        this.textBold = textBold;
        preferences.edit()
                .putBoolean("textBold", textBold)
                .apply();
    }

    public void setReadBookFont(String fontPath) {
        this.fontPath = fontPath;
        preferences.edit()
                .putString("fontPath", fontPath)
                .apply();
    }

    public String getFontPath() {
        return fontPath;
    }

    public int getTextConvert() {
        return textConvert == -1 ? 2 : textConvert;
    }

    public Boolean getTextBold() {
        return textBold;
    }

    public Boolean getCanKeyTurn() {
        return canKeyTurn;
    }

    public void setCanKeyTurn(Boolean canKeyTurn) {
        this.canKeyTurn = canKeyTurn;
        preferences.edit()
                .putBoolean("canKeyTurn", canKeyTurn)
                .apply();
    }

    public Boolean getCanClickTurn() {
        return canClickTurn;
    }

    public void setCanClickTurn(Boolean canClickTurn) {
        this.canClickTurn = canClickTurn;
        preferences.edit()
                .putBoolean("canClickTurn", canClickTurn)
                .apply();
    }

    public float getTextLetterSpacing() {
        return textLetterSpacing;
    }

    public void setTextLetterSpacing(float textLetterSpacing) {
        this.textLetterSpacing = textLetterSpacing;
        preferences.edit()
                .putFloat("textLetterSpacing", textLetterSpacing)
                .apply();
    }

    public float getLineMultiplier() {
        return lineMultiplier;
    }

    public void setLineMultiplier(float lineMultiplier) {
        this.lineMultiplier = lineMultiplier;
        preferences.edit()
                .putFloat("lineMultiplier", lineMultiplier)
                .apply();
    }

    public float getParagraphSize() {
        return paragraphSize;
    }

    public void setParagraphSize(float paragraphSize) {
        this.paragraphSize = paragraphSize;
        preferences.edit()
                .putFloat("paragraphSize", paragraphSize)
                .apply();
    }

    public Boolean getClickAllNext() {
        return clickAllNext;
    }

    public void setClickAllNext(Boolean clickAllNext) {
        this.clickAllNext = clickAllNext;
        preferences.edit()
                .putBoolean("clickAllNext", clickAllNext)
                .apply();
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
        preferences.edit()
                .putBoolean("showTitle", showTitle)
                .apply();
    }

    public Boolean getShowLine() {
        return showLine;
    }

    public void setShowLine(Boolean showLine) {
        this.showLine = showLine;
        preferences.edit()
                .putBoolean("showLine", showLine)
                .apply();
    }

    public int getScreenTimeOut() {
        return screenTimeOut;
    }

    public void setScreenTimeOut(int screenTimeOut) {
        this.screenTimeOut = screenTimeOut;
        preferences.edit()
                .putInt("screenTimeOut", screenTimeOut)
                .apply();
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        preferences.edit()
                .putInt("paddingLeft", paddingLeft)
                .apply();
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        preferences.edit()
                .putInt("paddingTop", paddingTop)
                .apply();
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        preferences.edit()
                .putInt("paddingRight", paddingRight)
                .apply();
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        preferences.edit()
                .putInt("paddingBottom", paddingBottom)
                .apply();
    }

    public int getTipPaddingLeft() {
        return tipPaddingLeft;
    }

    public void setTipPaddingLeft(int tipPaddingLeft) {
        this.tipPaddingLeft = tipPaddingLeft;
        preferences.edit()
                .putInt("tipPaddingLeft", tipPaddingLeft)
                .apply();
    }

    public boolean isCanSelectText() {
        return canSelectText;
    }

    public void setCanSelectText(boolean canSelectText) {
        this.canSelectText = canSelectText;
        preferences.edit()
                .putBoolean("canSelectText", canSelectText)
                .apply();
    }

    public int getTipPaddingTop() {
        return tipPaddingTop;
    }

    public void setTipPaddingTop(int tipPaddingTop) {
        this.tipPaddingTop = tipPaddingTop;
        preferences.edit()
                .putInt("tipPaddingTop", tipPaddingTop)
                .apply();
    }

    public int getTipPaddingRight() {
        return tipPaddingRight;
    }

    public void setTipPaddingRight(int tipPaddingRight) {
        this.tipPaddingRight = tipPaddingRight;
        preferences.edit()
                .putInt("tipPaddingRight", tipPaddingRight)
                .apply();
    }

    public int getTipPaddingBottom() {
        return tipPaddingBottom;
    }

    public void setTipPaddingBottom(int tipPaddingBottom) {
        this.tipPaddingBottom = tipPaddingBottom;
        preferences.edit()
                .putInt("tipPaddingBottom", tipPaddingBottom)
                .apply();
    }

    public int getPageMode() {
        return pageMode;
    }

    public void setPageMode(int pageMode) {
        this.pageMode = pageMode;
        preferences.edit()
                .putInt("pageMode", pageMode)
                .apply();
    }

    public int getScreenDirection() {
        return screenDirection;
    }

    public void setScreenDirection(int screenDirection) {
        this.screenDirection = screenDirection;
        preferences.edit()
                .putInt("screenDirection", screenDirection)
                .apply();
    }

    public void setIndent(int indent) {
        this.indent = indent;
        preferences.edit()
                .putInt("indent", indent)
                .apply();
    }

    public int getIndent() {
        return indent;
    }

    public int getLight() {
        return preferences.getInt("light", getScreenBrightness());
    }

    public void setLight(int light) {
        preferences.edit()
                .putInt("light", light)
                .apply();
    }

    public Boolean getLightFollowSys() {
        return preferences.getBoolean("lightFollowSys", true);
    }

    public void setLightFollowSys(boolean isFollowSys) {
        preferences.edit()
                .putBoolean("lightFollowSys", isFollowSys)
                .apply();
    }

    private int getScreenBrightness() {
        int value = 0;
        ContentResolver cr = MApplication.getInstance().getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException ignored) {
        }
        return value;
    }
}
