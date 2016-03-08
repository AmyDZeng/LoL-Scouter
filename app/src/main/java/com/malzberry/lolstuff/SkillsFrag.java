package com.malzberry.lolstuff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
  important links:
 http://ddragon.leagueoflegends.com/cdn/6.1.1/img/passive/Cryophoenix_Rebirth.png
    passive skill image

 http://ddragon.leagueoflegends.com/cdn/6.1.1/img/spell/FlashFrost.png
    ability image

 gameplan:

 pull info like always using json and riot API
 put into HTML file locally (same one each time, have default one saved to reset it to)
 host that HTML into a webview

 */
public class SkillsFrag extends android.support.v4.app.Fragment {
    public static final String ARG_SECTION_NUMBER = "3";
    public static final String TITLE = "Skills";
    public SkillsFrag(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // TODO: skills.xml layout
        View rootView = inflater.inflate(R.layout.skills, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.section_label);
        //tv.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        tv.setText(ARG_SECTION_NUMBER);
        return rootView;
    }
}
