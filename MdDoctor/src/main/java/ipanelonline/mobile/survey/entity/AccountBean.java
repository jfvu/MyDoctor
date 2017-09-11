package ipanelonline.mobile.survey.entity;

/**
 * 说明：
 * 作者：郭富东 on 17/8/18 13:50
 * 邮箱：878749089@qq.com
 */
public class AccountBean {


    /**
     * type : 1 // 兑换账号类别 1 支付宝 3 PayPal
     * account : xxxx@163.com
     * account_name : xxxx
     */

    public String type;
    public String account;
    public String account_name;

    public AccountBean(String type, String account, String account_name) {
        this.type = type;
        this.account = account;
        this.account_name = account_name;
    }
}
