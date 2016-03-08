package com.malzberry.lolstuff;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParseSummary extends AsyncTask<String, Void, ArrayList<String>> {
    public AsyncResponseTips delegate = null;
    public ParseSummary(AsyncResponseTips ar) { delegate = ar;  }
    ArrayList<String> output = new ArrayList<String>();

    @Override
    protected ArrayList<String> doInBackground(String... strings)  {
        StringBuffer buffer = new StringBuffer();
        try {
            Document doc = Jsoup.connect("http://na.op.gg/statistics/champion/")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1").get();

            // Get document (HTML page) title
            String champ = strings[0];
            // output.add(title + "\r\n\n");

            // Get meta info
            Elements metaElems = doc.select("tr.no_background");

            for(Element elem: metaElems){
                Elements tds = elem.select("td");
                for(int i = 0; i < tds.size(); i++) {
                    String content = tds.get(i).text();
                    buffer.append(content + "\r\n\n");
                }
            }

            /*
            for(Element metaElem : metaElems)   {
                String content = metaElem.ownText();
                buffer.append(content + "\r\n\n");
            }
            */

        }
        catch(Throwable t) {
            t.printStackTrace();
            output.add("Error");
            output.add(t.toString());
        }
        output.add(buffer.toString());


        return output;
    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
        super.onPostExecute(s);
        delegate.processFinish(s);
    }
}