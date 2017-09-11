package ipanelonline.mobile.survey.entity;

/**
 * 说明：系统通知
 * 作者：郭富东 on 17/8/9 13:16
 * 邮箱：878749089@qq.com
 */
public class NoticeBean {


    /**
     * id : 1
     * content : 通知内容
     * is_read : // 1已读  2未读
     * add_time : 2017-02-27 15:12:01
     */

    public String id;
    public String content;
    public String is_read;
    public String add_time;

    public NoticeBean(String id, String content, String is_read, String add_time) {
        this.id = id;
        this.content = content;
        this.is_read = is_read;
        this.add_time = add_time;
    }
}
