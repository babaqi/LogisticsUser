package com.logisticsUser.adapter;

import java.util.List;

import com.example.logistics.R;
import com.logisticsUser.model.CityModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityListViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<CityModel> mCityList;
	
	

	public CityListViewAdapter(Context context,List<CityModel> mCityList)
	{
		this.mContext=context;
		this.mCityList=mCityList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCityList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mCityList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final HolderView mHolder;
		if (convertView == null) {
			mHolder = new HolderView();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.city_adapter, null);
			mHolder.city_text = (TextView) convertView
					.findViewById(R.id.city_text);
			
			convertView.setTag(mHolder);
		} else {
			mHolder = (HolderView) convertView.getTag();
		}
		mHolder.city_text.setText(mCityList.get(position).getCity_name());
		
		return convertView;
	}

	class HolderView {
		TextView city_text;

	}
}
