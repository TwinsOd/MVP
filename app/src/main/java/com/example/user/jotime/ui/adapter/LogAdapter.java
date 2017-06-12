package com.example.user.jotime.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.jotime.R;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.CommentHolder> {

    @NonNull
    private final List<String> comments;

    public LogAdapter(@NonNull List<String> comments) {
        this.comments = comments;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        String log = comments.get(position);
        //2017-05-31 20:03:21 Захаров Владимир 7F00692541 UProx IP 5-й этаж out mssql
        String[] array = log.split(" ");

        holder.timeText.setText(array[1]);
        holder.placeText.setText(array[7] + " " + array[8]);

        if (array[9].equals("out"))
            holder.cardView.setBackgroundColor(holder.timeText.getResources().getColor(R.color.out_log));
        else if (array[9].equals("in"))
            holder.cardView.setBackgroundColor(holder.timeText.getResources().getColor(R.color.in_log));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentHolder extends RecyclerView.ViewHolder {
        TextView timeText;
        TextView placeText;
        CardView cardView;

        CommentHolder(View itemView) {
            super(itemView);

            timeText = (TextView) itemView.findViewById(R.id.time_text);
            placeText = (TextView) itemView.findViewById(R.id.place_text);
            cardView = (CardView) itemView.findViewById(R.id.card_view_item);
        }
    }
}
