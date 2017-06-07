package com.example.user.jotime.net;


import android.util.Log;

import com.example.user.jotime.data.model.ItemListModel;
import com.example.user.jotime.data.model.UserModel;
import com.example.user.jotime.ui.LoadData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeRunnable implements Runnable {

    private UserModel model;
    private LoadData loadData;
    public TimeRunnable(UserModel model, LoadData loadData){
        this.model = model;
        this.loadData = loadData;
    }

    @Override
    public void run() {
        final Map<String, String> params = new HashMap<>();
        params.put("card_code_dec", String.valueOf(model.getId()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        params.put("date_from", formatter.format(model.getFromDate()));
        params.put("date_till", formatter.format(model.getTillDate()));
        Log.i("TimeRunnable", "formatter.format(model.getTillDate()): " +  formatter.format(model.getTillDate()));

        final Element body;
        List<ItemListModel> listModels = new ArrayList<>();
        try {
            String URL = "http://jo.time";
            body = Jsoup.connect(URL).data(params).post().body();
            Log.i("TimeRunnable", body.text());

            List<String> missingTimeList = getMissingTime(body);
            List<String> datesList = getDates(body);

            for (String str:missingTimeList){
                Log.i("TimeRunnable", "missingTime: " +  str);
                listModels.add(new ItemListModel(null,str));
            }

            int i = 0;
            for (String str:datesList){
                Log.i("TimeRunnable", "Dates: " +  str);
                listModels.get(i).setDates(str);
                i++;
            }

            loadData.success(listModels);

        } catch (IOException e) {
            e.printStackTrace();
            loadData.error();
        }
    }

    private static List<String> getMissingTime(Element body) {
        final List<String> list = new ArrayList<>();
        Elements elements = body.select("td.red b");
        elements.select("span").remove();
        for (Element e : elements) list.add(e.text());
        return list;
    }

    private static List<String> getDates(Element body) {
        final List<String> list = new ArrayList<>();
        Elements elements = body.select("table.stat tr:lt(2)>td:lt(1)");
        for (Element e : elements) list.add(e.text());
        return list;
    }
}
