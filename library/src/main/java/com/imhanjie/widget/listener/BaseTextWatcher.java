package com.imhanjie.widget.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by hanjie on 2017/6/7.
 */

public abstract class BaseTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
