package com.example.user.jotime.ui.customView;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;

public class DateTextView extends android.support.v7.widget.AppCompatTextView{
    public DateTextView(Context context) {
        super(context);
    }

    public DateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLongDate(long date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String dateText = formatter.format(date);
        setText(dateText);
    }
}
