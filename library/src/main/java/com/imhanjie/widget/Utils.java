package com.imhanjie.widget;

import android.content.Context;
import android.util.TypedValue;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-11-06
 */

public class Utils {

    public static int getPrimaryColor(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

}
