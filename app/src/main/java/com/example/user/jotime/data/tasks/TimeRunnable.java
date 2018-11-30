package com.example.user.jotime.data.tasks;


import android.os.Handler;
import android.util.Log;

import com.example.user.jotime.data.callback.TimeCallback;
import com.example.user.jotime.data.model.ItemModel;
import com.example.user.jotime.data.model.SettingModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class TimeRunnable implements Runnable {

    private SettingModel model;
    private TimeCallback<List<ItemModel>> timeCallback;
    private Handler mainUiHandler;

    public TimeRunnable(SettingModel model, TimeCallback<List<ItemModel>> callback, Handler mainUiHandler) {
        this.model = model;
        timeCallback = callback;
        this.mainUiHandler = mainUiHandler;
    }

    @Override
    public void run() {
        final Map<String, String> params = new HashMap<>();
        params.put("option", "com_smartshoutbox");
        params.put("task", "addshout");


//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
//        params.put("date_from", formatter.format(model.getFromDate()));
//        params.put("date_till", formatter.format(model.getTillDate()));
//        Log.i("TimeRunnable", "formatter.format(model.getTillDate()): " + formatter.format(model.getTillDate()));

        final Element body;
        List<ItemModel> listModels = new ArrayList<>();
        try {
            String URL = "http://pozitiv.fm";
            body = Jsoup.connect(URL).post().body();
            Log.i("TimeRunnable", "length " + body.text().length());
            Log.i("TimeRunnable", body.text());

            List<String> missingTimeList = getMissingTime(body);
            Log.i("TimeRunnable", missingTimeList.toString());
            Log.i("TimeRunnable", "size " + missingTimeList.size());
            List<String> datesList = getDates(body);
            Log.i("TimeRunnable", "size " + missingTimeList.size());
            Log.i("TimeRunnable", missingTimeList.toString());
            List<String> listLog = getListLog(body);
            Log.i("TimeRunnable", listLog.toString());


//            int i = 0;
//            for (String missingTime : missingTimeList) {
//                listModels.add(new ItemModel(datesList.get(i), missingTime));
//                i++;
//            }
//
//            i = -1;
//            for (String str : listLog) {
//                if (str.lastIndexOf("Дата") == 0) {
//                    i++;
//                } else
//                    listModels.get(i).getLogList().add(str);
//            }
//
//            mainUiHandler.post(new CallbackToUI(listModels));
        } catch (final IOException e) {
            e.printStackTrace();

            mainUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    timeCallback.onError(e);
                }
            });
        }
    }

    private static List<String> getMissingTime(Element body) {
        final List<String> list = new ArrayList<>();
        Elements elements = body.select("tbody");
        for (Element e : elements) {
            Elements column = e.select("tr");
            for (Element mod : column){
                Log.i("TimeRunnable_el", "----------------------------------");
//                Log.i("TimeRunnable_el", mod.text());
                Elements values = mod.select("td");
                for (Element val:values){
                    Log.i("TimeRunnable_el", val
                            .text());
                }
            }
            Log.i("TimeRunnable_el", column.toString());
            Log.i("TimeRunnable_el", "s " + column.size());
        }
//        elements.select("span").remove();
        for (Element e : elements) list.add(e.text());
        return list;
    }

    private static List<String> getDates(Element body) {
        final List<String> list = new ArrayList<>();
        Elements elements = body.select("tr class");
        for (Element e : elements) list.add(e.text());
        return list;
    }

    private static List<String> getListLog(Element body) {
        final List<String> list = new ArrayList<>();
        Elements elements = body.select("table.events tr");
        for (Element e : elements) {
            if (e.text().lastIndexOf("Дата") == 0) Log.i("TimeRunnable", "******************");
            list.add(e.text());
            Log.i("TimeRunnable", e.text());
        }
        return list;
    }

    private class CallbackToUI implements Runnable {

        private final List<ItemModel> listModels;

        CallbackToUI(List<ItemModel> postModels) {
            this.listModels = postModels;
        }

        @Override
        public void run() {
            timeCallback.onEmit(listModels);
            timeCallback.onCompleted();
        }
    }
}
