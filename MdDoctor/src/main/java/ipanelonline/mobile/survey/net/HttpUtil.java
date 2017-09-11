package ipanelonline.mobile.survey.net;

import android.content.SharedPreferences;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import ipanelonline.mobile.survey.App;


/**
 * 说明：网络请求工具类
 */
public class HttpUtil {

    /**
     * post请求
     * @param url ：请求地址
     * @param tag ：请求标识（用于区分哪里的请求）
     * @param map :请求参数的集合
     * @param listener ：请求回调的接口
     */
    public static void post(String url,Object tag, Map<String,String> map, final OnResultListener listener){
        SharedPreferences sp = App.context.getSharedPreferences("config_lang", App.context.MODE_APPEND);
        String host = sp.getString("host", "");
        String country = sp.getString("country", "");
        String lang = sp.getString("lang", "");
        Log.e("params","url = " + host + url);
        Log.e("params", "country = " + country);
        Log.e("params","lang = " + lang);
        Log.e("params","type = " + App.TYPE);
        Log.e("params","uuid = " + App.uuid);
        Log.e("params","device = " + App.deviceNum);
        PostRequest<String> postRequest = OkGo.<String>post(host + url).tag(tag);
        postRequest.params("device_type", App.TYPE).params("country", country).params("lang",lang)
        .params("uuid",App.uuid == null ? "1234567" : App.uuid).params("device_no",App.deviceNum);
        if(map != null){
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                postRequest.params(key,map.get(key));
                Log.e("params",key + " = " + map.get(key));
            }
        }
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                if(response.isSuccessful()){
                    String json = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String re_st = jsonObject.getString("re_st");
                        String re_info = jsonObject.getString("re_info");
                        if (re_st.equals("success")) {
                            if(listener != null)
                            listener.onSuccess(re_info);
                            Log.e("deal",re_info.toString());
                        }else{
                            if(listener != null)
                            listener.onError(re_st,re_info);
                            Log.e("deal",re_info.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);

                if(listener != null)
                listener.onError("error","访问网络失败");
            }
        });
    }


    public static void postFile(PostRequest<String> postRequest, final OnResultListener listener){
        SharedPreferences sp = App.context.getSharedPreferences("config_lang", App.context.MODE_APPEND);

        String country = sp.getString("country", "");
        String lang = sp.getString("lang", "");



        postRequest.params("device_type",App.TYPE).params("country", country).params("lang",lang)
                .params("uuid",App.uuid == null ? "1234567" : App.uuid).params("device_no",App.deviceNum);

        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e("122","成功");
                if(response.isSuccessful()){
                    String json = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String re_st = jsonObject.getString("re_st");
                        String re_info = jsonObject.getString("re_info");
                        Log.e("122",re_st.toString()+"#####"+re_info.toString());
                        if (re_st.equals("success")) {
                            if(listener != null)
                                listener.onSuccess(re_info);
                        }else{
                            if(listener != null)
                                listener.onError(re_st,re_info);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Response<String> response) {
                Log.e("122",response.toString());
                super.onError(response);
                if(listener != null)
                    listener.onError("error","访问网络失败");
            }
        });
    }

    /**
     * 网络访问回调接口
     */
    public interface OnResultListener{
        /**
         * 获取数据成功
         * @param result ：获取的数据
         */
        void onSuccess(String result);

        /**
         * 获取失败
         * @param error：错误信息
         */
        void onError(String status,String error);
    }


}
