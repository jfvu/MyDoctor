package ipanelonline.mobile.survey.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
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

import butterknife.BindView;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.AreaBean;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

public class Step2Activity extends BaseActivity {


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

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra("title");
            String data = intent.getStringExtra("data");
            Gson gson = new Gson();
            ArrayList<AreaBean> areas = gson.fromJson(data, new TypeToken<ArrayList<AreaBean>>() {
            }.getType());
            mTvShow.setText(mTitle);
            mRvActivityArea.setAdapter(new MyAdapter(areas));
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


        private ArrayList<AreaBean> mDatas;

        public MyAdapter(ArrayList<AreaBean> datas) {

            mDatas = datas;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Step2Activity.this).inflate(R.layout.item_rv_country, parent, false);
            AutoUtils.autoSize(view);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {

            final String text = mDatas.get(position).name;
            holder.mView.setText(text);
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Step2Activity.this,PersonalDataActivity.class);
                    SharedPreferences sp = getSharedPreferences("config", MODE_APPEND);
                    sp.edit().putBoolean("isSet",true).putString("data",mTitle + "," + text).putString("id",mDatas.get(position).id).putString("pid",mDatas.get(position).pid).commit();
                    startActivity(intent);
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
