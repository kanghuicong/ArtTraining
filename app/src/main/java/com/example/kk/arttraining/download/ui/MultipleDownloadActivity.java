package com.example.kk.arttraining.download.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.example.kk.arttraining.R;
import com.example.kk.arttraining.download.adapter.FileListAdapter;
import com.example.kk.arttraining.download.bean.FileInfo;
import com.example.kk.arttraining.download.service.MultipleDownloadService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MultipleDownloadActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity2";

    @InjectView(R.id.list)
    ListView list;

    private List<FileInfo> fileList;
    private FileListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.inject(this);

        initData();
        initSetup();
        initRegister();


    }

    private void initRegister() {
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(MultipleDownloadService.ACTION_UPDATE);
        filter.addAction(MultipleDownloadService.ACTION_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 添加数据
     */
    private void initData() {
        fileList = new ArrayList<>();
        FileInfo fileInfo1 = new FileInfo(0, "http://121.43.172.150:8080/LeRun/colorrun.apk",
                "weixin.apk", 0, 0);
        FileInfo fileInfo2 = new FileInfo(1, "http://121.43.172.150:8080/LeRun/piaoxue.mp3",
                "qq.apk", 0, 0);
        FileInfo fileInfo3 = new FileInfo(2, "http://121.43.172.150:8080/LeRun/image_zbwt.zip",
                "imooc.apk", 0, 0);
        FileInfo fileInfo4 = new FileInfo(3, "http://121.43.172.150:8080/LeRun/image_zbwt.jpg",
                "Activator.exe", 0, 0);
        fileList.add(fileInfo1);
        fileList.add(fileInfo2);
        fileList.add(fileInfo3);
        fileList.add(fileInfo4);
    }


    private void initSetup() {
        //创建适配器
        listAdapter = new FileListAdapter(this, fileList);
        //给listview设置适配器
        list.setAdapter(listAdapter);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MultipleDownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                long finished = intent.getLongExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                Log.e(TAG, "finished==" + finished);
                Log.e(TAG, "id==" + id);
                listAdapter.updateProgress(id, finished);
                //progressBar.setProgress(finished);
            } else if (MultipleDownloadService.ACTION_FINISHED.equals(intent.getAction())) {
                FileInfo fileinfo = (FileInfo) intent.getSerializableExtra("fileinfo");
                //更新进度为100
                listAdapter.updateProgress(fileinfo.getId(), 100);
                Toast.makeText(
                        MultipleDownloadActivity.this,
                        fileinfo.getFileName() + "下载完成",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }


}
