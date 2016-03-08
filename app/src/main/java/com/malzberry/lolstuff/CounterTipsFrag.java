package com.malzberry.lolstuff;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Malzberry on 12/26/2015.
 */
public class CounterTipsFrag extends android.support.v4.app.Fragment implements AsyncResponseTips{
    public static final String ARG_SECTION_NUMBER = "2";
    public static final String TITLE = "Counter Tips";

    public CounterTipsFrag(){
    }

    String player, champ;
    ImageView icon;
    TextView tipsBody;
    TextView tipsHeader;
    AsyncTask parseTips = new ParseTips(this);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        player = getArguments().getString("player");
        champ = getArguments().getString("champ");

        icon = (ImageView) getView().findViewById(R.id.info_icon);
        tipsBody = (TextView) getView().findViewById(R.id.tips_body);
        tipsHeader = (TextView) getView().findViewById(R.id.tips_title);

        int resID = getResources().getIdentifier(champ.replaceAll("\\s+", "").replaceAll("'","").replaceAll("\\.","").toLowerCase() +"_square_0", "drawable", getActivity().getPackageName());
        try {
            icon.setImageResource(resID);
        }catch(Exception e){
            icon.setImageResource(getResources().getIdentifier("default_square_0", "drawable", getActivity().getPackageName()));
        }

        parseTips.execute(new String[]{champ});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.counter_tips, container, false);

        return rootView;
    }
    public class GeneralExc extends Exception{
        public GeneralExc(String message){
            super(message);
        }
    }

    public void processFinish(ArrayList<String> output){
        //hold champion info inarray
        if(output.get(0) == "Error"){
            //Toast.makeText(this, "Error in ParseTips: " + output.get(1), Toast.LENGTH_LONG).show();
            // TODO: ERROR HANDLING
            //throw new GeneralExc("Error: champion does not exist");
            // close?
            Toast.makeText(getActivity(), "Error: Champion does not exist", Toast.LENGTH_LONG).show();
            getActivity().finish();
            return;
        }

        // TODO: change to ArrayList? safer
        tipsHeader.setText(output.get(0));
        tipsBody.setText(output.get(1));
    }
}
