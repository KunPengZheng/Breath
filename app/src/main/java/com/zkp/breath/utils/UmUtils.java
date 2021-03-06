package com.zkp.breath.utils;

import android.app.Notification;
import android.content.Context;
import android.widget.RemoteViews;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.zkp.breath.BuildConfig;
import com.zkp.breath.R;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/**
 * Um友盟配置工具
 */
public class UmUtils {

    static {
        // 当debug模式下开启log
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
    }

    // 初始化友盟统计
    public static void initUmAnalytics(Context context) {
        ExecutorService cachedPool = ThreadUtils.getCachedPool();
        cachedPool.submit(() -> {
            // 初始化SDK
            UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, "765597c10774728c396403cbf461ff6a");
            // 选用AUTO页面采集模式
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
            // 支持在子进程中统计自定义事件
            UMConfigure.setProcessEvent(true);
            // 用于集成测试获取设备信息
            String[] testDeviceInfo = getTestDeviceInfo(context);
            LogUtils.i("UM_设备信息", Arrays.toString(testDeviceInfo));
        });
    }

    // 友盟channel进程初始化推送
    public static void initUmPushOnUmPushProcess(Context context) {
        if (ProcessUtils.getCurrentProcessName().equals("com.zkp.breath:channel")) {
            initUmPush(context);
        }
    }

    // 主进程初始化友盟推送
    public static void initUmPush(Context context) {
        ExecutorService cachedPool = ThreadUtils.getCachedPool();
        cachedPool.submit(() -> {
            // 初始化SDK
            UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, "765597c10774728c396403cbf461ff6a");
            // 推送流程
            PushAgent mPushAgent = PushAgent.getInstance(context);
            // 自定义通知栏是否显示
            mPushAgent.setNotificaitonOnForeground(true);
            // 自定义样式配置
            mPushAgent.setMessageHandler(customNotification());
            //注册推送服务，每次调用register方法都会回调该接口
            mPushAgent.register(new IUmengRegisterCallback() {
                @Override
                public void onSuccess(String deviceToken) {
                    // 注册成功会返回deviceToken，deviceToken是推送消息的唯一标志
                    LogUtils.i("UM_Push", "注册成功：deviceToken：" + deviceToken);
                }

                @Override
                public void onFailure(String s, String s1) {
                    LogUtils.i("UM_Push", "注册失败：" + "s:" + s + ",s1:" + s1);
                }
            });
        });
    }

    private static UmengMessageHandler customNotification() {
        return new UmengMessageHandler() {

            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 21:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                R.layout.view_notification);
                        remoteViews.setTextViewText(R.id.notification_title, msg.title);
                        remoteViews.setTextViewText(R.id.notification_text, msg.text);
                        remoteViews.setImageViewResource(R.id.notification_large_icon, R.drawable.shape_bg_rcv_item);
                        remoteViews.setImageViewResource(R.id.notification_small_icon, R.drawable.shape_bg_rcv_item);

                        builder.setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);
                        return builder.build();

                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
    }

    private static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
            //
        }
        return deviceInfo;
    }
}
