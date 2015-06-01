package com.logisticsUser.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.logistics.R;
import com.logisticsUser.adapter.CityListViewAdapter;
import com.logisticsUser.base.BaseActivity;
import com.logisticsUser.commen.Constants;
import com.logisticsUser.http.RequestManager;
import com.logisticsUser.http.RequestManager.ResponseInterface;
import com.logisticsUser.model.CityModel;
import com.logisticsUser.model.UserModel;
import com.logisticsUser.serviceBean.CityBean;
import com.logisticsUser.serviceBean.UserBean;
import com.logisticsUser.utils.SharePreferenceUtils;

public class LoginActivity extends BaseActivity implements ResponseInterface {

	private EditText username;
	private EditText pwd;
	private Button login;
	private Button register;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		username = (EditText) findViewById(R.id.username);
		pwd = (EditText) findViewById(R.id.pwd);
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);

		username.getText();
		pwd.getText();
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (username.getText().toString().equals("")) {
					Toast.makeText(LoginActivity.this, "请输入用户名！", 5000).show();
				} else if (pwd.getText().toString().equals("")) {
					Toast.makeText(LoginActivity.this, "请输入密码！", 5000).show();
				} else {
					UserModel userModel = new UserModel();
					userModel.setUser_name(username.getText().toString());
					userModel.setUser_pwd(pwd.getText().toString());
					// userModel.setUser_name("小龙");
					// userModel.setUser_pwd("xiaolong123");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("user", userModel);
					showProgress();
					RequestManager manager = new RequestManager();
					manager.setResponseListener(LoginActivity.this);
					manager.requestUserLogin(map);
				}

			}
		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub
		if (parsedGSON != null) {
			if (tag == 1001) {
				UserBean userBean = (UserBean) parsedGSON;
				if (userBean.getRetcode().equals("0")) {
					if (userBean.getUser() != null) {
						SharePreferenceUtils share = new SharePreferenceUtils(
								LoginActivity.this);
						share.put("userId",
								String.valueOf(userBean.getUser().getUser_id()));
						share.put("userName", userBean.getUser().getUser_name());
						share.put("userCityId", userBean.getUser()
								.getUser_city_id());
						share.put("userTel", userBean.getUser().getUser_tel());
						share.put("areaId", userBean.getUser()
								.getUser_area_id());
						share.put(Constants.ServiceInterFace.IsLogin, "1");
						Toast.makeText(LoginActivity.this, "登录成功！",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(LoginActivity.this, "登录失败，请检查网络！",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(LoginActivity.this, "登录失败，请检查网络！",
							Toast.LENGTH_SHORT).show();
				}
			}

		}
		stopProgress();

	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		Toast.makeText(LoginActivity.this, "登录失败，请检查网络！", 5000).show();
	}

}
