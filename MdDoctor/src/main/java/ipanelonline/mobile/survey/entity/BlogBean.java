package ipanelonline.mobile.survey.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.lzy.ninegrid.ImageInfo;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * 说明：日志信息
 * 作者：郭富东 on 17/8/8 15:30
 * 邮箱：878749089@qq.com
 */
public class BlogBean {


    /**
     * id : 538
     * creater : 2072769
     * image : {"1":"/uploads/members/2017/07/27/d27b881bb1abc42657219d5e65d4bb05.jpg"}
     * easy_address : 中國-上海市-上海
     * content : is test content
     * praise_num : 0
     * comment_num : 0
     * nickname : testaccount
     * avatar : http: //appapi.ipanelonline.com/uploads/panel/2015/02/17/523ace69e1824036ff972ac16ce98476.jpg
     */

    public String id;
    public String creater;
    public String create_time;
    public ImageBean image;
    public String easy_address;
    public String content;
    public String praise_num;
    public String comment_num;
    public String nickname;
    public String avatar;
    public String page;
    public String is_praise;

    public static class ImageBean {
        /**
         * 1 : /uploads/members/2017/07/27/d27b881bb1abc42657219d5e65d4bb05.jpg
         */

        @SerializedName("1")
        public String imgUrl1;
        @SerializedName("2")
        public String imgUrl2;
        @SerializedName("3")
        public String imgUrl3;
        @SerializedName("4")
        public String imgUrl4;
        @SerializedName("5")
        public String imgUrl5;
        @SerializedName("6")
        public String imgUrl6;
        @SerializedName("7")
        public String imgUrl7;
        @SerializedName("8")
        public String imgUrl8;
        @SerializedName("9")
        public String imgUrl9;

    }

    public ArrayList<ImageInfo> getImageData() {
        if(image == null){
            return null;
        }
        ArrayList<ImageInfo> list = new ArrayList<>();
        if (!TextUtils.isEmpty(image.imgUrl1)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl1);
            e.setThumbnailUrl(image.imgUrl1);
            list.add(e);
        }else {
            return null;
        }
        if (!TextUtils.isEmpty(image.imgUrl2)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl2);
            e.setThumbnailUrl(image.imgUrl2);
            list.add(e);
        }else{
            return list;
        }
        if (!TextUtils.isEmpty(image.imgUrl3)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl3);
            e.setThumbnailUrl(image.imgUrl3);
            list.add(e);
        }else{
            return list;
        }
        if (!TextUtils.isEmpty(image.imgUrl4)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl4);
            e.setThumbnailUrl(image.imgUrl4);
            list.add(e);
        }else{
            return list;
        }
        if (!TextUtils.isEmpty(image.imgUrl5)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl5);
            e.setThumbnailUrl(image.imgUrl5);
            list.add(e);
        }else{
            return list;
        }
        if (!TextUtils.isEmpty(image.imgUrl6)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl6);
            e.setThumbnailUrl(image.imgUrl6);
            list.add(e);
        }else{
            return list;
        }
        if (!TextUtils.isEmpty(image.imgUrl7)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl7);
            e.setThumbnailUrl(image.imgUrl7);
            list.add(e);
        }else{
            return list;
        }
        if (!TextUtils.isEmpty(image.imgUrl8)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl8);
            e.setThumbnailUrl(image.imgUrl8);
            list.add(e);
        }else{
            return list;
        }
        if (!TextUtils.isEmpty(image.imgUrl9)) {
            ImageInfo e = new ImageInfo();
            e.setBigImageUrl(image.imgUrl9);
            e.setThumbnailUrl(image.imgUrl9);
            list.add(e);
        }
        return list;
    }


}
