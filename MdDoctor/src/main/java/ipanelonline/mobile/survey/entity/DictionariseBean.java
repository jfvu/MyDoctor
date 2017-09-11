package ipanelonline.mobile.survey.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class DictionariseBean {


    /**
     * area : {"country":{"id":"292","name":"台湾"},"province":{"292":[{"id":"293","name":"台灣","pid":"292"}]},"city":{"293":[{"id":"307","name":"台北市","pid":"293"},{"id":"308","name":"基隆市","pid":"293"},"..."]}}
     * education : [{"id":"346","name":"國中"},{"id":"347","name":"高中"},"..."]
     * vocation : [{"id":"296","name":"IT"},{"id":"2467","name":"服務業"},"..."]
     * duty : [{"id":"297","name":"經理"},{"id":"333","name":"一般白領職員"},"..."]
     * monthly_salary : [{"id":"2456","name":"10000 新台幣以下"},{"id":"2473","name":"10,001~20,000 新台幣"},"..."]
     * property : [{"id":"299","name":"私有"},{"id":"2452","name":"合資企業"},"..."]
     * scope : [{"id":"2460","name":"10人及以下"},{"id":"300","name":"11~50 人"},"..."]
     * employee : [{"id":"301","name":"全职"},{"id":"2465","name":"兼职"},"..."]
     * house_monthly_salary : [{"id":"24628","name":"少於 30,000 新台幣"},{"id":"24627","name":"30,001 ~ 42,000 新台幣"},"..."]
     * subscription_ratio : 100:5
     * currency : TWD
     * alipay_support : 1
     * weixin_support : 1
     * paypal_support : 1
     * mobile_recharge_support : 1
     */

    private AreaBean area;
    private String subscription_ratio;
    private String currency;
    private String alipay_support;
    private String weixin_support;
    private String paypal_support;
    private String mobile_recharge_support;
    private List<EducationBean> education;
    private List<VocationBean> vocation;
    private List<DutyBean> duty;
    private List<MonthlySalaryBean> monthly_salary;
    private List<PropertyBean> property;
    private List<ScopeBean> scope;
    private List<EmployeeBean> employee;
    private List<HouseMonthlySalaryBean> house_monthly_salary;


    public static class AreaBean {
        /**
         * country : {"id":"292","name":"台湾"}
         * province : {"292":[{"id":"293","name":"台灣","pid":"292"}]}
         * city : {"293":[{"id":"307","name":"台北市","pid":"293"},{"id":"308","name":"基隆市","pid":"293"},"..."]}
         */

        private CountryBean country;
        private ProvinceBean province;
        private CityBean city;


        public static class CountryBean {
            /**
             * id : 292
             * name : 台湾
             */

            private String id;
            private String name;

        }

        public static class ProvinceBean {
            @SerializedName("292")
            private List<_$292Bean> _$292;

            public List<_$292Bean> get_$292() {
                return _$292;
            }


            public static class _$292Bean {
                /**
                 * id : 293
                 * name : 台灣
                 * pid : 292
                 */

                private String id;
                private String name;
                private String pid;

            }
        }

        public static class CityBean {
            @SerializedName("293")
            private List<_$293Bean> _$293;

            public List<_$293Bean> get_$293() {
                return _$293;
            }

            public void set_$293(List<_$293Bean> _$293) {
                this._$293 = _$293;
            }

            public static class _$293Bean {
                /**
                 * id : 307
                 * name : 台北市
                 * pid : 293
                 */

                private String id;
                private String name;
                private String pid;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }
    }

    public static class EducationBean {
        /**
         * id : 346
         * name : 國中
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class VocationBean {
        /**
         * id : 296
         * name : IT
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DutyBean {
        /**
         * id : 297
         * name : 經理
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MonthlySalaryBean {
        /**
         * id : 2456
         * name : 10000 新台幣以下
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PropertyBean {
        /**
         * id : 299
         * name : 私有
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ScopeBean {
        /**
         * id : 2460
         * name : 10人及以下
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class EmployeeBean {
        /**
         * id : 301
         * name : 全职
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class HouseMonthlySalaryBean {
        /**
         * id : 24628
         * name : 少於 30,000 新台幣
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
