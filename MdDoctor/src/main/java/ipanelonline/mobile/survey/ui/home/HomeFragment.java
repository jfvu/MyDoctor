package ipanelonline.mobile.survey.ui.home;


import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.AccountBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.account.PayWithdrawals;
import ipanelonline.mobile.survey.ui.account.ZfbWithdrawals;
import ipanelonline.mobile.survey.ui.base.BaseFragment;

import butterknife.OnClick;

/**
 * 说明:首页
 */
public class HomeFragment extends BaseFragment {


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.llay_tx, R.id.root_task, R.id.root_jf, R.id.root_share, R.id.img_home_tile_rg, R.id.tv_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root_task://任务
                startActivity(new Intent(getActivity(), InvestigationAct.class));
                break;
            case R.id.root_jf://积分
                startActivity(new Intent(getActivity(), IntegralAct.class));
                break;
            case R.id.root_share://分享
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
            case R.id.img_home_tile_rg://通知
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.tv_logo://联系客服
                startActivity(new Intent(getActivity(), CheckingActivity.class));
                break;
            case R.id.llay_tx://提现
                withdraw();
                break;
        }
    }

    private void withdraw() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        HttpUtil.post(Api.URL_GETEXCHANGE, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String type = obj.getString("type");
                    String account = obj.getString("account");
                    String accountName = obj.getString("account_name");
                    if (type.equals("1")) {//支付宝提现
                        Intent intent = new Intent(getActivity(), ZfbWithdrawals.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                    }else if(type.equals("3")){//paypal提现
                        Intent intent = new Intent(getActivity(), PayWithdrawals.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String status, String error) {
                if(status.equals("empty")){
                    Toast.makeText(app, "未绑定账户", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
