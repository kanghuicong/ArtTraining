package com.example.kk.arttraining.custom.view;

import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 * 说明：左右循环滚动字体
 */
public class TextChange extends TextView {
    private List<String> list;
    private Handler handler;
    private boolean isOne;

    public TextChange(Context context, AttributeSet attrs) {
        super(context, attrs);
        list = new ArrayList<String>();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(getText() + "")) {
                        if (i < list.size() - 1) {
                            setText(list.get(i + 1));
                            break;
                        } else {
                            setText(list.get(0));
                            break;
                        }
                    }
                }
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        };
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        if (!isOne) {
            segmentationTxt();
            isOne = true;
        }
    }
    private void segmentationTxt() {
        if (getVisibility() == GONE || getText() == null
                || "".equals(getText().toString())) {
            return;
        }
        String txt = getText().toString();
        int characterTotalLength = getLayout().getLineEnd(0) + 1;
        int characterVisibleLength = getLayout().getEllipsisStart(0)+1;
        int linehang = characterTotalLength % characterVisibleLength == 0 ? characterTotalLength
                / characterVisibleLength
                : characterTotalLength / characterVisibleLength + 1;
        for (int i = 0; i < linehang; i++) {
            if (i * characterVisibleLength + characterVisibleLength > characterTotalLength - 1) {
                list.add(txt.substring(i * characterVisibleLength,
                        characterTotalLength - 1));
            } else {
                list.add(txt.substring(i * characterVisibleLength, i
                        * characterVisibleLength + characterVisibleLength));
            }
        }
        if (list.size() > 0) {
            setText(list.get(0));
            if (list.size() > 1) {
                handler.sendEmptyMessageDelayed(0, 2500);
            }
        }
    }
}
