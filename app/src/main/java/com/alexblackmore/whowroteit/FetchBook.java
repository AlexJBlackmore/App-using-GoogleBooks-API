package com.alexblackmore.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> authorResultTV;
    private WeakReference<TextView> titleResultTV;

    public FetchBook(TextView authorResultParam, TextView titleResultParam){
        this.authorResultTV = new WeakReference<>(authorResultParam);
        this.titleResultTV = new WeakReference<>(titleResultParam);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;

            while (i < itemsArray.length() && (authors == null && title == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

            if (title != null && authors != null) {
                authorResultTV.get().setText(authors);
                titleResultTV.get().setText(title);
            } else {
                authorResultTV.get().setText("Nothing1");
                titleResultTV.get().setText("Nothing2");
            }

        } catch (JSONException e) {
            authorResultTV.get().setText("Nothing1");
            titleResultTV.get().setText("Nothing2");
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }
}
