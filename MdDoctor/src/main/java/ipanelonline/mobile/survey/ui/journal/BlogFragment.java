package ipanelonline.mobile.survey.ui.journal;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

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
import ipanelonline.mobile.survey.adapter.DiaryListAdapter;
import ipanelonline.mobile.survey.entity.BlogBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseFragment;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;
import ipanelonline.mobile.survey.utils.TimeUtils;

/**
 * 日志页面
 */
public class BlogFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(R.id.recy_list)
    LRecyclerView recyList;
    @BindView(R.id.refresh)
    BGARefreshLayout refreshLayout;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ArrayList<BlogBean> datas = new ArrayList();
    private int page = 0;
    private SearchFragment searchFragment;
    private int PAGE_SIZE;
    private boolean isRun = false;
    private DiaryListAdapter mDataAdapter;
    private View viewById;

    public static BlogFragment newInstance() {
        return new BlogFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void initView() {
        viewById = getActivity().findViewById(R.id.ll_activity_main);
        searchFragment = searchFragment.newInstance();
        recyList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDataAdapter = new DiaryListAdapter(getActivity(), datas,viewById);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        recyList.setAdapter(mLRecyclerViewAdapter);
        //添加分割线
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(2f)
                .setColorResource(R.color.gray)
                .build();
        recyList.addItemDecoration(divider);
        recyList.setPullRefreshEnabled(false);
        recyList.setLoadMoreEnabled(false);
        refreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(App.context,true);
        // 设置下拉刷新和上拉加载更多的风格
        refreshLayout.setRefreshViewHolder(refreshViewHolder);
        refreshLayout.setIsShowLoadingMoreView(true);

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 0;
        requstData(page, true);
        PAGE_SIZE = 0;
    }

    @Override
    protected void setListaner() {
        super.setListaner();
        //item点击
        mDataAdapter.setOnItemClickListener(new DiaryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(datas.get(position));
                intent.putExtra("json", json);
                startActivity(intent);
            }
        });
        //搜索
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                if (TextUtils.isEmpty(keyword)) return;
                HashMap<String, String> params = new HashMap();
                params.put("uid", app.getId());
                params.put("login_token", app.getLoginToken());
                params.put("key", keyword);
                params.put("page", page + "");
                HttpUtil.post(Api.URL_BLOGSEARCH, BlogFragment.this, params, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        datas.clear();
                        datas.addAll(parserBlogJson(result));
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String status, String error) {
                        Toast.makeText(app, error, Toast.LENGTH_SHORT).show();
                        if (status.equals("relogin")) {//令牌失效
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }

                    }
                });

            }
        });

    }

    /**
     * 获取日志数据
     *
     * @param page    ：页数
     * @param isClear ：是否清空之前的数据
     */
    private void requstData(int page, final boolean isClear) {
        isRun = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        params.put("page", page + "");
        HttpUtil.post(Api.URL_BLOGLIST, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.e("blog",result.toString());
                if (isClear) {
                    datas.clear();
                }
                datas.addAll(parserBlogJson(result));
                mLRecyclerViewAdapter.notifyDataSetChanged();
                refreshLayout.endRefreshing();
                isRun = false;

            }

            @Override
            public void onError(String status, String error) {
                Toast.makeText(app, error, Toast.LENGTH_SHORT).show();
                if (status.equals("relogin")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
                isRun = false;
                refreshLayout.endRefreshing();

            }
        });
    }

    /**
     * 解析日志json数据
     *
     * @param result
     * @return
     */
    private ArrayList<BlogBean> parserBlogJson(String result) {
        ArrayList<BlogBean> datas = null;
        try {
            datas = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                BlogBean blogBean = new BlogBean();
                blogBean.id = obj.getString("id");
                blogBean.creater = obj.getString("creater");
                blogBean.create_time = TimeUtils.format(obj.getString("create_time"));
                blogBean.easy_address = obj.getString("easy_address");
                blogBean.content = obj.getString("content");
                blogBean.praise_num = obj.getString("praise_num");
                blogBean.comment_num = obj.getString("comment_num");
                blogBean.nickname = obj.getString("nickname");
                blogBean.avatar = obj.getString("avatar");
                //Log.e("123",blogBean.avatar.toString());
                blogBean.is_praise = obj.getString("is_praise");
                blogBean.page = page + "";
                if (!obj.isNull("image")) {
                    JSONObject jsonObject = obj.getJSONObject("image");
                    BlogBean.ImageBean imageBean = new BlogBean.ImageBean();
                    if (!jsonObject.isNull("1")) {
                        imageBean.imgUrl1 = jsonObject.getString("1");
                    }
                    if (!jsonObject.isNull("2")) {
                        imageBean.imgUrl2 = jsonObject.getString("2");
                    }
                    if (!jsonObject.isNull("3")) {
                        imageBean.imgUrl3 = jsonObject.getString("3");
                    }
                    if (!jsonObject.isNull("4")) {
                        imageBean.imgUrl4 = jsonObject.getString("4");
                    }
                    if (!jsonObject.isNull("5")) {
                        imageBean.imgUrl5 = jsonObject.getString("5");
                    }
                    if (!jsonObject.isNull("6")) {
                        imageBean.imgUrl6 = jsonObject.getString("6");
                    }
                    if (!jsonObject.isNull("7")) {
                        imageBean.imgUrl7 = jsonObject.getString("7");
                    }
                    if (!jsonObject.isNull("8")) {
                        imageBean.imgUrl8 = jsonObject.getString("8");
                    }
                    if (!jsonObject.isNull("9")) {
                        imageBean.imgUrl9 = jsonObject.getString("9");
                    }
                    blogBean.image = imageBean;
                }
                datas.add(blogBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return datas;


        }
    }


    @OnClick({R.id.llay_search, R.id.img_fb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llay_search://搜索
                searchFragment.show(getActivity().getSupportFragmentManager(), SearchFragment.TAG);
                break;
            case R.id.img_fb://发布
                startActivityForResult(new Intent(getActivity(), ReleaseAct.class), 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            page = 0;
            requstData(page, true);
            PAGE_SIZE = datas.size();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (isRun) {
            return;
        }
        page = 0;
        requstData(page, true);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRun) {
            return false;
        }
        requstData(++page, false);
        return false;
    }
}
