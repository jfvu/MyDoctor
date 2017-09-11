package ipanelonline.mobile.survey.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ipanelonline.mobile.survey.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 说明：
 * 作者：郭富东 on 17/6/23 15:48
 * 邮箱：878749089@qq.com
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    protected App app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        BaseActivity activity = (BaseActivity) getActivity();
        app = activity.app;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){//可见
            loadData();
        }
    }

    protected void loadData() {

    }

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        setListaner();
    }

    protected void setListaner() {

    }

    protected abstract void initData();

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
