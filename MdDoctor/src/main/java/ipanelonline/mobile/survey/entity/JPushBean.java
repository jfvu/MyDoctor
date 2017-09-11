package ipanelonline.mobile.survey.entity;

/**
 * Created by Administrator on 2017/8/30.
 */

public class JPushBean {
    private String alert;

    private String title;

    private int builder_id;

    private Extras extras;

    public void setAlert(String alert){
        this.alert = alert;
    }
    public String getAlert(){
        return this.alert;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setBuilder_id(int builder_id){
        this.builder_id = builder_id;
    }
    public int getBuilder_id(){
        return this.builder_id;
    }
    public void setExtras(Extras extras){
        this.extras = extras;
    }
    public Extras getExtras(){
        return this.extras;
    }
    public class Extras {
        private String type;

        private String txt;

        private int msg_id;

        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setTxt(String txt){
            this.txt = txt;
        }
        public String getTxt(){
            return this.txt;
        }
        public void setMsg_id(int msg_id){
            this.msg_id = msg_id;
        }
        public int getMsg_id(){
            return this.msg_id;
        }

    }
}
