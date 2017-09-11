package ipanelonline.mobile.survey.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.AreaBean;
import ipanelonline.mobile.survey.entity.CityBean;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

public class Step1Activity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_area)
    LinearLayout mLlStatusbarActivityArea;
    @BindView(R.id.rv_activity_area)
    RecyclerView mRvActivityArea;
    @BindView(R.id.tv_show)
    TextView mTvShow;
    private Intent mIntent;
    private ArrayList mList = new ArrayList();
    public final static int RESULT_CODE = 2;
    private String mString;
    private String mTitle;
    private ArrayList<CityBean> mDatas;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra("title");
            String data = intent.getStringExtra("data");
            Gson gson = new Gson();
            mTvShow.setText(mTitle);
            if (mTitle.equals("选择地区")) {//城市页面
                ArrayList<CityBean> provinces = gson.fromJson(data, new TypeToken<ArrayList<CityBean>>() {
                }.getType());
                mRvActivityArea.setAdapter(new MyAdapter(provinces));
            } else if(mTitle.equals("行业")){
                mDatas = App.getVocationData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));
            }else if(mTitle.equals("教育")){
                mDatas = App.getEducationData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));

            }else if(mTitle.equals("职位")){
                mDatas = App.getDutyData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));

            }else if(mTitle.equals("工资")){
                mDatas = App.getMonthlySalaryData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));

            }else if(mTitle.equals("单位性质")){
                mDatas = App.getPropertyData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));

            }else if(mTitle.equals("单位规模")){
                mDatas = App.getScopeData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));

            }else if(mTitle.equals("工作状态")){
                mDatas = App.getEmployeeData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));

            }else if(mTitle.equals("家庭收入")){
                mDatas = App.getHouseSalaryData();
                mRvActivityArea.setAdapter(new MyAdapter1(mDatas));

            }
        }


    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityArea.getLayoutParams();
        params.height = getStatusBarHeight();
        mLlStatusbarActivityArea.setBackgroundColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        mRvActivityArea.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_area;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        private ArrayList<CityBean> mDatas;

        public MyAdapter(ArrayList<CityBean> datas) {

            mDatas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Step1Activity.this).inflate(R.layout.item_rv_country, parent, false);
            AutoUtils.autoSize(view);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final CityBean cityBean = mDatas.get(position);
            holder.mView.setText(cityBean.name);
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Step1Activity.this, Step2Activity.class);
                    if (mTitle.equals("选择地区")) {//城市页面
                        Gson gson = new Gson();
                        String data = gson.toJson(cityBean.areas, new TypeToken<ArrayList<AreaBean>>() {
                        }.getType());
                        intent.putExtra("title",cityBean.name);
                        intent.putExtra("data", data);
                        startActivityForResult(intent,0);
                    } else {//市区页面

                    }
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

    class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.ViewHolder> {


        private ArrayList<CityBean> mDatas;

        public MyAdapter1(ArrayList<CityBean> datas) {

            mDatas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Step1Activity.this).inflate(R.layout.item_rv_country, parent, false);
            AutoUtils.autoSize(view);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            CityBean cityBean = mDatas.get(position);
            final String text = cityBean.name;
            final String id = cityBean.id;
            holder.mView.setText(text);
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("data",text);
                    intent.putExtra("id",id);
                    setResult(0,intent);
                    finish();
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
