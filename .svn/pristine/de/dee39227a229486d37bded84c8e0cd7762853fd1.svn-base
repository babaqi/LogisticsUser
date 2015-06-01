/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.logisticsUser.refresh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.logistics.R;

public class MyListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_FINISH = 3;

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;

	private String footerNormal;
	private String footerReady;

	public MyListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public MyListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			if (footerReady == null) {
				mHintView.setText(R.string.xlistview_footer_hint_ready);
			} else {
				mHintView.setText(footerReady);
			}
		} else if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_header_hint_loading);
		} else {
			mHintView.setVisibility(View.VISIBLE);
			if (footerNormal == null) {
				mHintView.setText(R.string.xlistview_header_hint_normal);
			} else {
				mHintView.setText(footerNormal);
			}
		}
	}

	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public void setFooterText(String footerNormal, String footerReady) {
		this.footerNormal = footerNormal;
		this.footerReady = footerReady;
	}

	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
				.getLayoutParams();
		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView
				.findViewById(R.id.xlistview_footer_hint_textview);
		if (footerNormal == null) {
			mHintView.setText(R.string.xlistview_header_hint_normal);
		} else {
			mHintView.setText(footerNormal);
		}
	}

}
