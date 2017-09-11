package ipanelonline.mobile.survey.entity;

/**
 * Created by Administrator on 2017/8/25.
 */

public class IntergralBean {
    /*"point": 4,
            "reason": "日登录奖励积分",
            "point_change_time": "2013-08-02 17:13:49"*/
    private String point;
    private String reason;
    private String point_change_time;

    public String getPoint() {
        return point;
    }

    public String getReason() {
        return reason;
    }

    public String getPoint_change_time() {
        return point_change_time;
    }

    public IntergralBean(String point, String reason, String point_change_time) {
        this.point = point;
        this.reason = reason;
        this.point_change_time = point_change_time;
    }
}
