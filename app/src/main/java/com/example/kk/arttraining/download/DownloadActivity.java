package com.example.kk.arttraining.download;


import java.io.File;



import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.R;


public class DownloadActivity extends Activity {

	private EditText editpath;
	private Button btndown;
	private Button btnstop;
	private TextView textresult;
	private ProgressBar progressbar;
	private static final int PROCESSING = 1;   //正在下载实时数据传输Message标志
	private static final int FAILURE = -1;	   //下载失败时的Message标志


	private Handler handler = new UIHander();

	private final class UIHander extends Handler{
		public void handleMessage(Message msg) {
			switch (msg.what) {
				//下载时
				case PROCESSING:
					int size = msg.getData().getInt("size");     //从消息中获取已经下载的数据长度
					progressbar.setProgress(size);				 //设置进度条的进度
					//计算已经下载的百分比,此处需要转换为浮点数计算
					float num = (float)progressbar.getProgress() / (float)progressbar.getMax();
					int result = (int)(num * 100);     //把获取的浮点数计算结果转换为整数
					textresult.setText(result+ "%");   //把下载的百分比显示到界面控件上
					if(progressbar.getProgress() == progressbar.getMax()){ //下载完成时提示
						Toast.makeText(getApplicationContext(), "文件下载成功", Toast.LENGTH_SHORT).show();
					}
					break;

				case FAILURE:    //下载失败时提示
					Toast.makeText(getApplicationContext(), "文件下载失败",Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editpath = (EditText) findViewById(R.id.editpath);
		btndown = (Button) findViewById(R.id.btndown);
		btnstop = (Button) findViewById(R.id.btnstop);
		textresult = (TextView) findViewById(R.id.textresult);
		progressbar = (ProgressBar) findViewById(R.id.progressBar);
		ButtonClickListener listener = new ButtonClickListener();
		btndown.setOnClickListener(listener);
		btnstop.setOnClickListener(listener);


	}


	private final class ButtonClickListener implements View.OnClickListener{
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btndown:
					String path = editpath.getText().toString();
					if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
						File saveDir = Environment.getExternalStorageDirectory();
						download(path, saveDir);
					}else{
						Toast.makeText(getApplicationContext(), "sd卡读取失败", Toast.LENGTH_SHORT).show();
					}
					btndown.setEnabled(false);
					btnstop.setEnabled(true);
					break;

				case R.id.btnstop:
					exit();
					btndown.setEnabled(true);
					btnstop.setEnabled(false);
					break;
			}
		}
		/*
		由于用户的输入事件(点击button, 触摸屏幕....)是由主线程负责处理的，如果主线程处于工作状态，
		此时用户产生的输入事件如果没能在5秒内得到处理，系统就会报“应用无响应”错误。
		所以在主线程里不能执行一件比较耗时的工作，否则会因主线程阻塞而无法处理用户的输入事件，
		导致“应用无响应”错误的出现。耗时的工作应该在子线程里执行。
		 */
		private DownloadTask task;
		/**
		 * 退出下载
		 */
		public void exit(){
			if(task!=null) task.exit();
		}
		private void download(String path, File saveDir) {//运行在主线程
			task = new DownloadTask(path, saveDir);
			new Thread(task).start();
		}


		/*
		 * UI控件画面的重绘(更新)是由主线程负责处理的，如果在子线程中更新UI控件的值，更新后的值不会重绘到屏幕上
		 * 一定要在主线程里更新UI控件的值，这样才能在屏幕上显示出来，不能在子线程中更新UI控件的值
		 */
		private final class DownloadTask implements Runnable{
			private String path;
			private File saveDir;
			private FileDownloadered loader;
			public DownloadTask(String path, File saveDir) {
				this.path = path;
				this.saveDir = saveDir;
			}
			/**
			 * 退出下载
			 */
			public void exit(){
				if(loader!=null) loader.exit();
			}

			public void run() {
				try {
					loader = new FileDownloadered(getApplicationContext(), path, saveDir, 3);
					progressbar.setMax(loader.getFileSize());//设置进度条的最大刻度
					loader.download(new DownloadProgressListener() {
						public void onDownloadSize(int size) {
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(handler.obtainMessage(-1));
				}
			}
		}
	}
}
