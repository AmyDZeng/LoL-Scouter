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

public class ParseTips extends AsyncTask<String, Void, ArrayList<String>> {
    public AsyncResponseTips delegate = null;
    public ParseTips(AsyncResponseTips ar) { delegate = ar;  }
    ArrayList<String> output = new ArrayList<String>();

    @Override
    protected ArrayList<String> doInBackground(String... strings)  {
        StringBuffer buffer = new StringBuffer();
        try {
            Document doc = Jsoup.connect("http://www.championselect.net/champions/"
                    + strings[0].toLowerCase()).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1").get();

            // Get document (HTML page) title
            String title = doc.title();
            output.add(title + "\r\n\n");

            // Get meta info
            Elements metaElems = doc.select("span._tip");
            for(Element metaElem : metaElems)   {
                String content = metaElem.text();
                buffer.append(content + "\r\n\n");
            }

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
