package com.example.kk.arttraining.ui.homePage.function.search;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.sqlite.dao.SearchDao;
import com.example.kk.arttraining.ui.homePage.prot.ISearch;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.KeyBoardUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class DoSearch{

    //搜索
    public static void doSearch(Activity activity, TextView edSearchContent, ListView lvSearch) {
        String search_content = edSearchContent.getText().toString();
        if (Config.User_Id != null) {
            if (search_content.equals("")) {
                UIUtil.ToastshowShort(activity, "请输入搜索内容");
            } else {
                SearchDao dao = new SearchDao(activity);
                Boolean result = dao.contrastData(Config.User_Id, search_content);//判断搜索内容是否已经存在
                if (!result) {
                    dao.addData(Config.User_Id, search_content);
                }
                UIUtil.ToastshowShort(activity, search_content);
                edSearchContent.setText("");
            }
        } else {
            UIUtil.ToastshowShort(activity, "请先登录");
        }
    }

    public static void KeySearch(final Activity activity, final EditText edSearchContent,final ListView lvSearch) {
        edSearchContent.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 修改回车键功能
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    KeyBoardUtils.closeKeybord(edSearchContent,activity);
                    DoSearch.doSearch(activity,edSearchContent,lvSearch);
                }
                return false;
            }
        });
    }

}
