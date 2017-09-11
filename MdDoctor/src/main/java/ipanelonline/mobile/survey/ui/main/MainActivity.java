package ipanelonline.mobile.survey.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;

import java.lang.reflect.Method;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.home.HomeFragment;
import ipanelonline.mobile.survey.ui.home.SignAct;
import ipanelonline.mobile.survey.ui.journal.BlogFragment;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;
import ipanelonline.mobile.survey.ui.mine.MeFragment;


/**
 * 说明：应用程序主页面
 * 作者：郭富东
 * 时间： 17/6/12 上午10:36
 * 邮箱：878749089@qq.com
 */
public class MainActivity extends BaseActivity {

    private static final String KEY_FRAGMENT_TAG = "fragment_tag";
    private static final String FRAGMENT_TAG_HOME = "fragment_home";
    private static final String FRAGMENT_TAG_SEARCH = "fragment_search";
    private static final String FRAGMENT_TAG_ME = "fragment_me";
    @BindView(R.id.rb_main_home)
    RadioButton rbHome;
    @BindView(R.id.rb_main_search)
    RadioButton rbSearch;
    @BindView(R.id.rb_main_me)
    RadioButton rbMainMe;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    private String[] mFragmentTags = new String[]{FRAGMENT_TAG_HOME, FRAGMENT_TAG_SEARCH, FRAGMENT_TAG_ME};
    private String mFragmentCurrentTag = FRAGMENT_TAG_HOME;
    private MeFragment mMeFragment;
    private HomeFragment mOrderFragment;
    private BlogFragment mQueryFragment;
    private boolean isSign = false;


    @Override
    protected void init(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            restoreFragments();
            mFragmentCurrentTag = savedInstanceState.getString(KEY_FRAGMENT_TAG);
        }
    }

    @Override
    protected void initData() {
        if(isSign){
            return;
        }
        //签到
        HashMap<String,String> params = new HashMap<>();
        params.put("uid",app.getId());
        params.put("login_token",app.getLoginToken());
        HttpUtil.post(Api.URL_SIGNIN, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                isSign = true;
                startActivity(new Intent(MainActivity.this, SignAct.class));

            }

            @Override
            public void onError(String status, String error) {
                if(status.equals("relogin")){//令牌失效
                    startActivity(LoginActivity.class,true);
                }
            }
        });
    }

    @Override
    protected void initView() {

        AndPermission.with(this)
                .requestCode(200)
                .permission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE
                )
                .callback(null)
                .start();
        onTabSelect(mFragmentCurrentTag);
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void onTabSelect(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(manager, transaction);
        if (tag == FRAGMENT_TAG_HOME) {
            selectedFragment(transaction, mOrderFragment, HomeFragment.class, tag);
            rbHome.setChecked(true);
        } else if (tag == FRAGMENT_TAG_SEARCH) {
            selectedFragment(transaction, mQueryFragment, BlogFragment.class, tag);
            rbSearch.setChecked(true);
        } else if (tag == FRAGMENT_TAG_ME) {
            selectedFragment(transaction, mMeFragment, MeFragment.class, tag);
            rbMainMe.setChecked(true);
        }
    }

    private void selectedFragment(FragmentTransaction transaction, Fragment fragment, Class<?> clazz, String tag) {
        if (fragment == null) {
            try {
                Method newInstanceMethod = clazz.getDeclaredMethod("newInstance");
                fragment = (Fragment) newInstanceMethod.invoke(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            transaction.add(R.id.frame_main, fragment, tag);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    /**
     * 先全部隐藏
     *
     * @param fragmentManager
     * @param transaction
     */
    private void hideFragments(FragmentManager fragmentManager, FragmentTransaction transaction) {
        for (int i = 0; i < mFragmentTags.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(mFragmentTags[i]);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
    }

    /**
     * 恢复fragment
     */
    private void restoreFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < mFragmentTags.length; i++) {
            Fragment fragment = manager.findFragmentByTag(mFragmentTags[i]);
            if (fragment == null)
                return;
            if (fragment instanceof MeFragment) {
                mMeFragment = (MeFragment) fragment;
            } else if (fragment instanceof BlogFragment) {
                mQueryFragment = (BlogFragment) fragment;
            } else if (fragment instanceof HomeFragment) {
                mOrderFragment = (HomeFragment) fragment;
            }
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                showTaost("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_FRAGMENT_TAG, mFragmentCurrentTag);
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.rb_main_search, R.id.rb_main_home, R.id.rb_main_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_main_search://查询
                mFragmentCurrentTag = FRAGMENT_TAG_SEARCH;
                break;
            case R.id.rb_main_home://首页
                mFragmentCurrentTag = FRAGMENT_TAG_HOME;
                break;
            case R.id.rb_main_me://我的
                mFragmentCurrentTag = FRAGMENT_TAG_ME;
                break;
        }
        onTabSelect(mFragmentCurrentTag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

}
