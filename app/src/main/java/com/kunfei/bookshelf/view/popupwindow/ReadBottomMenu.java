package com.kunfei.bookshelf.view.popupwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kunfei.bookshelf.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadBottomMenu extends FrameLayout {

    @BindView(R.id.vw_bg)
    View vwBg;
    @BindView(R.id.tv_pre)
    TextView tvPre;
    @BindView(R.id.hpb_read_progress)
    SeekBar hpbReadProgress;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_catalog)
    LinearLayout llCatalog;
    @BindView(R.id.ll_adjust)
    LinearLayout llAdjust;
    @BindView(R.id.ll_font)
    LinearLayout llFont;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.llNavigationBar)
    LinearLayout llNavigationBar;
    @BindView(R.id.vwNavigationBar)
    View vwNavigationBar;

    private Callback callback;

    public ReadBottomMenu(Context context) {
        super(context);
        init(context);
    }

    public ReadBottomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReadBottomMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_read_menu, this);
        ButterKnife.bind(this, view);
        vwBg.setOnClickListener(null);
        vwNavigationBar.setOnClickListener(null);
    }

    public void setNavigationBarHeight(int height) {
        vwNavigationBar.getLayoutParams().height = height;
    }

    public void setListener(Callback callback) {
        this.callback = callback;
        bindEvent();
    }

    private void bindEvent() {
        //阅读进度
        hpbReadProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                callback.skipToPage(seekBar.getProgress());
            }
        });

        //上一章
        tvPre.setOnClickListener(view -> callback.skipPreChapter());

        //下一章
        tvNext.setOnClickListener(view -> callback.skipNextChapter());

        //目录
        llCatalog.setOnClickListener(view -> callback.openChapterList());

        //调节
        llAdjust.setOnClickListener(view -> callback.openAdjust());

        //界面
        llFont.setOnClickListener(view -> callback.openReadInterface());

        //设置
        llSetting.setOnClickListener(view -> callback.openMoreSetting());

    }

    public SeekBar getReadProgress() {
        return hpbReadProgress;
    }

    public void setTvPre(boolean enable) {
        tvPre.setEnabled(enable);
    }

    public void setTvNext(boolean enable) {
        tvNext.setEnabled(enable);
    }

    public interface Callback {
        void skipToPage(int page);

        void skipPreChapter();

        void skipNextChapter();

        void openChapterList();

        void openAdjust();

        void openReadInterface();

        void openMoreSetting();

        void toast(int id);

        void dismiss();
    }

}
