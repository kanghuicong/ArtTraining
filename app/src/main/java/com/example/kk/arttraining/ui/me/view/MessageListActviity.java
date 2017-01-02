package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.adapter.MessageListAdapter;
import com.example.kk.arttraining.ui.me.bean.MessageBean;
import com.example.kk.arttraining.ui.me.presenter.MessageListPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/12/30 09:49
 * 说明:
 */
public class MessageListActviity extends BaseActivity implements IMessageListView {

    @InjectView(R.id.tv_title_subtitle)
    TextView tvTitleSubtitle;
    @InjectView(R.id.me_lv_msg)
    ListView meLvMsg;
    @InjectView(R.id.msg_read_more)
    TextView msgReadMore;
    //标记是否有新的消息
    private boolean IS_NEW = false;
    //网络请求处理类
    MessageListPresenter presenter;

    private String from_type;
    //适配器
    private MessageListAdapter adapter = null;
    //获取到的数据
    private List<MessageBean> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_message_list_activity);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this, "消息");
    }

    @Override
    public void init() {
        from_type = getIntent().getStringExtra("type");
        if (from_type.equals("msg_yes")) {
            IS_NEW = true;
        } else {
            IS_NEW = false;
        }
        tvTitleSubtitle.setTextColor(getResources().getColor(R.color.gray));
        tvTitleSubtitle.setText("清空");
        presenter = new MessageListPresenter(this);
        //如果有新的信息那么调用获取新消息的接口，如果没有新的消息那么调用全部消息列表
        if (IS_NEW) {
            getMessageNewData();
        } else {
            getMessageAll();
        }
    }

    @OnClick({R.id.tv_title_subtitle, R.id.msg_read_more})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_subtitle:
                break;
            case R.id.msg_read_more:
                break;
        }

    }

    //获取新的消息列表
    @Override
    public void getMessageNewData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        presenter.getNewMessageData(map);

    }

    //获取全部消息列表
    @Override
    public void getMessageAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
        presenter.getAllMessageData(map);
    }

    //获取新的消息成功
    @Override
    public void SuccessNew(List<MessageBean> messageBeanList) {
        dataList = messageBeanList;
        adapter = new MessageListAdapter(MessageListActviity.this, dataList);
        meLvMsg.setAdapter(adapter);


    }

    //获取全部消息成功
    @Override
    public void SuccessAll(List<MessageBean> messageBeanList) {
        dataList = messageBeanList;
        if (adapter != null) {
            adapter.refreshCount(dataList.size());
            adapter.notifyDataSetChanged();
        } else {
            adapter = new MessageListAdapter(MessageListActviity.this, dataList);
            meLvMsg.setAdapter(adapter);
        }
    }

    //获取新消息列表失败
    @Override
    public void FailureNew(String error_code, String error_msg) {

        UIUtil.ToastshowShort(this, error_msg);
    }

    //获取全部消息失败
    @Override
    public void FailureAll(String error_code, String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
    }
}
