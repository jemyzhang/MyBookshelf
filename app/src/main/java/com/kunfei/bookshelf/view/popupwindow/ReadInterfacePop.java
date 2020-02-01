//Copyright (c) 2017. 章钦豪. All rights reserved.
package com.kunfei.bookshelf.view.popupwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.kunfei.bookshelf.R;
import com.kunfei.bookshelf.help.ReadBookControl;
import com.kunfei.bookshelf.help.permission.Permissions;
import com.kunfei.bookshelf.help.permission.PermissionsCompat;
import com.kunfei.bookshelf.utils.theme.ATH;
import com.kunfei.bookshelf.view.activity.ReadBookActivity;
import com.kunfei.bookshelf.widget.font.FontSelector;
import com.kunfei.bookshelf.widget.page.animation.PageAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.Unit;

public class ReadInterfacePop extends FrameLayout {
    @BindView(R.id.vw_bg)
    View vwBg;
    @BindView(R.id.fl_text_Bold)
    TextView flTextBold;
    @BindView(R.id.fl_text_font)
    TextView fl_text_font;

    @BindView(R.id.nbTextSize)
    TextView nbTextSize;
    @BindView(R.id.nbTextSizeAdd)
    TextView nbTextSizeAdd;
    @BindView(R.id.nbTextSizeDec)
    TextView nbTextSizeDec;
    @BindView(R.id.fl_indent)
    TextView tvIndent;
    @BindView(R.id.tvPageMode)
    TextView tvPageMode;
    @BindView(R.id.tvRowDef0)
    TextView tvRowDef0;
    @BindView(R.id.tvRowDef1)
    TextView tvRowDef1;
    @BindView(R.id.tvRowDef2)
    TextView tvRowDef2;
    @BindView(R.id.tvRowDef)
    TextView tvRowDef;
    @BindView(R.id.tvOther)
    ImageView tvOther;
    @BindView(R.id.vwNavigationBar_pri)
    View vwNavigationBar;

    private ReadBookActivity activity;
    private ReadBookControl readBookControl = ReadBookControl.getInstance();
    private Callback callback;

    public ReadInterfacePop(Context context) {
        super(context);
        init(context);
    }

    public ReadInterfacePop(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReadInterfacePop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_read_interface, this);
        ButterKnife.bind(this, view);
        vwBg.setOnClickListener(null);
        vwNavigationBar.setOnClickListener(null);
    }

    public void setNavigationBarHeight(int height) {
        vwNavigationBar.getLayoutParams().height = height;
    }

    public void setListener(ReadBookActivity readBookActivity, @NonNull Callback callback) {
        this.activity = readBookActivity;
        this.callback = callback;
        initData();
        bindEvent();
    }

    private void initData() {
        updateBoldText(readBookControl.getTextBold());
        updatePageMode(readBookControl.getPageMode());

        nbTextSize.setText(String.format("%d", readBookControl.getTextSize()));    }

    /**
     * 控件事件
     */
    private void bindEvent() {
        //字号减
        nbTextSizeDec.setOnClickListener(v -> {
            int fontSize = readBookControl.getTextSize() - 1;
            if (fontSize < 10) fontSize = 10;
            readBookControl.setTextSize(fontSize);
            nbTextSize.setText(String.format("%d", readBookControl.getTextSize()));
            callback.upTextSize();
        });
        //字号加
        nbTextSizeAdd.setOnClickListener(v -> {
            int fontSize = readBookControl.getTextSize() + 1;
            if (fontSize > 40) fontSize = 40;
            readBookControl.setTextSize(fontSize);
            nbTextSize.setText(String.format("%d", readBookControl.getTextSize()));
            callback.upTextSize();
        });
        //缩进
        tvIndent.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(activity, R.style.alertDialogTheme)
                    .setTitle(activity.getString(R.string.indent))
                    .setSingleChoiceItems(activity.getResources().getStringArray(R.array.indent),
                            readBookControl.getIndent(),
                            (dialogInterface, i) -> {
                                readBookControl.setIndent(i);
                                callback.refresh();
                                dialogInterface.dismiss();
                            })
                    .create();
            dialog.show();
            ATH.setAlertDialogTint(dialog);
        });
        //翻页模式
        tvPageMode.setOnClickListener(view -> {
            AlertDialog dialog = new AlertDialog.Builder(activity, R.style.alertDialogTheme)
                    .setTitle(activity.getString(R.string.page_mode))
                    .setSingleChoiceItems(PageAnimation.Mode.getAllPageMode(), readBookControl.getPageMode(), (dialogInterface, i) -> {
                        readBookControl.setPageMode(i);
                        updatePageMode(i);
                        callback.upPageMode();
                        dialogInterface.dismiss();
                    })
                    .create();
            dialog.show();
            ATH.setAlertDialogTint(dialog);
        });
        //加粗切换
        flTextBold.setOnClickListener(view -> {
            readBookControl.setTextBold(!readBookControl.getTextBold());
            updateBoldText(readBookControl.getTextBold());
            callback.upTextSize();
        });
        //行距单倍
        tvRowDef0.setOnClickListener(v -> {
            readBookControl.setLineMultiplier(0.6f);
            readBookControl.setParagraphSize(1.5f);
            callback.upTextSize();
        });
        //行距双倍
        tvRowDef1.setOnClickListener(v -> {
            readBookControl.setLineMultiplier(1.2f);
            readBookControl.setParagraphSize(1.8f);
            callback.upTextSize();
        });
        //行距三倍
        tvRowDef2.setOnClickListener(v -> {
            readBookControl.setLineMultiplier(1.8f);
            readBookControl.setParagraphSize(2.0f);
            callback.upTextSize();
        });
        //行距默认
        tvRowDef.setOnClickListener(v -> {
            readBookControl.setLineMultiplier(1.0f);
            readBookControl.setParagraphSize(1.8f);
            callback.upTextSize();
        });
        //自定义间距
        tvOther.setOnClickListener(v -> {
            activity.readAdjustMarginIn();
        });

        //选择字体
        fl_text_font.setOnClickListener(view -> {
            new PermissionsCompat.Builder(activity)
                    .addPermissions(Permissions.READ_EXTERNAL_STORAGE, Permissions.WRITE_EXTERNAL_STORAGE)
                    .rationale(R.string.get_storage_per)
                    .onGranted((requestCode) -> {
                        new FontSelector(activity, readBookControl.getFontPath())
                                .setListener(new FontSelector.OnThisListener() {
                                    @Override
                                    public void setDefault() {
                                        clearFontPath();
                                    }

                                    @Override
                                    public void setFontPath(String fontPath) {
                                        setReadFonts(fontPath);
                                    }
                                })
                                .create()
                                .show();
                        return Unit.INSTANCE;
                    })
                    .request();
        });

        //长按清除字体
        fl_text_font.setOnLongClickListener(view -> {
            clearFontPath();
            activity.toast(R.string.clear_font);
            return true;
        });
    }

    //设置字体
    public void setReadFonts(String path) {
        readBookControl.setReadBookFont(path);
        callback.refresh();
    }

    //清除字体
    private void clearFontPath() {
        readBookControl.setReadBookFont(null);
        callback.refresh();
    }

    private void updatePageMode(int pageMode) {
        tvPageMode.setText(String.format("%s", PageAnimation.Mode.getPageMode(pageMode)));
    }

    private void updateBoldText(Boolean isBold) {
        flTextBold.setSelected(isBold);
    }

    public interface Callback {
        void upPageMode();

        void upTextSize();

        void upMargin();

        void bgChange();

        void refresh();
    }

}