package com.veio.gx;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class pushtoSingle {

	public static void main(String[] args) throws Exception {
		String appId = "WDRtfrJBuS8vdjf1UHmAS9";
		String appkey = "9bNpVNvVrX63nA7AgiPeiA";
		String CID = "58f10fee03013231fd7386cf6323a686";
		String master = "MMhy18HpDg82FPtFsy9qm";

		String host = "http://192.168.10.61/apiex.htm";
		IGtPush push = new IGtPush(host, appkey, master);

		TransmissionTemplate template = TransmissionTemplateDemo(appId, appkey);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(2592000000L);
		message.setData(template);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(CID);

		try {
			for (int i = 0; i < 10; i++) {
				long start = System.currentTimeMillis();
				IPushResult ret = push.pushMessageToSingle(message, target);
				System.out.println("正常：" + ret.getResponse().toString() + ","
						+ (System.currentTimeMillis() - start));
			}

		} catch (RequestException e) {
			String requstId = e.getRequestId();
			IPushResult ret = push.pushMessageToSingle(message, target,
					requstId);
			System.out.println("异常：" + ret.getResponse().toString());
		}

		System.exit(0);
	}

	public static NotificationTemplate notificationTemplateDemo(String appId,
																String appkey) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle("ff");
		template.setText("请输入通知栏ff内容");
		// 配置通知栏图标
		//template.setLogo("icon.png");
		// 配置通知栏网络图标
		//template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
//		template.setTransmissionType(1);
//		template.setTransmissionContent("{\"postContext\":\"【3.15】中国经济假在哪？解析老外最担心的中国经济八大问题！！！\",\"nickName\":\"【3.15】中国经济假在哪？解析老外最担心的中国经济八大问题！！！\",\"rid\":\"29887\",\"code\":\"1003\"}");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		//template.setNotifyStyle(0);
		return template;
	}

	public static LinkTemplate linkTemplateDemo(String appId, String appKey) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setTitle("请输入通知栏标题");
		template.setText("请输入通知栏内容");
		// 配置通知栏图标
		template.setLogo("icon.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 设置打开的网址地址
		template.setUrl("http://www.getui.com");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	public static NotyPopLoadTemplate notyPopLoadTemplateDemo(String appId,
															  String appKey) {
		NotyPopLoadTemplate template = new NotyPopLoadTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setNotyTitle("请输入通知栏标题");
		template.setNotyContent("请输入通知栏内容");
		// 配置通知栏图标
		template.setNotyIcon("icon.png");
		// 设置通知是否响铃，震动，或者可清除
		template.setBelled(true);
		template.setVibrationed(true);
		template.setCleared(true);

		// 设置弹框标题与内容
		template.setPopTitle("弹框标题");
		template.setPopContent("弹框内容");
		// 设置弹框显示的图片
		template.setPopImage("");
		template.setPopButton1("下载");
		template.setPopButton2("取消");

		// 设置下载标题
		template.setLoadTitle("下载标题");
		template.setLoadIcon("file://icon.png");
		// 设置下载地址
		template.setLoadUrl("http://gdown.baidu.com/data/wisegame/80bab73f82cc29bf/shoujibaidu_16788496.apk");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	private static TransmissionTemplate TransmissionTemplateDemo(String appId, String appKey) throws Exception {
		TransmissionTemplate t = new TransmissionTemplate();
		t.setAppId(appId);
		t.setAppkey(appKey);
		t.setTransmissionContent("{\"title\":\"\",\"msg\":\" \",\"show_alert\":\"1\",\"msg_type\":\"13\",\"custom\":{\"app_url\":\"dadabus://page/website?url=http%3A%2F%2Ftest.jump.dadabus.com%2Fcommon%2Fload%3Fwebapp%3Dchartered_order_details%26order_id%3D202016042286129726\"}}");
		t.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		APNPayload.DictionaryAlertMsg msg = new APNPayload.DictionaryAlertMsg();
		msg.setTitle("title");
		msg.setBody("body");
		payload.setAlertMsg(msg);
		t.setAPNInfo(payload);
//		t.setPushInfo("actionLocKey", 1, "message", "sound", "payload",
//				"locKey", "locArgs", "launchImage");
		return t;
	}


	public static NotificationTemplate NotificationTemplateDemo1(String appId, String appkey) throws Exception {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle("alskdfgjreg13223");
		template.setText("默认");
		// 配置通知栏图标
		template.setLogo("push.png");
		// 配置通知栏网络图标
//		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
//		template.setTransmissionType(0);
//		template.setTransmissionContent("请输入您要透传的内容");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		// notifyStyle功能，1为指定的样式，0为android系统默认样式，不设置该字段默认为0
//		template.setNotifyStyle(1);
		return template;

	}
}