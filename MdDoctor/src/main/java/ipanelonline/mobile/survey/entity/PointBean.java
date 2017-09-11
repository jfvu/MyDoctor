package ipanelonline.mobile.survey.entity;


import com.google.gson.Gson;

/**
 * 说明：用户积分
 * 作者：郭富东 on 17/8/8 14:15
 * 邮箱：878749089@qq.com
 */
public class PointBean {


    /**
     * total_point : 1617
     * survey_point : 232
     * login_point : 384
     * inv_point : 0
     * commision_point : 0.0000
     * used_point : 1000
     * other : 1
     */

    public String total_point;
    public String survey_point;
    public String login_point;
    public String inv_point;
    public String commision_point;
    public String used_point;
    public String other;

    public static PointBean newInstance(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,PointBean.class);
    }


}
