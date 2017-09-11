package ipanelonline.mobile.survey.ui.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CountyBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

import static java.lang.String.valueOf;

public class LangActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_lang)
    LinearLayout mLlStatusbarActivityLang;
    @BindView(R.id.rl_return_activity_lang)
    RelativeLayout mRlReturnActivityLang;
    @BindView(R.id.rv_activity_lang)
    RecyclerView mRvActivityLang;
    private Intent mIntent;
    private MyAdapter mMyAdapter;
    private ArrayList<CountyBean> mList = new ArrayList<>();
    private String countryNum;

    @Override
    protected void initData() {
        mIntent = getIntent();
        String lang = mIntent.getStringExtra("lang");
        countryNum = mIntent.getStringExtra("id");
        try {
            JSONObject obj = new JSONObject(lang);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = obj.getString(key);
                mList.add(new CountyBean(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            mRvActivityLang.setAdapter(mMyAdapter);
        }
        HttpUtil.post(Api.URL_LANG_PACKAGE, this, null, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.e("lang",result);

            }

            @Override
            public void onError(String status, String error) {

            }
        });

    }

    @Override
    protected void initView() {

        ViewGroup.LayoutParams params = mLlStatusbarActivityLang.getLayoutParams();
        params.height = getStatusBarHeight();
        mLlStatusbarActivityLang.setBackgroundColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();


        mList = new ArrayList<>();
        mRvActivityLang.setLayoutManager(new LinearLayoutManager(this));
        mMyAdapter = new MyAdapter(mList);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lang;
    }


    @OnClick(R.id.rl_return_activity_lang)
    public void onViewClicked() {
        finish();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        private ArrayList<CountyBean> mDatas;

        public MyAdapter(ArrayList<CountyBean> datas) {

            mDatas = datas;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LangActivity.this).inflate(R.layout.item_rv_country, parent, false);
            AutoUtils.autoSize(view);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {

            final CountyBean countyBean = mDatas.get(position);
            holder.mView.setText(countyBean.lang);
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String host = null;
                    if (countryNum.equals(App.CHINA)){
                        host = "https://appapi.diaochatong.com:446/";
                    } else{
                        host = "https://appapi.ipanelonline.com/";
                    }
                    SharedPreferences sp = app.getSharedPreferences("config_lang", MODE_APPEND);
                    sp.edit().putString("host",host).putString("country",countryNum)
                    .putString("lang",countyBean.name).commit();
                    Intent intent = new Intent(LangActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    app.saveFirst();


                }
            });

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private LinearLayout mLayout;
            private TextView mView;

            public ViewHolder(View itemView) {
                super(itemView);
                mLayout = (LinearLayout) itemView.findViewById(R.id.ll_item_country);
                mView = (TextView) itemView.findViewById(R.id.tv_show);
                itemView.findViewById(R.id.img_right).setVisibility(View.GONE);
            }
        }
    }
}
