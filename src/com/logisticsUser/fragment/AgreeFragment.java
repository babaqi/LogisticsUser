package com.logisticsUser.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.logistics.R;
import com.logisticsUser.adapter.ShowListViewAdapter;
import com.logisticsUser.commen.Constants;
import com.logisticsUser.http.RequestManager;
import com.logisticsUser.http.RequestManager.ResponseInterface;
import com.logisticsUser.model.ContextModel;
import com.logisticsUser.refresh.MyListView;
import com.logisticsUser.refresh.MyListView.IXListViewListener;
import com.logisticsUser.serviceBean.ContextBean;
import com.logisticsUser.utils.SharePreferenceUtils;

public class AgreeFragment extends Fragment implements ResponseInterface,
		IXListViewListener {
	private MyListView myListView;
	private ListView myHistoryListView;
	private RelativeLayout RiLi;
	private TextView DemuNum;
	private TextView ChooseDate;
	private Button Finsh;
	private ShowListViewAdapter listViewAdapter;
	private Map<String, Object> map;
	private SharePreferenceUtils utils;
	private RequestManager manager;
	private SimpleDateFormat format;
	private SharePreferenceUtils share;
	private List<ContextModel> contextList;
	public ProgressDialog mProgress = null;
	private int page = 0;
	private String time;
	private String status = "0";// 问题状态（0：待确认，1：取货中，2：配送中
	private String UserId;
	private String mMenuNum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.agree_fragment, container, false);
		myListView = (MyListView) view.findViewById(R.id.showListView);
		myHistoryListView = (ListView) view
				.findViewById(R.id.HistoryShowListView);
		RiLi = (RelativeLayout) view.findViewById(R.id.rili_relay);// 历史订单布局
		DemuNum = (TextView) view.findViewById(R.id.DemuNum);// 历史订单数
		ChooseDate = (TextView) view.findViewById(R.id.ChooseDate);// 日期选择按钮

		map = new HashMap<String, Object>();
		manager = new RequestManager();

		myListView.setPullLoadEnable(true);
		myListView.setPullIsEnable(false);
		myListView.setXListViewListener(this);

		// myHistoryListView.setPullLoadEnable(true);
		// myHistoryListView.setPullIsEnable(false);
		// myHistoryListView.setXListViewListener(this);
		contextList = new ArrayList<ContextModel>();
		status = getArguments().get("status").toString();
		listViewAdapter = new ShowListViewAdapter(getActivity(), contextList,
				status);
		myListView.setAdapter(listViewAdapter);
		myHistoryListView.setAdapter(listViewAdapter);
		/** 逻辑处理 **/
		Logic();
		return view;
	}

	public void Request() {

		showProgress();
		map.put("page", String.valueOf(page));
		map.put("state", status);
		share = new SharePreferenceUtils(getActivity());
		if (status.equals("0")) {
			map.put("areaId", String.valueOf(share.getInt("areaId", 0)));
		} else {
			map.put("userid", share.getString("userId", null));
		}
		manager.setResponseListener(AgreeFragment.this);
		manager.requestSeclect(map);

	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		if (parsedGSON != null) {
			ContextBean contextBean = (ContextBean) parsedGSON;
			// 历史订单成功回调
			if (tag == Constants.RequestTag.QueryHistoryMenuListTag) {
				if (contextBean.getRetcode().equals("0")) {
					if (contextBean.getContext() != null) {
						mMenuNum = contextBean.getCount();// 得到历史订单的个数
						DemuNum.setText(mMenuNum);// 显示在控件上
						contextList.clear();
						for (int i = 0; i < contextBean.getContext().size(); i++) {
							contextList.add(contextBean.getContext().get(i));
						}
						listViewAdapter.setmContextList(contextList);
						listViewAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), "没有更多订单", 5000).show();
					}
				} else {
					Toast.makeText(getActivity(), "没有更多订单！", 5000).show();
				}
			}
			// 待确认、取货中、配送中 三个状态请求成功回调
			if (tag == Constants.RequestTag.QueryContextTag) {
				if (contextBean.getRetcode().equals("0")) {
					if (contextBean.getContext() != null) {
						for (int i = 0; i < contextBean.getContext().size(); i++) {
							contextList.add(contextBean.getContext().get(i));
						}
						listViewAdapter.setmContextList(contextList);
						listViewAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), "没有更多数据", 5000).show();
						myListView.setPullLoadEnable(false);
					}
				} else {
					if (getActivity() != null) {
						Toast.makeText(getActivity(), "没有更多数据", 5000).show();
					}

					myListView.setPullLoadEnable(false);
				}
			}
		}
		stopLoad();
		stopProgress();
	}

	// 请求失败的回调
	@Override
	public void errorResonse(String retmeg, int tag) {
		Toast.makeText(getActivity(), "没有更多数据！", 5000).show();
		stopLoad();
		stopProgress();
	}

	/**
	 * 停止刷新和加载
	 */
	public void stopLoad() {
		myListView.stopRefresh();
		myListView.stopLoadMore();
		myListView.setRefreshTime();
	}

	/**
	 * progressDialog的展示
	 */
	public void showProgress() {
		if (getActivity() != null && !getActivity().isFinishing()) {
			mProgress = new ProgressDialog(getActivity());
			mProgress.setMessage("正在努力加载中...");
			mProgress.setCancelable(true);
			mProgress.show();
		}
	}

	/**
	 * progressDialog的消失
	 */
	public void stopProgress() {
		if (mProgress != null) {
			mProgress.dismiss();
			mProgress = null;
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 0;
		myListView.setPullLoadEnable(true);
		contextList.clear();
		Request();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		page++;
		Request();

	}

	public void Logic() {

		if (!status.equals("3")) {
			Request();

		} else {
			myListView.setVisibility(View.GONE);
			myHistoryListView.setVisibility(View.VISIBLE);
			RiLi.setVisibility(View.VISIBLE);// 历史订单页面显示日历布局

			// 选择日期按钮按钮
			ChooseDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// 日历dialog
					final AlertDialog dlg = new AlertDialog.Builder(
							getActivity()).create();
					dlg.show();
					Window window = dlg.getWindow();
					// *** 主要就是在这里实现这种效果的.
					// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
					window.setContentView(R.layout.calender_dialog);
					Finsh = (Button) window.findViewById(R.id.Finsh);
					// timeEt=(EditText)findViewById(R.id.timeEt);
					final DatePicker datePicker = (DatePicker) window
							.findViewById(R.id.datePicker);
					// TimePicker
					// timePicker=(TimePicker)fsindViewById(R.id.timePicker);

					final Calendar calendar = Calendar.getInstance();
					final int year = calendar.get(Calendar.YEAR);
					final int monthOfYear = calendar.get(Calendar.MONTH);
					final int dayOfMonth = calendar.get(Calendar.DATE);

					datePicker.init(year, monthOfYear, dayOfMonth,
							new OnDateChangedListener() {
								public void onDateChanged(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {

								}
							});

					Finsh.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							time = datePicker.getYear() + "年"
									+ (datePicker.getMonth() + 1) + "月"
									+ datePicker.getDayOfMonth() + "日";
							ChooseDate.setText(time);
							calendar.set(datePicker.getYear(),
									datePicker.getMonth(),
									datePicker.getDayOfMonth());
							format = new SimpleDateFormat("yyyy-MM-dd");
							String timer = format.format(calendar.getTime());
							HistotyMenuList(timer);
							dlg.dismiss();
						}
					});
				}
			});
		}
	}

	/** 历史订单列表请求 **/
	public void HistotyMenuList(String timer) {
		showProgress();
		map = new HashMap<String, Object>();
		utils = new SharePreferenceUtils(getActivity());
		UserId = utils.getString("userId", null);
		map.put("time", timer);
		map.put("userid", UserId);
		manager.setResponseListener(AgreeFragment.this);
		manager.requestHistoryMenuList(map);
	}
}
