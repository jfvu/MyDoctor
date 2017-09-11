package ipanelonline.mobile.survey.ui.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CountyBean;
import ipanelonline.mobile.survey.entity.NoticeBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.ll_statusbar_activity_message)
    LinearLayout mLlStatusbarActivityMessage;
    @BindView(R.id.img_back_activity_message)
    ImageView mImgBackActivityMessage;
    @BindView(R.id.lv_message_activity_message)
    ListView mLvMessageActivityMessage;
    private ArrayList<NoticeBean> datas = new ArrayList<>();
    private int page = 0;
    private MyLvadapter adapter;

    @Override
    protected void initData() {
        HashMap<String,String> params = new HashMap<>();
        params.put("uid",app.getId());
        params.put("login_token",app.getLoginToken());
        params.put("page",page + "");
        HttpUtil.post(Api.URL_NOTICE, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String id = obj.getString("id");
                        String content = obj.getString("content");
                        String is_read = obj.getString("is_read");
                        String add_time = obj.getString("add_time");
                        NoticeBean notice = new NoticeBean(id,content,is_read,add_time);
                        datas.add(notice);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(String status, String error) {
                showTaost(error);
                if(status.equals("relogin")){//令牌失效
                    startActivity(LoginActivity.class,true);
                }

            }
        });

        mLvMessageActivityMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datas.get(position).is_read = "1";
                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityMessage.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        adapter = new MyLvadapter();
        mLvMessageActivityMessage.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }


    @OnClick(R.id.img_back_activity_message)
    public void onViewClicked() {
        finish();
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    class MyLvadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getBaseContext(), R.layout.item_messagelv, null);
            }

            ImageView img = (ImageView) convertView.findViewById(R.id.img_icon_messagelv);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title_messagelv);
            TextView content = (TextView) convertView.findViewById(R.id.tv_content_messagelv);
            TextView time = (TextView) convertView.findViewById(R.id.tv_time_messagelv);
            NoticeBean bean = datas.get(position);
            title.setText("系统通知");
            img.setImageResource(R.drawable.message_1);
            content.setText(bean.content);
            time.setText(bean.add_time);
            if (bean.is_read.equals("1")) {//已读
                convertView.findViewById(R.id.img_read).setVisibility(View.GONE);
            }else if(bean.is_read.equals("2")){//未读
                convertView.findViewById(R.id.img_read).setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }
}


