package ipanelonline.mobile.survey.ui.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.adapter.PointListAdapter;
import ipanelonline.mobile.survey.entity.IntergralBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

public class IntegralDetailActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate  {


    @BindView(R.id.ll_statusbar)
    LinearLayout mLlStatusbar;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.recy_list)
    LRecyclerView mRecyList;
    @BindView(R.id.refresh)
    BGARefreshLayout mRefresh;
    private boolean isRun = false;
    private ArrayList<IntergralBean> datas = new ArrayList();
    private PointListAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int page = 0;
    private int i;
    private Intent mIntent;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbar.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();

        mIntent = getIntent();
        i = mIntent.getIntExtra("IntegralDetailActivity",0);

        mRecyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new PointListAdapter(getBaseContext(),datas);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyList.setAdapter(mLRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(2f)
                .setColorResource(R.color.gray)
                .build();
        mRecyList.addItemDecoration(divider);
        mRecyList.setPullRefreshEnabled(false);
        mRecyList.setLoadMoreEnabled(false);

        mRefresh.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(App.context,true);
        mRefresh.setRefreshViewHolder(refreshViewHolder);
        mRefresh.setIsShowLoadingMoreView(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_detail;
    }



    @OnClick(R.id.img_back)
    public void onViewClicked() {
            finish();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (isRun) {
            return;
        }
        page = 0;
        requstData(page, true,i);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRun) {
            return false;
        }
        requstData(++page, false,i);
        return false;
    }
    /**
     * 获取积分数据
     *
     * @param page    ：页数
     * @param isClear ：是否清空之前的数据
     */
    private void requstData(int page, final boolean isClear,int i) {
        isRun = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        params.put("page", page + "");
        params.put("type",i+"");
        HttpUtil.post(Api.URL_POINT_DETAIL, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                if (isClear) {
                    datas.clear();
                }
                datas.addAll(parserPointJson(result));

                mLRecyclerViewAdapter.notifyDataSetChanged();
                //refreshLayout.endRefreshing();
                isRun = false;

            }

            @Override
            public void onError(String status, String error) {
                Toast.makeText(app, error, Toast.LENGTH_SHORT).show();
                if (status.equals("relogin")) {
                    startActivity(new Intent(IntegralDetailActivity.this, LoginActivity.class));
                    finish();
                }
                isRun = false;
                //refreshLayout.endRefreshing();

            }
        });
    }
    /**
     * 解析日志json数据
     *
     * @param result
     * @return
     */
    private ArrayList<IntergralBean> parserPointJson(String result) {
        ArrayList<IntergralBean> datas = null;
        try {
            datas = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                String point = obj.getString("point");
                String reason = obj.getString("reason");
                String point_change_time = obj.getString("point_change_time");
                IntergralBean intergralBean = new IntergralBean(point,reason,point_change_time);

                datas.add(intergralBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return datas;


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 0;
        requstData(page, true,i);
    }
}
