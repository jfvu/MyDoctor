package ipanelonline.mobile.survey.ui.inquiry;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.adapter.InvestAdapter;
import ipanelonline.mobile.survey.entity.CountyBean;
import ipanelonline.mobile.survey.entity.InvestItem;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseFragment;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

import static android.media.CamcorderProfile.get;

/**
 * 调查的Framgent
 */
public class InvestFrag extends BaseFragment {


    @BindView(R.id.recy_invest)
    LRecyclerView mRecyclerView;
    private ArrayList<InvestItem> datas = new ArrayList();
    private InvestAdapter adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int statues = 1;
    private int page = 0;

    public InvestFrag(){
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invest;
    }


    @Override
    protected void initView() {
        statues = getArguments().getInt("state");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        adapter = new InvestAdapter(getActivity(),datas);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
    }


    @Override
    protected void loadData() {
        datas.clear();
        app = App.context;
        HashMap<String,String> params = new HashMap<>();
        params.put("uid",app.getId());
        params.put("login_token",app.getLoginToken());
        params.put("status",statues + "");
        params.put("page",page + "");
        HttpUtil.post(Api.URL_INVESTIGATE, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = (JSONObject) jsonArray.get(i);
                        InvestItem item = new InvestItem(obj.getString("id"),
                                obj.getString("title"),obj.getString("content"),obj.getString("avatar"),
                                obj.getString("startime"),obj.getString("endtime"),obj.getString("pointc"),
                                obj.getString("loi"),obj.getString("join_link"));
                        datas.add(item);
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String status,String error) {
                if(status.equals("empty")){//没有数据

                }else if(status.equals("relogin")){//登入令牌失效
                    app.setLoginToken("");
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();

                }
                Toast.makeText(app, error, Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    protected void setListaner() {
        //下拉刷新
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                HashMap<String,String> params = new HashMap<>();
                params.put("uid",app.getId());
                params.put("login_token",app.getLoginToken());
                params.put("status",statues + "");
                params.put("page",page + "");
                HttpUtil.post(Api.URL_INVESTIGATE, this, params, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            datas.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                InvestItem item = new InvestItem(obj.getString("id"),
                                        obj.getString("title"),obj.getString("content"),obj.getString("avatar"),
                                        obj.getString("startime"),obj.getString("endtime"),obj.getString("pointc"),
                                        obj.getString("loi"),obj.getString("join_link"));
                                datas.add(item);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                                mRecyclerView.refreshComplete(page);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(String status,String error) {
                        mRecyclerView.refreshComplete(page);
                        if(status.equals("empty")){//没有数据

                        }else if(status.equals("relogin")){//登入令牌失效
                            app.setLoginToken("");
                            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));

                        }
                        Toast.makeText(app, error, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        //加载更多
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final HashMap<String,String> params = new HashMap<>();
                params.put("uid",app.getId());
                params.put("login_token",app.getLoginToken());
                params.put("status",statues + "");
                params.put("page",++page + "");
                HttpUtil.post(Api.URL_INVESTIGATE, this, params, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = (JSONObject) jsonArray.get(i);
                                InvestItem item = new InvestItem(obj.getString("id"),
                                        obj.getString("title"),obj.getString("content"),obj.getString("avatar"),
                                        obj.getString("startime"),obj.getString("endtime"),obj.getString("pointc"),
                                        obj.getString("loi"),obj.getString("join_link"));
                                datas.add(item);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                                mRecyclerView.refreshComplete(0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(String status,String error) {
                        mRecyclerView.refreshComplete(0);
                        if(status.equals("empty")){//没有数据

                        }else if(status.equals("relogin")){//登入令牌失效
                            app.setLoginToken("");
                            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));

                        }
                        Toast.makeText(app, error, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        //item点击事件
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), InQuestInfoAct.class);
                InvestItem item = datas.get(position);
                intent.putExtra("title",item.title);
                intent.putExtra("link",item.join_link);
                intent.putExtra("loi",item.loi);
                intent.putExtra("pointc",item.pointc);
                intent.putExtra("startime",item.startime);
                intent.putExtra("endtime",item.endtime);
                intent.putExtra("id",item.id);
                intent.putExtra("content",item.content);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
