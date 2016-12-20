package com.example.kk.arttraining.ui.discover.function.posting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class PostingTextChangeListener {

    public static void getTextChangeListener(final Context context, final EditText edPostingContent, final TextView tvPostingNumber, final int content_number){

        edPostingContent.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart ;
            private int editEnd ;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvPostingNumber.setText(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = edPostingContent.getSelectionStart();
                editEnd = edPostingContent.getSelectionEnd();
                tvPostingNumber.setText(temp.length() + "/140");

                if (edPostingContent.length() > content_number) {
                    UIUtil.ToastshowShort(context, "内容太长，无法发表...");
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    edPostingContent.setText(s);
                    edPostingContent.setSelection(tempSelection);
                }
            }
        });
    }
}
