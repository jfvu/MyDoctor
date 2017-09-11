package ipanelonline.mobile.survey.ui.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.FullImageInfo;
import ipanelonline.mobile.survey.entity.ImBean;
import ipanelonline.mobile.survey.entity.ImServiceBean;
import ipanelonline.mobile.survey.entity.MessageInfo;
import ipanelonline.mobile.survey.entity.UserBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.chat.adapter.ChatAdapter;
import ipanelonline.mobile.survey.ui.chat.adapter.CommonFragmentPagerAdapter;
import ipanelonline.mobile.survey.utils.Constants;
import ipanelonline.mobile.survey.utils.GlobalOnItemClickManagerUtils;
import ipanelonline.mobile.survey.utils.MediaManager;
import ipanelonline.mobile.survey.view.im.EmotionInputDetector;
import ipanelonline.mobile.survey.view.im.NoScrollViewPager;


public class ChatActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_chat)
    LinearLayout mLlStatusbarActivityChat;
    @BindView(R.id.img_back_activity_chat)
    ImageView mImgBackActivityChat;
    @BindView(R.id.tv_name_chat)
    TextView mTvNameChat;
    @BindView(R.id.img_recoud_chat)
    ImageView mImgRecoudChat;
    @BindView(R.id.et_input_chat)
    EditText mEtInputChat;
    @BindView(R.id.img_add_chat)
    ImageView mImgAddChat;
    @BindView(R.id.img_send_chat)
    ImageView mImgSendChat;
    @BindView(R.id.chat_list)
    EasyRecyclerView chatList;
    @BindView(R.id.voice_text)
    TextView mRecoundText;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.emotion_layout)
    RelativeLayout emotionLayout;
    private TIMConversation conversation;
    /*回话用户id或用户名*/
    private String peer = "";
    private String userim;
    private String userimtoken;
    private String serviceim;

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;
    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;
    private CommonFragmentPagerAdapter adapter;
    private HashMap<String, String> params1;

    @Override
    protected void initData() {
        initChatUI();



    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        ViewGroup.LayoutParams params = mLlStatusbarActivityChat.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();

        params1 = new HashMap<>();
        SharedPreferences sp = App.context.getSharedPreferences("config_lang", App.context.MODE_APPEND);
        String host = sp.getString("host", "");
        params1.put("country", sp.getString("country", ""));
        params1.put("lang", sp.getString("lang", ""));
        params1.put("login_token", app.getLoginToken());
        SharedPreferences sp1 = getSharedPreferences("userInfo", MODE_APPEND);
        params1.put("uid", sp1.getString("id", ""));
        Log.e("imuser",params1.toString());
        HttpUtil.post(Api.URL_GET_IM, this, params1, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                Log.e("im", result.toString());
                Gson gson = new Gson();
                ImBean imBean = gson.fromJson(result,ImBean.class);
                userim = imBean.im_accid;
                userimtoken = imBean.im_token;
                HttpUtil.post(Api.URL_GET_SERVICE, this, params1, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("im", result.toString());
                        Gson gson = new Gson();
                        ImServiceBean imServiceBean = gson.fromJson(result,ImServiceBean.class);
                        serviceim = imServiceBean.im_accid;
                        // identifier为用户名，userSig 为用户登录凭证
                        TIMManager.getInstance().login(userim, userimtoken, new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                //错误码code和错误描述desc，可用于定位请求失败原因
                                //错误码code列表请参见错误码表
                                Log.e("loginim", "login failed. code: " + code + " errmsg: " + desc);
                            }

                            @Override
                            public void onSuccess() {
                                Log.e("loginim", "login succ");
                                conversation = TIMManager.getInstance().getConversation(
                                        TIMConversationType.C2C,    //会话类型：单聊
                                        serviceim);
                            }
                        });
                    }

                    @Override
                    public void onError(String status, String error) {
                        showTaost(error);
                    }
                });
            }

            @Override
            public void onError(String status, String error) {
                showTaost(error);
            }
        });

        /*//初始化SDK基本配置
        int sdkAppId = 1400038429;
        TIMSdkConfig config = new TIMSdkConfig(sdkAppId)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        //初始化SDK
        TIMManager.getInstance().init(getApplicationContext(), config);*/


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia localMedia : selectList) {
                        String imgPath = localMedia.getCompressPath();
                        sendImage(imgPath);
                    }
                    break;
            }
        }
    }

    /**
     * 发送文本消息
     *
     * @param message
     */
    private void sendChat(String message) {
        TIMMessage msg = new TIMMessage();//构造一条消息
        TIMTextElem elem = new TIMTextElem();//添加文本内容
        elem.setText(message);
        if (msg.addElement(elem) != 0) {//将elem添加到消息
            return;
        }
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                Log.e("im",code+desc);
                Toast.makeText(ChatActivity.this, "消息接收方无效，对方用户不存在", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("im","成功");
            }
        });
    }

    /**
     * 发送图片消息
     *
     * @param imgPath
     */
    private void sendImage(final String imgPath) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setImageUrl(imgPath);
        EventBus.getDefault().post(messageInfo);
        TIMMessage msg = new TIMMessage();
        //添加图片
        TIMImageElem elem = new TIMImageElem();
        elem.setPath(imgPath);
        if (msg.addElement(elem) != 0) {
            return;
        }
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功

            }
        });
    }

    /**
     * 发送语音消息
     *
     * @param filePath
     */
    private void sendSound(String filePath) {
        TIMMessage msg = new TIMMessage();
        //添加语音
        TIMSoundElem elem = new TIMSoundElem();
        elem.setPath(filePath); //填写语音文件路径
        elem.setDuration(60);  //填写语音时长
        if (msg.addElement(elem) != 0) {
            return;
        }
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {//发送消息失败
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
            }
        });
    }

    /**
     * 选择图片
     */
    private void selectImage() {
        PictureSelector.create(ChatActivity.this)
                .openGallery(PictureMimeType.ofImage())//显示格式，这是只显示图片
                .maxSelectNum(1)//最大选择数量
                .minSelectNum(0)//最小选择数量
                .selectionMode(PictureConfig.SINGLE)//单选or多选
                .previewImage(true)//对否能预览
                .compressGrade(Luban.CUSTOM_GEAR)//压缩级别
                .isCamera(true) //是否显示拍照按钮
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩
                .selectionMedia(null)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    private void initChatUI() {
        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(chatList)
                .bindToEditText(mEtInputChat)
                .bindToAddButton(mImgAddChat, new EmotionInputDetector.OnBtnClickListener() {
                    @Override
                    public void onClick(String msg) {
                        selectImage();
                    }

                })
                .bindToSendButton(mImgSendChat, new EmotionInputDetector.OnBtnClickListener() {
                    @Override
                    public void onClick(String msg) {
                        if (TextUtils.isEmpty(msg)) {
                            return;
                        }
                        sendChat(msg);
                    }
                })
                .bindToVoiceButton(mImgRecoudChat)
                .bindToVoiceText(mRecoundText, new EmotionInputDetector.OnRecoundListener() {
                    @Override
                    public void onStop(String filePath) {
                        sendSound(filePath);
                    }
                })
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(mEtInputChat);

        chatAdapter = new ChatAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(chatAdapter);
        chatList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
        LoadData();
    }

    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener = new ChatAdapter.onItemClickListener() {
        @Override
        public void onHeaderClick(int position) {
        }

        @Override
        public void onImageClick(View view, int position) {
            int location[] = new int[2];
            view.getLocationOnScreen(location);
            FullImageInfo fullImageInfo = new FullImageInfo();
            fullImageInfo.setLocationX(location[0]);
            fullImageInfo.setLocationY(location[1]);
            fullImageInfo.setWidth(view.getWidth());
            fullImageInfo.setHeight(view.getHeight());
            fullImageInfo.setImageUrl(messageInfos.get(position).getImageUrl());
            EventBus.getDefault().postSticky(fullImageInfo);
        }

        @Override
        public void onVoiceClick(final ImageView imageView, final int position) {
            if (animView != null) {
                animView.setImageResource(res);
                animView = null;
            }
            switch (messageInfos.get(position).getType()) {
                case 1:
                    animationRes = R.drawable.voice_left;
                    res = R.mipmap.icon_voice_left3;
                    break;
                case 2:
                    animationRes = R.drawable.voice_right;
                    res = R.mipmap.icon_voice_right3;
                    break;
            }
            animView = imageView;
            animView.setImageResource(animationRes);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            MediaManager.playSound(messageInfos.get(position).getFilepath(), new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animView.setImageResource(res);
                }
            });
        }
    };


    /**
     * 构造聊天数据
     */
    private void LoadData() {
        messageInfos = new ArrayList<>();
        chatAdapter.addAll(messageInfos);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        messageInfo.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfos.add(messageInfo);
        chatAdapter.add(messageInfo);
        chatList.scrollToPosition(chatAdapter.getCount() - 1);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                chatAdapter.notifyDataSetChanged();
            }
        }, 2000);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                TIMMessage msg = new TIMMessage();
                for (int i = 0; i < msg.getElementCount(); i++) {
                    TIMElem elem = msg.getElement(i);
                    TIMElemType elemType = elem.getType();
                    Log.e("immessagetype",elemType.name());
                    if (elemType == TIMElemType.Text){
                        MessageInfo message = new MessageInfo();
                        message.setContent(elem.toString());
                        message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                        message.setHeader("http://tupian.enterdesk.com/2014/mxy/11/2/1/12.jpg");
                        messageInfos.add(message);
                        chatAdapter.add(message);
                        chatList.scrollToPosition(chatAdapter.getCount() - 1);
                    }/*else if (elemType == TIMElemType.Image){
                        TIMImageElem e = (TIMImageElem) elem;
                        for(TIMImage image : e.getImageList()) {

                            //获取图片类型, 大小, 宽高
                            final String tag = "im";
                            Log.d(tag, "image type: " + image.getType() +
                                    " image size " + image.getSize() +
                                    " image height " + image.getHeight() +
                                    " image width " + image.getWidth());

                            image.getImage(path, new TIMCallBack() {
                                @Override
                                public void onError(int code, String desc) {//获取图片失败
                                    //错误码code和错误描述desc，可用于定位请求失败原因
                                    //错误码code含义请参见错误码表
                                    Log.d(tag, "getImage failed. code: " + code + " errmsg: " + desc);
                                }

                                @Override
                                public void onSuccess() {//成功，参数为图片数据
                                    //doSomething
                                    Log.d(tag, "getImage success.");
                                }
                            });
                        }
                    }*/else if (elemType == TIMElemType.Sound){

                    }
                }

                /*MessageInfo message = new MessageInfo();
                message.setContent("这是模拟消息回复");
                message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                message.setHeader("http://tupian.enterdesk.com/2014/mxy/11/2/1/12.jpg");
                messageInfos.add(message);
                chatAdapter.add(message);
                chatList.scrollToPosition(chatAdapter.getCount() - 1);*/
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back_activity_chat)
    public void onViewClicked() {
        finish();
    }
}
