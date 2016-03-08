package com.malzberry.lolstuff;



import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONArray;


public class ParsePlayers extends AsyncTask<String, Void, String[]> {
    public AsyncResponse delegate = null;
    public ParsePlayers(AsyncResponse ar) { delegate = ar;  }

    @Override
    protected String[] doInBackground(String... strings)  {
        StringBuffer buffer = new StringBuffer();
        JSONObject json;
        JSONArray jsonArr;
        String summonerId = "", summonerData = "", url = "";
        String output[] = {"", "", "", "", ""},  champUrl = "", id = "", playerUrl = "", rank = "";
        String playerIds[] = {"","","","","","","","","",""};

        try { //TODO: in the try, organize names by team numbers
            url =  "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"
                    + strings[0].toLowerCase().replaceAll(" ", "%20")
                    + "?api_key=f491797f-4845-4a08-b280-a337e2fae904";

            json = JsonReader.readJsonFromUrl(url);
            summonerId = (json.getJSONObject(json.names().getString(0))).getString("id");

            // use ID to get raw data
            url = "https://na.api.pvp.net/observer-mode/rest/consumer/getSpectatorGameInfo/NA1/"
                    + summonerId + "?api_key=f491797f-4845-4a08-b280-a337e2fae904";

            summonerData = JsonReader.readStringJsonFromUrl(url);
        } catch(Exception e){
            // TODO: throw 2 different exceptions, player doesn't exist && player not in a game rn.
            output[0] = "error";
            //output[1] = e.toString();
            output[1] = "Player doesn't exist or isn't currently in game.";
            return output;
        }

        /*
        participants: 10th (starting at 1) array elem.
        masteries(1), bot(2), runes(3), spell2Id(4), profile icon(5), summonerName(6),
        championId(7), teamId(8), summonerId(9), spell1Id(10)
        */
        try {// grab all players data
            json = new JSONObject(summonerData);
            jsonArr = json.getJSONArray("participants");

            for(int i = 0; i < jsonArr.length(); i++){
                output[0] += (jsonArr.getJSONObject(i)).getString("summonerName") + ",";

                // grab champion name by ID
                champUrl = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion/" + Integer.parseInt((jsonArr.getJSONObject(i)).getString("championId")) + "?api_key=f491797f-4845-4a08-b280-a337e2fae904";
                json = JsonReader.readJsonFromUrl(champUrl);
                output[1] += json.getString("name") + ",";
                playerIds[i] = (jsonArr.getJSONObject(i)).getString("summonerId");
                // grab playerid
                output[4] += playerIds[i] + ",";
            }


            // grab league rankings w/output[3]
            playerUrl = "https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/"
                    + output[4].substring(0, output[4].length()-1)
                    + "/entry?api_key=f491797f-4845-4a08-b280-a337e2fae904";

            JSONObject jsonObj = JsonReader.readJsonFromUrl(playerUrl);

            // 21072787,25128783,38209317
            for(int i = 0; i < playerIds.length; i++){
                rank = "";
                try{
                    json = jsonObj.getJSONArray(playerIds[i]).getJSONObject(0);
                    // jsonArr = "queue": "RANKED_SOLO_5x5"
                    rank = json.getString("tier") + " " ;
                    json = json.getJSONArray("entries").getJSONObject(0);
                    rank += json.getString("division");
                    rank += " (" + json.getString("leaguePoints") + ")";
                }catch(Exception e){
                    rank = "Unranked";
                }
                output[3] += rank + ",";
            }

        } catch(Exception e) {
            output[0] = "error";
            //output[1] = e.toString();
            output[1] = "Error loading game information.";
            return output;
        }
        return output;
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);
        delegate.processFinish(s);
    }
}