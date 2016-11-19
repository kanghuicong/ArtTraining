package com.example.kk.arttraining.ui.homePage.function.refresh;

import android.os.Handler;
import android.os.Message;


public class MyListener implements PullToRefreshLayout.OnRefreshListener
{

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{

		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
