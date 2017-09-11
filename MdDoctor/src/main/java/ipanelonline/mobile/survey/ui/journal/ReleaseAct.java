package ipanelonline.mobile.survey.ui.journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;


/**
 * 发布说说页面
 */
public class ReleaseAct extends BaseActivity {


    private static final int IMG_COUNT = 9;
    private static final String IMG_ADD_TAG = "a";
    @BindView(R.id.gridview)
    GridView gridView;
    @BindView(R.id.ed_content)
    EditText mEdContent;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    @BindView(R.id.tv_shownum)
    TextView mTvShownum;
    @BindView(R.id.tv_cancle)
    TextView mTvCancle;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    private GVAdapter adapter;
    private List<String> list;
    private boolean isRun = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        if (list == null) {
            list = new ArrayList<>();
            list.add(IMG_ADD_TAG);
        }
        adapter = new GVAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).equals(IMG_ADD_TAG)) {
                    if (list.size() <= IMG_COUNT) {
                        PictureSelector.create(ReleaseAct.this)
                                .openGallery(PictureMimeType.ofImage())//显示格式，这是只显示图片
                                .maxSelectNum(IMG_COUNT)//最大选择数量
                                .minSelectNum(0)//最小选择数量
                                .selectionMode(PictureConfig.MULTIPLE)//单选or多选
                                .previewImage(false)//对否能预览
                                .compressGrade(Luban.CUSTOM_GEAR)//压缩级别
                                .isCamera(true) //是否显示拍照按钮
                                .enableCrop(false)// 是否裁剪 true or false
                                .compress(true)// 是否压缩 true or false
                                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩
                                .selectionMedia(null)// 是否传入已选图片
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

                    } else
                        showTaost("最多只能选择" + IMG_COUNT + "张照片！");
                }
            }
        });
        mEdContent.addTextChangedListener(mTextWatcher);
    }

    @OnClick({R.id.tv_cancle, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle://取消
                finish();
                break;
            case R.id.tv_send://发送
                if (isRun) {
                    return;
                }
                String content = mEdContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    showTaost("发布的内容不能为空");
                    return;
                }
                isRun = true;
                SharedPreferences sp = App.context.getSharedPreferences("config_lang", App.context.MODE_APPEND);
                String host = sp.getString("host", "");
                PostRequest<String> postRequest = OkGo.<String>post(host + Api.URL_ADDBLOG).tag(this);
                postRequest.params("uid", app.getId());
                postRequest.params("login_token", app.getLoginToken());
                postRequest.params("content", content);
                for (int i = 1; i < list.size(); i++) {
                    String path = list.get(i);
                    String key = "img" + i;
                    File value = new File(path);
                    postRequest.params(key, value);
                }
                HttpUtil.postFile(postRequest, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showTaost(result);
                        setResult(1);
                        finish();
                        isRun = false;
                    }

                    @Override
                    public void onError(String status, String error) {
                        showTaost(error);
                        if (status.equals("relogin")) {//令牌失效
                            //startActivity(new Intent(ReleaseAct.this, LoginActivity.class));
                            finish();
                        }
                        isRun = false;
                    }
                });
                PictureFileUtils.deleteCacheDirFile(this);//清楚图片缓存
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia localMedia : selectList) {
                        list.add(localMedia.getCompressPath());
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplication()).inflate(R.layout.item_release_gride, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String s = list.get(position);
            if (!s.equals(IMG_ADD_TAG)) {
                holder.checkBox.setVisibility(View.VISIBLE);
                Glide.with(ReleaseAct.this).load(s).into(holder.imageView);
            } else {
                holder.checkBox.setVisibility(View.GONE);
                holder.imageView.setImageResource(R.drawable.diary_add);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    refreshAdapter();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }

    }

    private void refreshAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GVAdapter();
        }
        adapter.notifyDataSetChanged();
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

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = mEdContent.getSelectionStart();
            editEnd = mEdContent.getSelectionEnd();
            mTvShownum.setText("您输入了" + temp.length() + "个字符/800");
            if (temp.length() > 800) {

                Toast.makeText(ReleaseAct.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                mEdContent.setText(s);
                mEdContent.setSelection(tempSelection);
                mTvSend.setEnabled(false);
            }else mTvSend.setEnabled(true);
        }
    };
}

