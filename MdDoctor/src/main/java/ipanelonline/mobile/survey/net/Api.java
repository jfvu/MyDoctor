package ipanelonline.mobile.survey.net;

/**
 * 说明：接口地址
 */
public class Api {


    public static String HOST = "";

    /**
     * 地区列表
     *           https://appapi.diaochatong.com:446/
     */
    public  static String URL_AREA = HOST + "base/arealist";

    public  final static String URL_COUNTRY = "https://appapi.ipanelonline.com/base/arealist";
    /**
     * 字典数据
     */
    public static String URL_DICTIONARY = HOST + "base/dictionary";
    /**
     * 帮助列表数据
     */
    public  static String URL_HELP = HOST + "base/help";
    /**
     * 语言包
     */
    public  static String URL_LANG_PACKAGE = HOST + "base/langpackage";
    /**
     * 语言包版本
     */
    public  static String URL_LANG_VERSION = HOST + "base/langversion";
    /**
     * 配置信息
     */
    public  static String URL_INFO = HOST + "base/copyinfomation";
    /**
     * 短信验证
     */
    public  static String URL_SIM = HOST + "base/sendsms";

    /**
     * 注册
     */
    public  static String URL_REGISTER = HOST + "member/register";
    /**
     * 修改密码
     */
    public  static String URL_RESET = HOST + "member/resetpassword";
    /**
     * 修改手机号
     */
    public  static String URL_RESET_MOBILE = HOST + "member/resetmobile";
    /**
     * 意见反馈
     */
    public  static String URL_FREEBACK = HOST + "member/freeback";
    /**
     * 忘记密码
     */
    public  static String URL_FORGET_PWD = HOST + "member/forgetpassword";
    /**
     * 登入
     */
    public  static String URL_LOGIN = HOST + "member/login";
    /**
     * 系统通知
     */
    public  static String URL_NOTICE = HOST + "member/notice";
    /**
     * 提交个人信息
     */
    public  static String URL_UPPROFILE = HOST + "member/upprofile";
    /**
     * 设置系统通知已读
     */
    public  static String URL_SETNOTICE = HOST + "member/setnotice";

    /**
     * 签到
     */
    public  static String URL_SIGNIN = HOST + "member/signin";

    /**
     * 积分
     */
    public  static String URL_POINT = HOST + "member/point";
    /**
     * 积分
     */
    public  static String URL_POINT_DETAIL = HOST + "member/pointdetail";
    /**
     * 设置提现账号
     */
    public  static String URL_SETEXCHANGE = HOST + "member/setexchange";
    /** 获取提现账户 */
    public  static String URL_GETEXCHANGE = HOST + "member/getexchange";


    /**
     * 提现
     */
    public  static String URL_GETCASH = HOST + "member/getcash";

    /** 三方登入验证 */
    public  static String URL_THIRDPARTY_CHECK = HOST + "member/thirdpartycheck";
    /** 三方登入 */
    public  static String URL_THIRDPARTY_LOGIN= HOST + "member/thirdpartylogin";

    /**
     * 调查项目列表
     */
    public  static String URL_INVESTIGATE = HOST + "project/pjlist";
    /**
     * 日志列表
     */
    public  static String URL_BLOGLIST = HOST + "blog/bloglist";
    /**
     * 搜索日志
     */
    public  static String URL_BLOGSEARCH = HOST + "blog/blogsearch";

    /**
     * 发布日志
     */
    public  static String URL_ADDBLOG = HOST + "blog/addblog";
    /**
     * 评论列表
     */
    public  static String URL_COMMENT = HOST + "blog/commentlist";
    /**
     * 发表评论
     */
    public  static String URL_ADD_COMMENT = HOST + "blog/comment";
    /** 点赞 */
    public  static String URL_PRAISE = HOST + "blog/praise";
    /**
     * 用户im
     */
    public  static String URL_GET_IM = HOST + "member/getim";
    /** 服务器im */
    public  static String URL_GET_SERVICE = HOST + "member/getservice";


}
