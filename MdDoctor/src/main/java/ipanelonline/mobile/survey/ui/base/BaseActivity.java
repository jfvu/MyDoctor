package ipanelonline.mobile.survey.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ipanelonline.mobile.survey.App;


/**
 * 说明：
 * 作者：郭富东 on 17/6/23 11:19
 * 邮箱：878749089@qq.com
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    private Unbinder unbinder;
    public App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        setContentView(getLayoutId());
        app = (App) getApplicationContext();
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 打开应用设置界面
     *
     * @param requestCode 请求码
     * @return
     */
    private boolean openApplicationSettings(int requestCode) {
        try {
            Intent intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + this.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            this.startActivityForResult(intent, requestCode);
            return true;
        } catch (Throwable e) {
        }
        return false;
    }

    protected abstract void initData();

    protected abstract void initView();

    protected void init(Bundle savedInstanceState) {

    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected void startActivity(Class<? extends BaseActivity> clz, boolean isFinish) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
        if (isFinish) finish();
    }

    protected int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void showTaost(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
