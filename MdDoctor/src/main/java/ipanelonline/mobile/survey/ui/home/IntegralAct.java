package ipanelonline.mobile.survey.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.PointBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.account.AccountActivity;
import ipanelonline.mobile.survey.ui.account.PayWithdrawals;
import ipanelonline.mobile.survey.ui.account.ZfbWithdrawals;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

/**
 * 积分页面
 */
public class IntegralAct extends BaseActivity {


    @BindView(R.id.tv_intagral_jf)
    TextView tvIntagralJf;
    @BindView(R.id.tv_intagral_qd_num)
    TextView tvIntagralQdNum;
    @BindView(R.id.tv_intagral_tj_num)
    TextView tvIntagralTjNum;
    @BindView(R.id.tv_intagral_fl_num)
    TextView tvIntagralFlNum;
    @BindView(R.id.tv_intagral_tx_num)
    TextView tvIntagralTxNum;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    @BindView(R.id.tv_dc)
    TextView tvDcPoint;
    @BindView(R.id.tv_rj)
    TextView tvRjPoint;
    private Intent mIntent;

    @Override
    protected void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        HttpUtil.post(Api.URL_POINT, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.e("json", result);
                PointBean pointBean = PointBean.newInstance(result);
                tvIntagralJf.setText(pointBean.total_point);//总积分
                tvDcPoint.setText(pointBean.survey_point);//调查
                tvIntagralQdNum.setText(pointBean.login_point);//签到
                tvIntagralTjNum.setText(pointBean.inv_point);//推荐
                tvIntagralFlNum.setText(pointBean.commision_point);//返利
                tvIntagralTxNum.setText(pointBean.other);//提现
                tvRjPoint.setText(pointBean.used_point);//日记
            }

            @Override
            public void onError(String status, String error) {
                showTaost(error);
                if (status.equals("relogin")) {//令牌失效
                    startActivity(LoginActivity.class, true);
                }
            }
        });

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral;
    }


    @OnClick({R.id.img_integral_back, R.id.tv_intagral_tx, R.id.llay_integral_dc, R.id.llay_intagral_qd, R.id.llay_intagral_rj, R.id.llay_intagral_tj, R.id.llay_intagral_tx, R.id.llay_intagral_fl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回按钮
                finish();
                break;
            case R.id.tv_intagral_tx://提现按钮
                withdraw();
                //startActivity(new Intent(IntegralAct.this, RecordActivity.class));


                break;
            case R.id.llay_intagral_tx://提现
                //startActivity(new Intent(IntegralAct.this, WithdrawalsAct.class));
                mIntent = new Intent(IntegralAct.this,IntegralDetailActivity.class);
                mIntent.putExtra("IntegralDetailActivity",6);
                startActivity(mIntent);
                break;
            case R.id.llay_integral_dc://调查
                //startActivity(new Intent(IntegralAct.this, InvestigationAct.class));
                mIntent = new Intent(IntegralAct.this,IntegralDetailActivity.class);
                mIntent.putExtra("IntegralDetailActivity",1);
                startActivity(mIntent);
                break;
            case R.id.llay_intagral_qd://签到
                //startActivity(new Intent(IntegralAct.this, SignAct.class));
                mIntent = new Intent(IntegralAct.this,IntegralDetailActivity.class);
                mIntent.putExtra("IntegralDetailActivity",2);
                startActivity(mIntent);
                break;
            case R.id.llay_intagral_rj://日记
                //startActivity(new Intent(IntegralAct.this, DiaryAct.class));
                mIntent = new Intent(IntegralAct.this,IntegralDetailActivity.class);
                mIntent.putExtra("IntegralDetailActivity",4);
                startActivity(mIntent);
                break;
            case R.id.llay_intagral_tj://推荐
                //startActivity(new Intent(IntegralAct.this, ShareActivity.class));
                mIntent = new Intent(IntegralAct.this,IntegralDetailActivity.class);
                mIntent.putExtra("IntegralDetailActivity",3);
                startActivity(mIntent);
                break;
            case R.id.llay_intagral_fl://返利
                mIntent = new Intent(IntegralAct.this,IntegralDetailActivity.class);
                mIntent.putExtra("IntegralDetailActivity",5);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
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
                        Intent intent = new Intent(IntegralAct.this, ZfbWithdrawals.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                    }else if(type.equals("3")){//paypal提现
                        Intent intent = new Intent(IntegralAct.this, PayWithdrawals.class);
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
                    startActivity(new Intent(IntegralAct.this, AccountActivity.class));
                }
            }
        });
    }
}
