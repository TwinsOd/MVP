package com.example.user.jotime.ui.list.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.jotime.R;
import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.ui.list.view.DetailsInteractors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.CommentHolder> implements View.OnClickListener {

    @NonNull
    private final Context context;
    @NonNull
    private List<ItemModel> list;
    private final DetailsInteractors listener;

    public TimeAdapter(@NonNull Context context, @NonNull List<ItemModel> list, DetailsInteractors listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
        return new CommentHolder(view, this);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        ItemModel model = list.get(position);
        holder.missingText.setText(model.getMissingTime());
        holder.cardView.setTag(position);
        holder.datesText.setText(model.getDates());
        if (changeLimitTime(model.getMissingTime())) {
            holder.missingText.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    private boolean changeLimitTime(String time) {
        if (time == null || time.isEmpty()) return false;

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = formatter.parse(time);
            return (date.getTime() > 25 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update(List<ItemModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(list.get((int) v.getTag()).getLogList());
    }

    static class CommentHolder extends RecyclerView.ViewHolder {

        TextView datesText;
        TextView missingText;
        CardView cardView;

        CommentHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);

            missingText = (TextView) itemView.findViewById(R.id.missing_time_text);
            cardView = (CardView) itemView.findViewById(R.id.card_view_item);
            cardView.setOnClickListener(onClickListener);
            datesText = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}
