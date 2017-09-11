package ipanelonline.mobile.survey.entity;

import com.google.gson.Gson;

import ipanelonline.mobile.survey.App;

/**
 * 说明：
 * 作者：郭富东 on 17/8/8 09:57
 * 邮箱：878749089@qq.com
 */
public class UserBean {


    /**
     * id : 2072769
     * avatar :
     * nickname :
     * country :
     * province :
     * city :
     * name : test_account
     * birthday :
     * gender :
     * mobile : 15900888888
     * email : tw1@ipanelonline.com
     * address :
     * zip_code :
     * vocation :
     * education :
     * duty :
     * salary :
     * property :
     * scope :
     * employee :
     * house_income :
     * can_exchange : 1
     * is_black_list : 1
     * login_token : 6dace6ba2d0d7c73098d74e21130c69c
     */

    public String id;
    public String avatar;
    public String nickname;
    public String country;
    public String province;
    public String city;
    public String name;
    public String birthday;
    public String gender;
    public String mobile;
    public String email;
    public String address;
    public String zip_code;
    public String vocation;
    public String education;
    public String duty;
    public String salary;
    public String property;
    public String scope;
    public String employee;
    public String house_income;
    public String can_exchange;
    public String is_black_list;
    public String login_token;

    public static UserBean newInstance(String json){
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(json, UserBean.class);
        userBean.vocation = App.getVocationH().get(userBean.vocation);
        userBean.education = App.getEducationH().get(userBean.education);
        userBean.duty = App.getDutyH().get(userBean.duty);
        userBean.salary = App.getMonthlySalary().get(userBean.salary);
        userBean.property = App.getPropertyH().get(userBean.property);
        userBean.scope = App.getScopeH().get(userBean.scope);
        userBean.employee = App.getEmployeeH().get(userBean.employee);
        userBean.house_income = App.getHouseSalaryH().get(userBean.house_income);
        return userBean;
    }

}
