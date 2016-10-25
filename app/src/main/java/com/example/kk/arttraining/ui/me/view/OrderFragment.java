package com.example.kk.arttraining.ui.me.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;
import com.example.kk.arttraining.ui.me.adapter.OrderAdapter;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：wschenyongyin on 2016/10/23 13:33
 * 说明:
 */
public class OrderFragment extends Fragment {

//    @InjectView(R.id.lv_order)
    ListView lv_order;

    private View view;
    private Context context;
    private ParseOrderListBean list;
    private OrderAdapter orderAdapter;
    private int count;

    public OrderFragment(ParseOrderListBean list, int count) {
        this.list = list;
        this.count=count;
        UIUtil.showLog("执行了22", "------->");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UIUtil.showLog("执行了66", "------->");
        context = getActivity();

        view = View.inflate(context, R.layout.me_order_fragment, null);
        lv_order= (ListView) view.findViewById(R.id.lv_order);
        ButterKnife.inject(view);
        initView();
        return view;
    }

    private void initView() {

        orderAdapter = new OrderAdapter(context,count);
        lv_order.setAdapter(orderAdapter);
        UIUtil.showLog("执行了33", "------->");
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
