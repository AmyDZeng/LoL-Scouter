package com.malzberry.lolstuff;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.sephiroth.android.library.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Activity activity;
    private ArrayList<String> champs;
    private ArrayList<String> players;
    private ArrayList<String> rankings;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView pic;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            pic = (ImageView) v.findViewById(R.id.icon);
            pic.setImageResource(R.drawable.braum_square_0);

        }
    }

    public void add(int position, String player, String champ, String rank){
        rankings.add(position, rank);
        players.add(position, player);
        champs.add(position, champ);
        notifyItemInserted(position);
    }

    public void remove(String player) {
        int position = players.indexOf(player);
        players.remove(position);
        champs.remove(position);
        rankings.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Activity activity, ArrayList<String> players, ArrayList<String> champs, ArrayList<String> rankings) {
        this.activity = activity;
        this.players = players;
        this.champs = champs;
        this.rankings = rankings;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View v = LayoutInflater.from(activity).inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // TODO: set champ icon here
        final String name = players.get(position);
        final String champ = champs.get(position);
        final String rank = rankings.get(position);

        holder.txtHeader.setText(name);
        holder.txtFooter.setText(rank);

        int resID = activity.getResources().getIdentifier(champs.get(position).replaceAll("\\s+", "").replaceAll("'","").replaceAll("\\.","").toLowerCase() +"_square_0", "drawable", activity.getPackageName());
        // holder.pic.setImageResource(resID);
        try {
            Picasso.with(activity).load(resID).into(holder.pic);
        }catch(Exception e){
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(activity, champs.get(position).replaceAll("\\s+", "").replaceAll("'", "").replaceAll("\\.","").toLowerCase(), Toast.LENGTH_LONG).show();
            Toast.makeText(activity, champs.get(position).replaceAll("\\s+", "").replaceAll("'","").replaceAll("\\.","").toLowerCase() +"_square_0", Toast.LENGTH_LONG).show();
        }

        holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: go to second page
                // Toast.makeText(v.getContext(), "click", Toast.LENGTH_LONG).show();

                Intent goToDisplay = new Intent(activity.getBaseContext(), DisplayInfoActivity.class); // make display
                goToDisplay.putExtra("player", name);
                goToDisplay.putExtra("champion", champ);
                activity.startActivity(goToDisplay);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return players.size();
    }

}