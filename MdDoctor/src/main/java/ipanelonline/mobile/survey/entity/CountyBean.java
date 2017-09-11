package ipanelonline.mobile.survey.entity;

/**
 * Created by jiaofeng on 2017/8/17.
 */

public class CountyBean {

    public String name;
    public String lang;
    public String id;


    public CountyBean(String name, String lang) {
        this.name = name;
        this.lang = lang;
    }


    public CountyBean(String name, String lang, String id) {
        this.name = name;
        this.lang = lang;
        this.id = id;
    }
}
