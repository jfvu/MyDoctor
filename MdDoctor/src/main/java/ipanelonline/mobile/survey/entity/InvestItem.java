package ipanelonline.mobile.survey.entity;

/**
 * 说明：
 * 作者：郭富东 on 17/8/8 11:06
 * 邮箱：878749089@qq.com
 */
public class InvestItem {


    /**
     * id : 19638
     * title : 關於P70019638的調查
     * content : 你好,你即將進入調查問卷,在回答問卷之前,為保證你能夠順利獲得調查的獎勵積分,請認真閱讀所有題目,根據實際情況回答整份問卷,調查結束後我們會對數據進行核對,如發現以下情況你將無法得到調查的獎勵積分.例如:（1）問答問卷所用時間過短.（2）選擇題隨意選擇（3）主觀題輸入的內容與所問的問題不符合.（4）填寫個人信息不真實者.如在數據審核時發現以上情況,回答的問卷將被認為是無效問卷,你將不會得到調查的獎勵積分.
     * avatar : http://appapi.ipanelonline.com/uploads/management/2017/07/05/8b693702213a90ea1b78214aec13cad4.jpg
     * startime : 2017-06-30 00:00:00
     * endtime : 2017-08-29 00:00:00
     * pointc : 750
     * loi : 20
     * join_link : https://appapi.ipanelonline.com/project/joinproject/2072769/14e15c930ccda26b65ae5cc89188a32d/19638
     */

    public String id;
    public String title;
    public String content;
    public String avatar;
    public String startime;
    public String endtime;
    public String pointc;
    public String loi;
    public String join_link;

    public InvestItem(String id, String title, String content, String avatar, String startime, String endtime,
                      String pointc, String loi, String join_link) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.avatar = avatar;
        this.startime = startime;
        this.endtime = endtime;
        this.pointc = pointc;
        this.loi = loi;
        this.join_link = join_link;
    }
}
