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

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

public class SexActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_sex)
    LinearLayout mLlStatusbarActivitySex;
    @BindView(R.id.tv_show)
    TextView mTvShow;
    @BindView(R.id.rv_activity_sex)
    RecyclerView mRvActivitySex;
    private ArrayList mList;
    public final static int RESULT_CODE = 2;
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mList = new ArrayList();
        ViewGroup.LayoutParams params = mLlStatusbarActivitySex.getLayoutParams();
        params.height = getStatusBarHeight();
        mLlStatusbarActivitySex.setBackgroundColor(Color.parseColor("#ffffff"));
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        mList.add("男");
        mList.add("女");
        mRvActivitySex.setLayoutManager(new LinearLayoutManager(this));
        mRvActivitySex.setAdapter(new MyAdapter(mList));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sex;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        private ArrayList<String> mDatas;

        public MyAdapter(ArrayList datas) {

            mDatas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SexActivity.this).inflate(R.layout.item_rv_country, parent, false);
            AutoUtils.autoSize(view);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.mView.setText(mDatas.get(position));
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.putExtra("PersonalDataActivity",mDatas.get(position));
                    setResult(RESULT_CODE, intent);
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
