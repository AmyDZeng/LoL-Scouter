package com.malzberry.lolstuff;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// todo list!!
// TODO: think of grabbing/summarizing builds from other websites.
// TODO: think of expanding on counter tips
// TODO: draw out general idea of how the finished product should look
// TODO: look into little blocks to put tips in -- or just use EditText with editing off.
// TODO: write onResume() ?


public class MainActivity extends Activity {
    Button startButton, champButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: change bg based on last played champion
        // grab shared preferences

        // Toast.makeText(this, "Starting Main Activity", Toast.LENGTH_LONG).show();

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);

        if( sharedPref.contains("last_champ_played") ){ // if last champ saved exists
            // load last champ.
            String lastChamp = sharedPref.getString("last_champ_played", "DEFAULT");
            // Toast.makeText(this, "Loading: " + lastChamp, Toast.LENGTH_LONG).show();
            if(lastChamp != "default") {
                int resID = getResources().getIdentifier(lastChamp.replaceAll("\\s+", "").replaceAll("'", "").replaceAll("\\.", "").toLowerCase() + "_rect_0", "drawable", getPackageName());
                ImageView pic;
                pic = (ImageView) findViewById(R.id.main_bg);
                pic.setImageResource(resID);
            }
        }

        final EditText edtUrl = (EditText) findViewById(R.id.siteUrl);
        edtUrl.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        startButton = (Button) findViewById(R.id.enterButton);
        //respText = (TextView) findViewById(R.id.et);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // grab summoner name, go to display and parse lolnexus

                String summoner = edtUrl.getText().toString();
                //(new ParseTips(respText)).execute(new String[]{siteUrl});
                Intent goToDisplay = new Intent(MainActivity.this, Display.class); // make display
                goToDisplay.putExtra("summoner", summoner);
                startActivity(goToDisplay);
            }
        });

        champButton = (Button) findViewById(R.id.findChampBtn);
        champButton.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v){
                String champ = edtUrl.getText().toString();

                Intent goToDisplay = new Intent(MainActivity.this, DisplayInfoActivity.class); // make display
                goToDisplay.putExtra("player", "null");
                goToDisplay.putExtra("champion", champ);
                startActivity(goToDisplay);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
