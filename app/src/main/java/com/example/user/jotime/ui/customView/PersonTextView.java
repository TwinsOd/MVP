package com.example.user.jotime.ui.customView;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class PersonTextView extends android.support.v7.widget.AppCompatTextView {
    public PersonTextView(Context context) {
        super(context);
    }

    public PersonTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setId(String id) {
        setText("Сотрудник (карта): " + id);
    }

    public void setId(int id) {
        setId(String.valueOf(id));
    }
}
