package ipanelonline.mobile.survey;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.mob.MobSDK;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import ipanelonline.mobile.survey.entity.AreaBean;
import ipanelonline.mobile.survey.entity.CityBean;
import ipanelonline.mobile.survey.entity.UserBean;
import ipanelonline.mobile.survey.utils.AppPhoneMgr;
import okhttp3.OkHttpClient;

/**
 * 说明：
 * 作者：郭富东 on 17/6/12 12:02
 * 邮箱：878749089@qq.com
 */
public class App extends Application {

    public static final String CHINA = "1";
    public static App context;
    private static SharedPreferences sp;
    private static final String ID_QQ = "100371282";
    private static final String ID_WX = "100371282";
    private static final String ID_QZ = "100371282";
    private static final String ID_SINA = "100371282";
    private static final String ID_FACEBOOK = "100371282";
    private static final String ID_TWITTER = "100371282";

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    /**
     * 默认设备类型
     */
    public static String TYPE = "3";
    /**
     * 默认的国家编号
     */
    public static String COUNTRY = null;
    /**
     * 默认的语言编号
     */
    public static String lang = null;
    /**
     * 设备唯一的编号
     */
    public static String uuid;
    /**
     * 设备的型号
     */
    public static String deviceNum = AppPhoneMgr.getSystemModel();
    private static final int IM_APP_ID = 1400038429;


    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initScreenSize();
        context = this;
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        //配置网络请求(Okgo)
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        NineGridView.setImageLoader(new NineGridView.ImageLoader() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                Glide.with(context).load(url)
                        .into(imageView).onLoadStarted(new ColorDrawable(Color.GRAY));
            }

            @Override
            public Bitmap getCacheImage(String url) {
                return null;
            }
        });
        //初始化SDK基本配置
        TIMSdkConfig config = new TIMSdkConfig(IM_APP_ID)
                .enableCrashReport(false)
                .enableLogPrint(true);
        //初始化SDK
        TIMManager.getInstance().init(getApplicationContext(), config);

    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    /**
     * 判断用户是否登入
     *
     * @return
     */
    public boolean isLogin() {
        String token = sp.getString("login_token", "");
        if (TextUtils.isEmpty(token)) {
            return false;
        }
        return true;
    }

    public void loadUserInfo(UserBean userBean) {
        // TODO: 17/8/8 待更新国家标号
        //COUNTRY = userBean.country;
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("id", userBean.id);
        edit.putString("avatar", userBean.avatar);
        edit.putString("nickname", userBean.nickname);
        edit.putString("country", userBean.country);
        edit.putString("province", userBean.province);
        edit.putString("city", userBean.city);
        edit.putString("name", userBean.name);
        edit.putString("birthday", userBean.birthday);
        edit.putString("gender", userBean.gender);
        edit.putString("mobile", userBean.mobile);
        edit.putString("email", userBean.email);
        edit.putString("address", userBean.address);
        edit.putString("zip_code", userBean.zip_code);
        //字典id
        edit.putString("vocation", userBean.vocation);
        edit.putString("education", userBean.education);
        edit.putString("duty", userBean.duty);
        edit.putString("salary", userBean.salary);
        edit.putString("property", userBean.property);
        edit.putString("scope", userBean.scope);
        edit.putString("employee", userBean.employee);
        edit.putString("house_income", userBean.house_income);
        edit.putString("can_exchange", userBean.can_exchange);
        edit.putString("is_black_list", userBean.is_black_list);
        edit.putString("house_income", userBean.house_income);
        edit.putString("login_token", userBean.login_token);
        edit.commit();
        Log.e("token", "save = " + getLoginToken());
    }

    public String getAppId(int type) {
        if (type == 1) {
            return ID_WX;
        } else if (type == 2) {
            return ID_QQ;
        } else if (type == 3) {
            return ID_SINA;
        } else if (type == 6) {
            return ID_TWITTER;
        } else if (type == 7) {
            return ID_FACEBOOK;
        } else {
            return "";
        }
    }

    public void saveFirst() {
        getSharedPreferences("cpuntry", MODE_PRIVATE).edit().putInt("key", 1).commit();
    }

    public int getCountryFirst() {
        return getSharedPreferences("cpuntry", MODE_PRIVATE).getInt("key", 0);
    }

    public String getId() {
        return sp.getString("id", "");
    }

    public void setId(String id) {
        sp.edit().putString("id", id).commit();
    }

    public String getAvatar() {
        return sp.getString("avatar", "");
    }

    public void setAvatar(String avatar) {
        sp.edit().putString("avatar", avatar).commit();
    }

    public String getNickname() {
        return sp.getString("nickname", "");
    }

    public void setNickname(String nickname) {
        sp.edit().putString("nickname", nickname).commit();
    }

    public String getCountry() {
        return sp.getString("country", "");
    }

    public void setCountry(String country) {
        sp.edit().putString("country", country).commit();
    }

    public String getProvince() {
        return sp.getString("province", "");
    }

    public void setProvince(String province) {
        sp.edit().putString("province", province).commit();
    }

    public String getCity() {
        return sp.getString("city", "");
    }

    public void setCity(String city) {
        sp.edit().putString("city", city).commit();
    }

    public String getName() {
        return sp.getString("name", "");
    }

    public void setName(String name) {
        sp.edit().putString("name", name).commit();
    }

    public String getBirthday() {
        return sp.getString("birthday", "");
    }

    public void setBirthday(String birthday) {
        sp.edit().putString("birthday", birthday).commit();
    }

    public String getGender() {
        return sp.getString("gender", "");
    }

    public void setGender(String gender) {
        sp.edit().putString("gender", gender).commit();
    }

    public String getMobile() {
        return sp.getString("mobile", "");
    }

    public void setMobile(String mobile) {
        sp.edit().putString("mobile", mobile).commit();
    }

    public String getEmail() {
        return sp.getString("email", "");
    }

    public void setEmail(String email) {
        sp.edit().putString("email", email).commit();
    }

    public String getAddress() {
        return sp.getString("address", "");
    }

    public void setAddress(String address) {
        sp.edit().putString("address", address).commit();
    }

    public String getZipCode() {
        return sp.getString("zip_code", "");
    }

    public void setZipCode(String zip_code) {
        sp.edit().putString("zip_code", zip_code).commit();
    }

    public String getVocation() {
        return sp.getString("vocation", "");
    }

    public void setVocation(String vocation) {
        sp.edit().putString("vocation", vocation).commit();
    }

    public String getEducation() {
        return sp.getString("education", "");
    }

    public void setEducation(String education) {
        sp.edit().putString("education", education).commit();
    }

    public String getDuty() {
        return sp.getString("duty", "");
    }

    public void setDuty(String duty) {
        sp.edit().putString("duty", duty).commit();
    }

    public String getSalary() {
        return sp.getString("salary", "");
    }

    public void setSalary(String salary) {
        sp.edit().putString("salary", salary).commit();
    }

    public String getProperty() {
        return sp.getString("property", "");
    }

    public void setProperty(String property) {
        sp.edit().putString("property", property).commit();
    }

    public String getScope() {
        return sp.getString("scope", "");
    }

    public void setScope(String scope) {
        sp.edit().putString("scope", scope).commit();
    }

    public String getEmployee() {
        return sp.getString("employee", "");
    }

    public void setEmployee(String employee) {
        sp.edit().putString("employee", employee).commit();
    }

    public String getHouseIncome() {
        return sp.getString("house_income", "");
    }

    public void setHouseIncome(String house_income) {
        sp.edit().putString("house_income", house_income).commit();
    }

    public String getCanExchange() {
        return sp.getString("can_exchange", "");
    }

    public void setCanExchange(String can_exchange) {
        sp.edit().putString("can_exchange", can_exchange).commit();
    }

    public String getIsBlackList() {
        return sp.getString("is_black_list", "");
    }

    public void setIsBlackList(String is_black_list) {
        sp.edit().putString("is_black_list", is_black_list).commit();
    }

    public String getLoginToken() {
        Log.e("token", sp.getString("login_token", ""));
        return sp.getString("login_token", "");
    }

    public void setLoginToken(String login_token) {
        sp.edit().putString("login_token", login_token).commit();
    }


    /** 获取地区数据 */
    public static ArrayList<CityBean> getAreaData() {
        ArrayList<CityBean> datas = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(sp.getString("init_json",""));
            //获取地区数据
            JSONObject area = obj.getJSONObject("area");
            String country_id = area.getJSONObject("country").getString("id");
            JSONObject province = area.getJSONObject("province");//省名字
            JSONObject city = area.getJSONObject("city");//所有城市的市区
            JSONArray ary = province.getJSONArray(country_id);//所有的城市
            //封装城市信息
            for (int i = 0; i < ary.length(); i++) {
                JSONObject cityObj = ary.getJSONObject(i);
                String id = cityObj.getString("id");
                String name = cityObj.getString("name");
                String pid = cityObj.getString("pid");
                CityBean cityBean = new CityBean(id, name, pid);

                JSONArray jsonArray = city.getJSONArray(id);
                ArrayList<AreaBean> areas = new ArrayList<>();
                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    String area_name = jsonArray.getJSONObject(i1).getString("name");
                    String area_id = jsonArray.getJSONObject(i1).getString("id");
                    String area_pid = jsonArray.getJSONObject(i1).getString("pid");
                    AreaBean areaBean = new AreaBean(area_id, area_name, area_pid);
                    areas.add(areaBean);
                }
                cityBean.areas = areas;
                datas.add(cityBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }

    private static ArrayList<CityBean> parserJson(String tag){
        ArrayList<CityBean> datas = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(sp.getString("init_json",""));
            JSONArray education = obj.getJSONArray(tag);
            for (int i = 0; i < education.length(); i++) {
                String name = education.getJSONObject(i).getString("name");
                String id = education.getJSONObject(i).getString("id");
                datas.add(new CityBean(id,name,null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  datas;
    }

    /**
     * 获取教育信息
     * @return
     */
    public static ArrayList<CityBean> getEducationData(){
        return parserJson("education");
    }

    /**
     * 获取行业信息
     * @return
     */
    public static ArrayList<CityBean> getVocationData(){
        return parserJson("vocation");
    }

    /**
     * 获取职位信息
     * @return
     */
    public static ArrayList<CityBean> getDutyData(){
        return parserJson("duty");
    }

    /**
     * 获取月收入信息
     * @return
     */
    public static ArrayList<CityBean> getMonthlySalaryData(){
        return parserJson("monthly_salary");
    }

    /**
     * 获取单位性质信息
     * @return
     */
    public static ArrayList<CityBean> getPropertyData(){
        return parserJson("property");
    }

    /**
     * 获取单位规模信息
     * @return
     */
    public static ArrayList<CityBean> getScopeData(){
        return parserJson("scope");
    }

    /**
     * 获取工作状态信息
     * @return
     */
    public static ArrayList<CityBean> getEmployeeData(){
        return parserJson("employee");
    }

    /**
     * 获取家庭月收入信息
     * @return
     */
    public static ArrayList<CityBean> getHouseSalaryData(){
        return parserJson("house_monthly_salary");
    }

    public static void saveInitData(String json){
        sp.edit().putString("init_json",json).commit();
    }

    private static HashMap<String,String> parser(String tag){
        HashMap<String,String> datas = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(sp.getString("init_json",""));
            JSONArray education = obj.getJSONArray(tag);
            for (int i = 0; i < education.length(); i++) {
                String name = education.getJSONObject(i).getString("name");
                String id = education.getJSONObject(i).getString("id");
                datas.put(id,name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  datas;
    }


    /**
     * 获取教育信息
     * @return
     */
    public static  HashMap<String,String> getEducationH(){
        return parser("education");
    }

    /**
     * 获取行业信息
     * @return
     */
    public static  HashMap<String,String> getVocationH(){
        return parser("vocation");
    }

    /**
     * 获取职位信息
     * @return
     */
    public static  HashMap<String,String> getDutyH(){
        return parser("duty");
    }

    /**
     * 获取月收入信息
     * @return
     */
    public static  HashMap<String,String> getMonthlySalary(){
        return parser("monthly_salary");
    }

    /**
     * 获取单位性质信息
     * @return
     */
    public static  HashMap<String,String> getPropertyH(){
        return parser("property");
    }

    /**
     * 获取单位规模信息
     * @return
     */
    public static  HashMap<String,String> getScopeH(){
        return parser("scope");
    }

    /**
     * 获取工作状态信息
     * @return
     */
    public static  HashMap<String,String> getEmployeeH(){
        return parser("employee");
    }

    /**
     * 获取家庭月收入信息
     * @return
     */
    public static HashMap<String,String> getHouseSalaryH(){
        return parser("house_monthly_salary");
    }

    public void saveLangVersion(String result) {
        sp.edit().putString("lang_version",result).commit();
    }

    public String getLangVersion(){
        return sp.getString("lang_version","");
    }
}
