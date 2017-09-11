package ipanelonline.mobile.survey.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.JPushBean;
import ipanelonline.mobile.survey.ui.home.IntegralAct;
import ipanelonline.mobile.survey.ui.home.InvestigationAct;
import ipanelonline.mobile.survey.ui.home.MessageActivity;

/**
 * Created by jiaofeng on 2017/8/29.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "jg";
    private String type;
    private Intent mIntent;
    private NotificationManager mNotificationManager;
    private Notification myNotification;

    @Override
    public void onReceive(Context context, Intent intent) {
       /* Bundle bundle = intent.getExtras();
        //接受自定义消息
       if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
           String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
           String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
           Log.e("jpush",message);
       }*/
        try {
            Bundle bundle = intent.getExtras();
            Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + bundle.toString());

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                receivingNotification(context,bundle);
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                Gson gson = new Gson();
                JPushBean jPushBean = gson.fromJson(message, JPushBean.class);
                type = jPushBean.getExtras().getType();

                if (type != null){
                    if (type.equals("1")){
                        mIntent = new Intent(context,InvestigationAct.class);
                    }else if (type.equals("2")){
                        mIntent = new Intent(context,IntegralAct.class);
                    }else if (type.equals("3")){
                        //mIntent = new Intent(context,InvestigationAct.class);
                    }else if (type.equals("4")){
                        mIntent = new Intent(context,MessageActivity.class);
                    }
                }

                PendingIntent pi = PendingIntent.getActivity(
                        App.context,
                        100,
                        mIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);


                mNotificationManager = (NotificationManager) App.context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder myBuilder = new Notification.Builder(App.context);
                myBuilder.setContentTitle(jPushBean.getTitle())
                        .setContentText(jPushBean.getExtras().getTxt())
                        .setTicker("您收到新的消息")
                        //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示
                        .setSmallIcon(R.drawable.app_icon)

                        //设置默认声音和震动
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true)//点击后取消
                        .setWhen(System.currentTimeMillis())//设置通知时间
                        //.setPriority(Notification.PRIORITY_HIGH)//高优先级
                        //.setVisibility(Notification.VISIBILITY_PUBLIC)
                        //android5.0加入了一种新的模式Notification的显示等级，共有三种：
                        //VISIBILITY_PUBLIC  只有在没有锁屏时会显示通知
                        //VISIBILITY_PRIVATE 任何情况都会显示通知
                        //VISIBILITY_SECRET  在安全锁和没有锁屏的情况下显示通知
                        .setContentIntent(pi);  //3.关联PendingIntent
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    myNotification = myBuilder.build();
                }
                mNotificationManager.notify(1, myNotification);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
                if (type != null){
                    if (type.equals("1")){
                        mIntent = new Intent(context,InvestigationAct.class);
                    }else if (type.equals("2")){
                        mIntent = new Intent(context,IntegralAct.class);
                    }else if (type.equals("3")){
                        //mIntent = new Intent(context,InvestigationAct.class);
                    }else if (type.equals("4")){
                        mIntent = new Intent(context,MessageActivity.class);
                    }
                }
                //打开自定义的Activity
                //Intent i = new Intent(context, TestActivity.class);
                //i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
               context.startActivity(mIntent);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e){

        }
    }
    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);
    }
}
