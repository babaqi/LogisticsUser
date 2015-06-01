package com.logisticsUser.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.logistics.R;
import com.logisticsUser.adapter.CityListViewAdapter;
import com.logisticsUser.base.BaseActivity;
import com.logisticsUser.http.RequestManager;
import com.logisticsUser.http.RequestManager.ResponseInterface;
import com.logisticsUser.model.CityModel;
import com.logisticsUser.model.UserModel;
import com.logisticsUser.serviceBean.CityBean;
import com.logisticsUser.serviceBean.UserBean;

public class RegisterActivity extends BaseActivity implements ResponseInterface {
	private EditText registername;
	private EditText registerpwd;
	private EditText registertel;
	private Button registerbtn;
	private Button city_btn;
	private ListView city_listview;
	private List<CityModel> listCityBean = new ArrayList<CityModel>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.reginst_activity);
		super.onCreate(savedInstanceState);
		registername = (EditText) findViewById(R.id.regist_username);
		registerpwd = (EditText) findViewById(R.id.regist_pwd);
		registertel = (EditText) findViewById(R.id.regist_tel);
		registerbtn = (Button) findViewById(R.id.regist_btn);
		city_btn = (Button) findViewById(R.id.city_spinner);
		city_listview = (ListView) findViewById(R.id.city_list);
		registerbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				UserModel userModel = new UserModel();
				userModel.setUser_name(registername.getText().toString());
				userModel.setUser_pwd(registerpwd.getText().toString());
				userModel.setUser_tel(registertel.getText().toString());
			
				if(registername.getText().toString().equals(""))
				{
					Toast.makeText(RegisterActivity.this, "请输入用户名！", 5000)
					.show();
				}else if(registerpwd.getText().toString().equals(""))
				{
					Toast.makeText(RegisterActivity.this, "请输密码！", 5000)
					.show();
				}
				else if(registertel.getText().toString().equals(""))
				{
					Toast.makeText(RegisterActivity.this, "请输电话！", 5000)
					.show();
				}else if(city_btn.getText().toString().equals(""))
				{
					Toast.makeText(RegisterActivity.this, "请选择城市！", 5000)
					.show();
				}else
				{
					showProgress();
					userModel.setUser_city_id(Integer.parseInt(city_btn.getText().toString()));
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("user", userModel);
					showProgress();
					RequestManager manager = new RequestManager();
					manager.setResponseListener(RegisterActivity.this);
					manager.requestRegister(map);
				}
				
			}
		});
		city_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				showProgress();
				RequestManager manager = new RequestManager();
				manager.setResponseListener(RegisterActivity.this);
				manager.requestCity(map);
			}
		});
		city_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				city_btn.setText(String.valueOf(listCityBean.get(arg2).getCity_name()));
				city_listview.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub
		if (parsedGSON != null) {
			if (tag == 1006) {
				UserBean userBean = (UserBean) parsedGSON;
				if (userBean.getRetcode().equals("0")) {
					if (userBean.getUser() != null) {
						Toast.makeText(RegisterActivity.this, "注册成功！", 5000)
								.show();
						RegisterActivity.this.finish();
					} else {
						Toast.makeText(RegisterActivity.this, "注册失败，请检查网络！",
								5000).show();
					}
				} else {
					Toast.makeText(RegisterActivity.this, "注册失败，请检查网络！", 5000)
							.show();

				}
			}
			if (tag == 1007) {
				city_listview.setVisibility(View.VISIBLE);
				CityBean cityBean = (CityBean) parsedGSON;
				listCityBean = cityBean.getCity();
				CityListViewAdapter cityListViewAdapter = new CityListViewAdapter(
						RegisterActivity.this, listCityBean);
				city_listview.setAdapter(cityListViewAdapter);
				cityListViewAdapter.notifyDataSetChanged();
				stopProgress();
			}

		}
		stopProgress();

	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		Toast.makeText(RegisterActivity.this, "注册失败，请检查网络！", 5000).show();
	}
}
