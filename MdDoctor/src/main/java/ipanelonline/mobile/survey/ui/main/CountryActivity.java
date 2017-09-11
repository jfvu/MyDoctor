package ipanelonline.mobile.survey.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CountyBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

public class CountryActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_country)
    LinearLayout mLlStatusbarActivityCountry;
    @BindView(R.id.rv_activity_country)
    RecyclerView mRvActivityCountry;
    private ArrayList<CountyBean> mDatas = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        ViewGroup.LayoutParams params = mLlStatusbarActivityCountry.getLayoutParams();
        params.height = getStatusBarHeight();
        mLlStatusbarActivityCountry.setBackgroundColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        getList();

        mRvActivityCountry.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mDatas);
        mRvActivityCountry.setAdapter(mAdapter);
    }

    private void getList() {
        HashMap<String,String> params = new HashMap<>();
        HttpUtil.post(Api.URL_COUNTRY, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray ary = new JSONArray(result);
                    for (int i = 0; i < ary.length(); i++) {
                        JSONObject obj = ary.getJSONObject(i);
                        String name = obj.getString("name");
                        String id = obj.getString("id");
                        String lang = obj.getJSONObject("lang").toString();
                        mDatas.add(new CountyBean(name,lang,id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String status, String error) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_country;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        private ArrayList<CountyBean> mDatas;

        public MyAdapter(ArrayList<CountyBean> datas) {

            mDatas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CountryActivity.this).inflate(R.layout.item_rv_country, parent, false);
            AutoUtils.autoSize(view);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final CountyBean countyBean = mDatas.get(position);
            holder.mView.setText(countyBean.name);
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CountryActivity.this, LangActivity.class);
                    intent.putExtra("lang",countyBean.lang);
                    intent.putExtra("id",countyBean.id);
                    startActivity(intent);


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
            }
        }
    }
}
