package com.malzberry.lolstuff;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// TODO: game plan
/*
    use ParsePlayers to grab list of player names
    create separate class for extracting (one at a time, from display) each info
    for selected player, add <SEARCHED_PLAYER> as the line before it.

 */

public class Display extends Activity implements AsyncResponse{
    TextView tv;
    String summoner, champ = "default";
    AsyncTask asyncTask = new ParsePlayers(this);

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;
    final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        //et = (EditText) findViewById(R.id.editDisplay);
        tv = (TextView) findViewById(R.id.genDisplay);
        summoner = getIntent().getStringExtra("summoner");

        asyncTask.execute(new String[]{summoner});

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void processFinish(String[] output){
        // receive result from async class
        tv.setText("");

        if(output[0] == "error"){
            // toast error
            Toast.makeText(this, "Error: " + output[1], Toast.LENGTH_LONG).show();
            tv.setText(output[1]);
            finish();
            return;
        }

        //Toast.makeText(this, "Rankings: " + output[3], Toast.LENGTH_LONG).show();
        ArrayList<String> players = new ArrayList<String>(Arrays.asList(output[0].split(",")));
        ArrayList<String> champs = new ArrayList<String>(Arrays.asList(output[1].split(",")));
        ArrayList<String> rankings = new ArrayList<String>(Arrays.asList(output[3].split(",")));
        //Toast.makeText(this, "summoner: " + summoner, Toast.LENGTH_LONG).show();
        // store last champ played
        for(int i = 0; i < players.size(); i++){
            //Toast.makeText(this, players.get(i).toLowerCase()+ " vs " +summoner.toLowerCase(), Toast.LENGTH_LONG).show();
            if(players.get(i).toLowerCase().trim().equals(summoner.toLowerCase().trim())){
                champ = champs.get(i);
                //Toast.makeText(this, "Champ found: " + champ, Toast.LENGTH_LONG).show();
                break;
            }
        }
        // update shared preferences
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("last_champ_played", champ);
        editor.commit();

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this, players, champs, rankings);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)   {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // TODO: set champion in shared preference here, create on pause or onStop
    /*
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("last_champ_played", champ);
    }
    */
}
