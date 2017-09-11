package ipanelonline.mobile.survey.ui.mine;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CityBean;
import ipanelonline.mobile.survey.entity.PersonalData;
import ipanelonline.mobile.survey.entity.UserBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.view.CircleImageView;


/**
 * 个人资料页面
 */

public class PersonalDataActivity extends BaseActivity {

    @BindView(R.id.ll_statusbar_activity_personaldata)
    LinearLayout mLlStatusbarActivityPersonaldata;
    @BindView(R.id.img_return_personaldata)
    ImageView mImgReturnPersonaldata;
    @BindView(R.id.rl_title_personaldata)
    RelativeLayout mRlTitlePersonaldata;
    @BindView(R.id.img_head_personaldata)
    CircleImageView mImgHeadPersonaldata;
    @BindView(R.id.rl_head_personaldata)
    RelativeLayout mRlHeadPersonaldata;
    @BindView(R.id.tv_nickname_personaldata)
    EditText mTvNicknamePersonaldata;
    @BindView(R.id.rl_nickname_personaldata)
    RelativeLayout mRlNicknamePersonaldata;
    @BindView(R.id.tv_area_personaldata)
    EditText mTvAreaPersonaldata;
    @BindView(R.id.rl_area_personaldata)
    RelativeLayout mRlAreaPersonaldata;
    @BindView(R.id.tv_name_personaldata)
    EditText mTvNamePersonaldata;
    @BindView(R.id.rl_name_personaldata)
    RelativeLayout mRlNamePersonaldata;
    @BindView(R.id.tv_birthday_personaldata)
    EditText mTvBirthdayPersonaldata;
    @BindView(R.id.rl_birthday_personaldata)
    RelativeLayout mRlBirthdayPersonaldata;
    @BindView(R.id.tv_sex_personaldata)
    TextView mTvSexPersonaldata;
    @BindView(R.id.rl_sex_personaldata)
    RelativeLayout mRlSexPersonaldata;
    @BindView(R.id.tv_phone_personaldata)
    EditText mTvPhonePersonaldata;
    @BindView(R.id.rl_phone_personaldata)
    RelativeLayout mRlPhonePersonaldata;
    @BindView(R.id.tv_mail_personaldata)
    EditText mTvMailPersonaldata;
    @BindView(R.id.rl_mail_personaldata)
    RelativeLayout mRlMailPersonaldata;
    @BindView(R.id.tv_address_personaldata)
    EditText mTvAddressPersonaldata;
    @BindView(R.id.rl_address_personaldata)
    RelativeLayout mRlAddressPersonaldata;
    @BindView(R.id.tv_postcode_personaldata)
    EditText mTvPostcodePersonaldata;
    @BindView(R.id.rl_postcode_personaldata)
    RelativeLayout mRlPostcodePersonaldata;
    @BindView(R.id.tv_industry_personaldata)
    TextView mTvIndustryPersonaldata;
    @BindView(R.id.rl_industry_personaldata)
    RelativeLayout mRlIndustryPersonaldata;
    @BindView(R.id.tv_education_personaldata)
    TextView mTvEducationPersonaldata;
    @BindView(R.id.rl_education_personaldata)
    RelativeLayout mRlEducationPersonaldata;
    @BindView(R.id.tv_position_personaldata)
    TextView mTvPositionPersonaldata;
    @BindView(R.id.rl_position_personaldata)
    RelativeLayout mRlPositionPersonaldata;
    @BindView(R.id.tv_salary_personaldata)
    TextView mTvSalaryPersonaldata;
    @BindView(R.id.rl_salary_personaldata)
    RelativeLayout mRlSalaryPersonaldata;
    @BindView(R.id.tv_nature_personaldata)
    TextView mTvNaturePersonaldata;
    @BindView(R.id.rl_nature_personaldata)
    RelativeLayout mRlNaturePersonaldata;
    @BindView(R.id.tv_scale_personaldata)
    TextView mTvScalePersonaldata;
    @BindView(R.id.rl_scale_personaldata)
    RelativeLayout mRlScalePersonaldata;
    @BindView(R.id.tv_status_personaldata)
    TextView mTvStatusPersonaldata;
    @BindView(R.id.rl_status_personaldata)
    RelativeLayout mRlStatusPersonaldata;
    @BindView(R.id.tv_income_personaldata)
    TextView mTvIncomePersonaldata;
    @BindView(R.id.rl_income_personaldata)
    RelativeLayout mRlIncomePersonaldata;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.tv_location_personaldata)
    TextView mTvLocationPersonaldata;
    @BindView(R.id.rl_location_personaldata)
    RelativeLayout mRlLocationPersonaldata;

    private PersonalData mPersonalData;
    private int mYear;
    private int mMonth;
    private int mDay;
    private boolean isOK = false;
    private boolean mFlag = true;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    String imagePath;
    private String actionUrl = "http://user.ucplat.zhangmingyue.com/upload";
    private String newName = "headimg.jpg";
    FileInputStream fStream;
    private Intent mIntent;
    private final static int REQUEST_CODE = 1;
    private ArrayList<CityBean> mProvinces;
    private String mData;
    private String mHeaderPath;
    private String country_id;
    private LoadingDailog dialog;

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("config", MODE_APPEND);
        if (sp.getBoolean("isSet", false)) {
            mTvLocationPersonaldata.setText(sp.getString("data", "请选择"));

        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("config", MODE_APPEND);
        mTvLocationPersonaldata.setText(sp.getString("data", "请选择"));
    }

    @Override
    protected void initView() {
        mProvinces = App.getAreaData();
        ViewGroup.LayoutParams params = mLlStatusbarActivityPersonaldata.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        //mButSubmitPersonaldata.setVisibility(View.GONE);
        SharedPreferences sp = getSharedPreferences("config", MODE_APPEND);
        mPersonalData = new PersonalData(
                app.getAvatar(),
                app.getNickname(),
                sp.getString("data", ""),
                app.getName(),
                app.getBirthday(),
                (app.getGender().equals("1") ? "男" : "女"),
                app.getMobile(),
                app.getEmail(),
                app.getAddress(),
                app.getZipCode(),
                app.getVocation(),
                app.getEducation(),
                app.getDuty(),
                app.getSalary(),
                app.getProperty(),
                app.getScope(),
                app.getEmployee(),
                app.getHouseIncome());
        setTextView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personaldata;
    }

    @OnClick({R.id.rl_location_personaldata, R.id.img_return_personaldata, R.id.img_head_personaldata, R.id.rl_nickname_personaldata, R.id.rl_area_personaldata, R.id.rl_name_personaldata, R.id.rl_birthday_personaldata, R.id.rl_sex_personaldata, R.id.rl_phone_personaldata, R.id.rl_mail_personaldata, R.id.rl_address_personaldata, R.id.rl_postcode_personaldata, R.id.rl_industry_personaldata, R.id.rl_education_personaldata, R.id.rl_position_personaldata, R.id.rl_salary_personaldata, R.id.rl_nature_personaldata, R.id.rl_scale_personaldata, R.id.rl_status_personaldata, R.id.rl_income_personaldata, R.id.tv_save})
    public void onViewClicked(View view) {
        Gson gson = new Gson();
        switch (view.getId()) {
            case R.id.img_return_personaldata:
                finish();
                break;
            case R.id.img_head_personaldata://点击头像
                setImageHeader();
                break;
            case R.id.rl_location_personaldata:
                mIntent = new Intent();
                mIntent.setClass(this, Step1Activity.class);
                mIntent.putExtra("title", "选择地区");
                mData = gson.toJson(mProvinces, new TypeToken<ArrayList<CityBean>>() {
                }.getType());
                mIntent.putExtra("data", mData);
                startActivityForResult(mIntent, REQUEST_CODE);
                break;
            case R.id.rl_nickname_personaldata:
                mTvNicknamePersonaldata.setEnabled(true);
                break;
            case R.id.rl_area_personaldata:
                mIntent = new Intent(this, ChooseCityActivity.class);

                startActivity(mIntent);
                break;
            case R.id.rl_name_personaldata:
                break;
            case R.id.rl_birthday_personaldata:
                Calendar calendar = Calendar.getInstance();
                Date myDate = new Date();
                calendar.setTime(myDate);
                if (mPersonalData.getBirthday().equals("") || mPersonalData.getBirthday().split("-")[0].equals("0000")) {
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get(Calendar.MONTH);
                    mDay = calendar.get(Calendar.DAY_OF_MONTH);
                } else {
                    mYear = Integer.parseInt(mPersonalData.getBirthday().split("-")[0]);
                    mMonth = Integer.parseInt(mPersonalData.getBirthday().split("-")[1]);
                    mDay = Integer.parseInt(mPersonalData.getBirthday().split("-")[2]);
                }
                Log.e("123", "nian:" + mYear + "yue:" + mMonth + "ri:" + mDay + mPersonalData.getBirthday().toString());
                new DatePickerDialog(PersonalDataActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.rl_sex_personaldata:
                mIntent = new Intent();
                mIntent.setClass(this, SexActivity.class);
                startActivityForResult(mIntent, REQUEST_CODE);
                break;
            case R.id.rl_phone_personaldata:
                break;
            case R.id.rl_mail_personaldata:
                break;
            case R.id.rl_address_personaldata:
                break;
            case R.id.rl_postcode_personaldata:
                break;
            //行业
            case R.id.rl_industry_personaldata:
                selectData(gson, "行业", 0x10);
                break;
            //教育
            case R.id.rl_education_personaldata:
                selectData(gson, "教育", 0x11);
                break;
            //职位
            case R.id.rl_position_personaldata:
                selectData(gson, "职位", 0x12);
                break;
            //
            case R.id.rl_salary_personaldata:
                selectData(gson, "工资", 0x13);
                break;
            case R.id.rl_nature_personaldata:
                selectData(gson, "单位性质", 0x14);
                break;
            case R.id.rl_scale_personaldata:
                selectData(gson, "单位规模", 0x15);
                break;
            case R.id.rl_status_personaldata:
                selectData(gson, "工作状态", 0x16);
                break;
            case R.id.rl_income_personaldata:
                selectData(gson, "家庭收入", 0x17);
                break;
            case R.id.tv_save:
                final String a, b;
                LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                        .setMessage("加载中...")
                        .setCancelable(true)
                        .setCancelOutside(true);
                dialog =loadBuilder.create();
                dialog.show();
                if (mTvLocationPersonaldata.getText().toString().trim().equals("请选择")) {
                    a = "";
                    b = "";
                } else {
                    SharedPreferences sp = getSharedPreferences("config", MODE_APPEND);

                    a = sp.getString("pid", "");
                    b = sp.getString("id", "");


                }


                SharedPreferences sp = App.context.getSharedPreferences("config_lang", App.context.MODE_APPEND);
                String host = sp.getString("host", "");
                PostRequest<String> postRequest = OkGo.<String>post(host + Api.URL_UPPROFILE).tag(this);
                String sex = mTvSexPersonaldata.getText().toString().trim() + "";
                if (sex.equals("男")) {
                    postRequest.params("gender", 1);
                } else if (sex.equals("女")) {
                    postRequest.params("gender", 2);
                }
                if (mHeaderPath != null) {
                    postRequest.params("avatar", new File(mHeaderPath));
                }
                SharedPreferences sp1 = getSharedPreferences("userInfo", MODE_APPEND);
                postRequest.params("uid", sp1.getString("id", ""));
                if (mHeaderPath == null) {
                    postRequest.params("avatar", "");
                } else {
                    postRequest.params("avatar", new File(mHeaderPath));
                }

                postRequest.params("lang", sp.getString("lang", ""));
                postRequest.params("country", sp.getString("country", ""));
                postRequest.params("login_token", app.getLoginToken());
                postRequest.params("nickname", mTvNicknamePersonaldata.getText().toString().trim() + "");
                postRequest.params("province", a);
                postRequest.params("city", b);
                postRequest.params("mobile", mTvPhonePersonaldata.getText().toString().trim() + "");
                postRequest.params("name", mTvNamePersonaldata.getText().toString().trim() + "");
                if (mTvBirthdayPersonaldata.getText().toString().trim().equals("0000-00-00")) {
                    postRequest.params("birthday", "");
                } else {
                    postRequest.params("birthday", mTvBirthdayPersonaldata.getText().toString().trim() + "");
                }

                postRequest.params("email", mTvMailPersonaldata.getText().toString().trim() + "");
                postRequest.params("address", mTvAddressPersonaldata.getText().toString().trim() + "");


                postRequest.params("zip_code", mTvPostcodePersonaldata.getText().toString().trim() + "");

                postRequest.params("vocation", (String) mTvIndustryPersonaldata.getTag());
                postRequest.params("education", (String) mTvEducationPersonaldata.getTag());
                postRequest.params("duty", (String) mTvPositionPersonaldata.getTag());
                postRequest.params("salary", (String) mTvSalaryPersonaldata.getTag());
                postRequest.params("property", (String) mTvNaturePersonaldata.getTag());
                postRequest.params("scope", (String) mTvScalePersonaldata.getTag());
                postRequest.params("employee", (String) mTvStatusPersonaldata.getTag());
                postRequest.params("house_income", (String) mTvIncomePersonaldata.getTag());
                HttpUtil.postFile(postRequest, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showTaost("保存成功");
                        UserBean userBean = UserBean.newInstance(result);
                        app.loadUserInfo(userBean);
                        SharedPreferences sp = getSharedPreferences("config", MODE_APPEND);
                        String area = mTvLocationPersonaldata.getText().toString().trim();
                        sp.edit().putString("data",area).commit();
                        dialog.cancel();
                    }

                    @Override
                    public void onError(String status, String error) {
                        dialog.cancel();
                        showTaost(error);
                        if (status.equals("relogin")) {//令牌失效
                            startActivity(LoginActivity.class, true);
                        }
                    }
                });
                break;
        }
    }

    /**
     * 设置头像
     */
    private void setImageHeader() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//显示格式，这是只显示图片
                .maxSelectNum(1)//最大选择数量
                .minSelectNum(0)//最小选择数量
                .withAspectRatio(1,1)
                .selectionMode(PictureConfig.SINGLE)//单选or多选
                .previewImage(false)//对否能预览
                .compressGrade(Luban.CUSTOM_GEAR)//压缩级别
                .isCamera(true) //是否显示拍照按钮
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩
                .selectionMedia(null)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);

    }

    private void selectData(Gson gson, String title, int code) {
        mIntent = new Intent(this, Step1Activity.class);
        mIntent.putExtra("title", title);
        startActivityForResult(mIntent, code);
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

    private void setTextView() {
        Glide.with(this).load(mPersonalData.getHead()).into(mImgHeadPersonaldata);
        mTvNicknamePersonaldata.setText(mPersonalData.getNickname());
        mTvAreaPersonaldata.setText(mPersonalData.getArea());
        mTvNamePersonaldata.setText(mPersonalData.getName());
        mTvBirthdayPersonaldata.setText(mPersonalData.getBirthday());
        mTvSexPersonaldata.setText(mPersonalData.getSex());
        mTvPhonePersonaldata.setText(mPersonalData.getPhone());
        mTvMailPersonaldata.setText(mPersonalData.getMail());
        mTvAddressPersonaldata.setText(mPersonalData.getAddress());
        mTvPostcodePersonaldata.setText(mPersonalData.getPostcode());
        mTvIndustryPersonaldata.setText(mPersonalData.getIndustry());
        mTvEducationPersonaldata.setText(mPersonalData.getEducation());
        mTvPositionPersonaldata.setText(mPersonalData.getPosition());
        mTvSalaryPersonaldata.setText(mPersonalData.getSalary());
        mTvNaturePersonaldata.setText(mPersonalData.getNature());
        mTvScalePersonaldata.setText(mPersonalData.getScale());
        mTvStatusPersonaldata.setText(mPersonalData.getStatus());
        mTvIncomePersonaldata.setText(mPersonalData.getIncome());
        mTvLocationPersonaldata.setText(mPersonalData.getArea());
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            }
            if (days.equals(mPersonalData.getBirthday())) {

            } else {
                mPersonalData.setBirthday(days);
                //setTextView();
                mTvBirthdayPersonaldata.setText(mPersonalData.getBirthday());
                mFlag = false;
                mImgReturnPersonaldata.setVisibility(View.GONE);
                //mButSubmitPersonaldata.setVisibility(View.VISIBLE);
            }


        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia localMedia = selectList.get(0);
                    if (localMedia != null) {
                        mHeaderPath = null;
                        if (localMedia.isCut()) {
                            mHeaderPath = localMedia.getCutPath();
                        } else {
                            mHeaderPath = localMedia.getCompressPath();
                        }
                        Glide.with(this).load(mHeaderPath).into(mImgHeadPersonaldata);
                    }
                    break;
            }
        }
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Step1Activity.RESULT_CODE) {
            }
            if (resultCode == SexActivity.RESULT_CODE) {
                mTvSexPersonaldata.setText(data.getStringExtra("PersonalDataActivity"));
            }
        }
        //
        if (data == null) return;
        String text = data.getStringExtra("data");
        String id = data.getStringExtra("id");
        if (requestCode == 0x10) {//行业
            mTvIndustryPersonaldata.setText(text);
            mTvIndustryPersonaldata.setTag(id);
        } else if (requestCode == 0x11) {//教育
            mTvEducationPersonaldata.setText(text);
            mTvEducationPersonaldata.setTag(id);
        } else if (requestCode == 0x12) {//职位
            mTvPositionPersonaldata.setText(text);
            mTvPositionPersonaldata.setTag(id);
        } else if (requestCode == 0x13) {//月薪
            mTvSalaryPersonaldata.setText(text);
            mTvSalaryPersonaldata.setTag(id);
        } else if (requestCode == 0x14) {//性质
            mTvNaturePersonaldata.setText(text);
            mTvNaturePersonaldata.setTag(id);
        } else if (requestCode == 0x15) {//规模
            mTvScalePersonaldata.setText(text);
            mTvScalePersonaldata.setTag(id);
        } else if (requestCode == 0x16) {//工作状态
            mTvStatusPersonaldata.setText(text);
            mTvStatusPersonaldata.setTag(id);
        } else if (requestCode == 0x17) {//家庭收入
            mTvIncomePersonaldata.setText(text);
            mTvIncomePersonaldata.setTag(id);
        }
    }


}
