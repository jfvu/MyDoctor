package ipanelonline.mobile.survey.ui.mine;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.adapter.MyELVadapter;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 帮助页面
 */

public class HelpActivity extends BaseActivity {
    @BindView(R.id.ll_statusbar_activity_help)
    LinearLayout mLlStatusbarActivityHelp;
    @BindView(R.id.img_back_activity_help)
    ImageView mImgBackActivityHelp;
    @BindView(R.id.elv_show_activity_help)
    ExpandableListView mElvShowActivityHelp;
    private ArrayList<String> fatherList;
    private ArrayList<String> childList;
    private MyELVadapter myELVadapter;

    @Override
    protected void initData() {
        HttpUtil.post(Api.URL_HELP, this, null, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.e("json",result);
                try {
                    childList.clear();
                    fatherList.clear();
                    JSONArray ary = new JSONArray(result);
                    for (int i = 0; i < ary.length(); i++) {
                        JSONObject obj = ary.getJSONObject(i);
                        String subject = obj.getString("subject");
                        String content = obj.getString("content");
                        fatherList.add(subject.trim());
                        childList.add(content);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    myELVadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String status, String error) {

            }
        });

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityHelp.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();

        fatherList = new ArrayList<>();
        childList = new ArrayList<>();
        myELVadapter = new MyELVadapter(fatherList,childList,this);
        mElvShowActivityHelp.setAdapter(myELVadapter);
        mElvShowActivityHelp.setGroupIndicator(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }



    @OnClick(R.id.img_back_activity_help)
    public void onViewClicked() {
        finish();
    }
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
