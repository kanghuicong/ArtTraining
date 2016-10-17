package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MessageEntity;
import com.example.kk.arttraining.ui.homePage.adapter.MessageListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/10/10.
 * QQ邮箱:515849594@qq.com
 */
public class MessageMain extends Activity {
    @InjectView(R.id.lv_message)
    ListView lvMessage;

    List<MessageEntity> list = new ArrayList<MessageEntity>();
    MessageEntity molder = new MessageEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_message);
        ButterKnife.inject(this);


        molder.setName("hehe");
        list.add(molder);

        MessageListAdapter adapter = new MessageListAdapter(this,list);
        lvMessage.setAdapter(adapter);
    }
}
