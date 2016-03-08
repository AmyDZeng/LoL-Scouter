package com.malzberry.lolstuff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SummaryPageFrag extends Fragment implements AsyncResponseTips{
    public static final String ARG_SECTION_NUMBER = "1";
    public static final String TITLE = "Summary";
    public SummaryPageFrag(){}

    String player, champ;
    ImageView icon;
    TextView winRate;
    TextView pickRate;

    Boolean firstCall = true;

    AsyncTask parseSummary = new ParseSummary(this);

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        player = getArguments().getString("player");
        champ = getArguments().getString("champ");

        icon = (ImageView) this.getView().findViewById(R.id.info_icon);
        winRate = (TextView) this.getView().findViewById(R.id.win_rate);
        pickRate = (TextView) this.getView().findViewById(R.id.pick_rate);

        int resID = getResources().getIdentifier(champ.replaceAll("\\s+", "").replaceAll("'","").replaceAll("\\.","").toLowerCase() +"_square_0", "drawable", getActivity().getPackageName());
        try {
            icon.setImageResource(resID);
        }catch(Exception e){
            icon.setImageResource(getResources().getIdentifier("default_square_0", "drawable", getActivity().getPackageName()));
        }

        // TODO: scrape parse and display general info (from riot and na.op.gg)

        if(firstCall) {
            parseSummary.execute(new String[]{champ});
            firstCall = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.summary_page, container, false);
        //TextView tv = (TextView) rootView.findViewById(R.id.section_label);
        //tv.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        // tv.setText(ARG_SECTION_NUMBER);

        // SET IMAGE

        return rootView;
    }

    public void processFinish(ArrayList<String> output){
        //hold champion info inarray
        if(output.get(0) == "Error"){
            // TODO: Error handling
            Toast.makeText(getActivity(), "Error: Hue", Toast.LENGTH_LONG).show();
            getActivity().finish();
            return;
        }

        // TODO: change to ArrayList? safer
        winRate.setText(output.get(0));
        pickRate.setText("Test");
        //tipsBody.setText(output.get(1));
    }
}
