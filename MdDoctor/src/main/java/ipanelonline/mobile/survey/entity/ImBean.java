package ipanelonline.mobile.survey.entity;

/**
 * Created by jiaofeng on 2017/9/10.
 */

public class ImBean {
    /*"im_accid": "1617", // IM账号
            "im_token": 232, // IM令牌*/
    public String im_accid;
    public String im_token;

    public ImBean(String im_accid, String im_token) {
        this.im_accid = im_accid;
        this.im_token = im_token;
    }
}
