package com.example.user.jotime.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.jotime.R;
import com.example.user.jotime.data.model.ItemListModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.CommentHolder> {

    @NonNull
    private final Context context;
    @NonNull
    private final List<ItemListModel> comments;

    public TimeAdapter(@NonNull Context context, @NonNull List<ItemListModel> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        ItemListModel model = comments.get(position);
        holder.missingText.setText(model.getMissingTime());
        holder.datesText.setText(model.getDates());
        if (changeLimitTime(model.getMissingTime())){
            holder.missingText.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    private boolean changeLimitTime(String time){
        if(time == null || time.isEmpty())return false;
        
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = formatter.parse(time);
            return  (date.getTime() > 25 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    static class CommentHolder extends RecyclerView.ViewHolder {

        TextView datesText;
        TextView missingText;

        CommentHolder(View itemView) {
            super(itemView);

            missingText = (TextView)itemView.findViewById(R.id.missing_time_text);
            datesText = (TextView)itemView.findViewById(R.id.date_text);
        }
    }
}
