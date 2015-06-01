package com.logisticsUser.activity;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.widget.Toast;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.logistics.R;
import com.logisticsUser.base.BaseActivity;
import com.logisticsUser.base.BaseApplication;
import com.logisticsUser.fragment.AgreeFragment;
import com.logisticsUser.jpushdemo.ExampleUtil;
import com.logisticsUser.utils.SharePreferenceUtils;

public class MainActivity extends BaseActivity {
	protected static final String TAG = MainActivity.class.getSimpleName();

	private static FragmentManager fMgr;
	SharePreferenceUtils share;
	public static boolean isForeground = false;
	private static Boolean isExit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerMessageReceiver();
		/** 解决actionbar中菜单栏中flowover不显示问题 **/
		try {
			ViewConfiguration mconfig = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (Exception ex) {
		}
		share = new SharePreferenceUtils(MainActivity.this);
		setAlias(share.getInt("areaId", 0));
		setStyleBasic();
		// 获取FragmentManager实例
		fMgr = getSupportFragmentManager();

		initFragment();
		dealBottomButtonsClickEvent();

	}

	/**
	 * 初始化首个Fragment
	 */
	private void initFragment() {
		FragmentTransaction ft = fMgr.beginTransaction();
		AgreeFragment showListView = new AgreeFragment();
		ft.add(R.id.fragmentRoot, showListView, "weiXinFragment");
		Bundle bundle = new Bundle();
		bundle.putString("status", "0");
		showListView.setArguments(bundle);
		ft.addToBackStack("weiXinFragment");
		ft.commit();
		findViewById(R.id.rbAgree).setBackgroundResource(R.color.orange);
		findViewById(R.id.rbPickUp).setBackgroundResource(R.color.orange);
		findViewById(R.id.rbDistribution).setBackgroundResource(R.color.orange);
		findViewById(R.id.rbHistory).setBackgroundResource(R.color.orange);
	}

	/**
	 * 处理底部点击事件
	 */
	private void dealBottomButtonsClickEvent() {
		findViewById(R.id.rbAgree).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				AgreeFragment showListView = new AgreeFragment();
				Bundle bundle = new Bundle();
				bundle.putString("status", "0");
				showListView.setArguments(bundle);
				ft.add(R.id.fragmentRoot, showListView, "MeFragment");
				ft.addToBackStack("MeFragment");
				ft.commit();
				findViewById(R.id.rbAgree).setBackgroundResource(
						R.color.deeporange);
				findViewById(R.id.rbPickUp).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbDistribution).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbHistory).setBackgroundResource(
						R.color.orange);
			}
		});
		findViewById(R.id.rbPickUp).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				AgreeFragment showListView = new AgreeFragment();
				Bundle bundle = new Bundle();
				bundle.putString("status", "1");
				showListView.setArguments(bundle);
				ft.add(R.id.fragmentRoot, showListView, "AddressFragment");
				ft.addToBackStack("AddressFragment");
				ft.commit();
				findViewById(R.id.rbAgree)
						.setBackgroundResource(R.color.orange);
				findViewById(R.id.rbPickUp).setBackgroundResource(
						R.color.deeporange);
				findViewById(R.id.rbDistribution).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbHistory).setBackgroundResource(
						R.color.orange);
			}
		});
		findViewById(R.id.rbDistribution).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popAllFragmentsExceptTheBottomOne();
						FragmentTransaction ft = fMgr.beginTransaction();
						ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
						AgreeFragment showListView = new AgreeFragment();
						Bundle bundle = new Bundle();
						bundle.putString("status", "2");
						showListView.setArguments(bundle);
						ft.add(R.id.fragmentRoot, showListView,
								"AddressFragment");
						ft.addToBackStack("FindFragment");
						ft.commit();
						findViewById(R.id.rbAgree).setBackgroundResource(
								R.color.orange);
						findViewById(R.id.rbPickUp).setBackgroundResource(
								R.color.orange);
						findViewById(R.id.rbDistribution)
								.setBackgroundResource(R.color.deeporange);
						findViewById(R.id.rbHistory).setBackgroundResource(
								R.color.orange);
					}
				});
		findViewById(R.id.rbHistory).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				AgreeFragment setFragment = new AgreeFragment();
				Bundle bundle = new Bundle();
				bundle.putString("status", "3");
				setFragment.setArguments(bundle);
				ft.add(R.id.fragmentRoot, setFragment, "MeFragment");
				ft.addToBackStack("MeFragment");
				ft.commit();
				findViewById(R.id.rbAgree)
						.setBackgroundResource(R.color.orange);
				findViewById(R.id.rbPickUp).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbDistribution).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbHistory).setBackgroundResource(
						R.color.deeporange);
			}
		});
	}

	/**
	 * 从back stack弹出所有的fragment，保留首页的那个
	 */
	public static void popAllFragmentsExceptTheBottomOne() {
		for (int i = 0, count = fMgr.getBackStackEntryCount() - 1; i < count; i++) {
			fMgr.popBackStack();
		}
	}

	// 点击返回按钮
	@Override
	public void onBackPressed() {
		if (fMgr.findFragmentByTag("weiXinFragment") != null
				&& fMgr.findFragmentByTag("weiXinFragment").isVisible()) {
			MainActivity.this.finish();
		} else {
			super.onBackPressed();
		}
	}

	// 双击退出
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			exitByDoubleClick();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void exitByDoubleClick() {
		Timer tExit;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			BaseApplication.getInstance().mScreenManager.finishAllActivity();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image, menu);
		// 动态设置标题栏中用户名
		menu.findItem(R.id.TitleUserName).setTitle(
				share.getString("userName", "").toString());

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.TitleUserName:// 标题栏显示用户名

			break;
		case R.id.TitleChangeUser:// 切换用户
			share = new SharePreferenceUtils(this);
			share.clear();
			share.commit();
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.TitleExit:// 退出程序

			BaseApplication.getInstance().mScreenManager.finishAllActivity();
			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub
		super.onDestory();
		unregisterReceiver(mMessageReceiver);
	}

	private void setAlias(Integer alias) {

		String aliasa = alias.toString().trim();
		// 调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, aliasa));
	}

	/**
	 * 设置通知提示方式 - 基础属性
	 */
	private void setStyleBasic() {
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				MainActivity.this);
		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; // 设置为点击后自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND; // 设置为铃声（
																	// Notification.DEFAULT_SOUND）或者震动（
																	// Notification.DEFAULT_VIBRATE）
		JPushInterface.setPushNotificationBuilder(1, builder);
	}

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_SET_ALIAS, alias),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_SET_TAGS, tags),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				Log.d(TAG, "Set alias in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(),
						(String) msg.obj, null, mAliasCallback);
				break;

			case MSG_SET_TAGS:
				Log.d(TAG, "Set tags in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(), null,
						(Set<String>) msg.obj, mTagsCallback);
				break;

			default:
				Log.i(TAG, "Unhandled msg - " + msg.what);
			}
		}
	};

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
				setCostomMsg(showMsg.toString());
			}
		}
	}

	private void setCostomMsg(String msg) {
	}
}
