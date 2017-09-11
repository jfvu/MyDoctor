package ipanelonline.mobile.survey.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

public class DealActivity extends BaseActivity {

    @BindView(R.id.ll_statusbar)
    LinearLayout mLlStatusbar;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.tv_show)
    TextView mTvShow;
    @BindView(R.id.tv_titleshow)
    TextView mTvTitleshow;
    private Intent mIntent;
    private int i;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params1 = mLlStatusbar.getLayoutParams();
        params1.height = getStatusBarHeight();
        mLlStatusbar.setBackgroundColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        mIntent = getIntent();
        i = mIntent.getIntExtra("DealActivity", 0);
        if (i == 2) {
            mTvTitleshow.setText("关于我们");
        }else {
            mTvTitleshow.setText("注册协议");
        }


        HashMap<String, String> params = new HashMap<>();
        SharedPreferences sp = App.context.getSharedPreferences("config_lang", App.context.MODE_APPEND);
        String host = sp.getString("host", "");
        params.put("country", sp.getString("country", ""));
        params.put("lang", sp.getString("lang", ""));
        params.put("type", i + "");
        Log.e("deal", params.toString());
        Log.e("deal", Api.URL_INFO.toString());
        Log.e("deal", host + Api.URL_INFO);
        HttpUtil.post(Api.URL_INFO, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.e("deal", result.toString());
                mTvShow.setText(Html.fromHtml(result));
            }

            @Override
            public void onError(String status, String error) {
                showTaost(error);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deal;
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
