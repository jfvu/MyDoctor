package ipanelonline.mobile.survey.entity;

import java.util.ArrayList;

/**
 * Created by jiaofeng on 2017/8/19.
 */

public class CityBean {

    /*
   "id": "280",
                    "name": "厦门",
                    "pid": "216"
    * */
    public String id;
    public String name;
    public String pid;
    public ArrayList<AreaBean> areas;

    public CityBean(String id, String name, String pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }
}
